package co.tournam.api;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class UploadImageWorker extends AsyncTask<Bitmap, Void, String> {
    private ImageUploaded listener;

    public UploadImageWorker(ImageUploaded listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        Bitmap image = params[0];

        String result = ImageLoader.uploadImage(image);

        return result;
    }

    @Override
    protected void onPostExecute(String imageId) {
        this.listener.result(imageId);
    }

    public interface ImageUploaded {
        void result(String imageId);
    }
}
