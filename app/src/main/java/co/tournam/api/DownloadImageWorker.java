package co.tournam.api;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DownloadImageWorker extends AsyncTask<String, Void, Bitmap> {

    private ImageDownloaded listener;

    /**
     * Constructor
     *
     * @param listener The listener to be notified when the image is downloaded
     */
    public DownloadImageWorker(ImageDownloaded listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String bannerID = params[0];

        Bitmap result = ImageLoader.downloadImage(bannerID);

        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        this.listener.result(result);
    }

    /**
     * Interface for the listener
     */
    public interface ImageDownloaded {
        void result(Bitmap image);
    }
}
