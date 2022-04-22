package co.tournam.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageLoader {
    public static final String DOWNLOAD_PATH = RequestHandler.url + "/image/download";
    public static final String UPLOAD_PATH = RequestHandler.url + "/image/upload";

    public static Bitmap loadImage(String imageId) {
        return downloadImage(imageId);
    }


    /**
     * Downloads an image from the server and returns it as a Bitmap
     * @param imageId The ID of the image to download
     * @return The image as a Bitmap
     */
    public static Bitmap downloadImage(String imageId) {
        if(imageId == null || imageId.equals("null")) return null;

        try {
            java.net.URL url = new java.net.URL(DOWNLOAD_PATH);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoInput(true);

            String jsonInputString = "{\"id\":\""+imageId+"\"}";

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            connection.connect();

            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Uploads an image to the server and returns the ID of the image
     * @param bitmap The image to upload
     * @return The ID of the image
     */
    public static String uploadImage(Bitmap bitmap) {
        try {
            java.net.URL url = new java.net.URL(UPLOAD_PATH);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "image/png");
            connection.setDoInput(true);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, connection.getOutputStream());
            connection.connect();

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine = null;

            while ((responseLine = input.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject json = new JSONObject(response.toString());

            return json.getString("id");
        } catch (IOException | JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
