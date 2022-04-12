package co.tournam.api;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class DownloadImageWorker extends AsyncTask<String, Void, Bitmap> {

    public DownloadImageWorker() {

    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String bannerID = params[0];

        Bitmap result = ImageLoader.downloadImage(bannerID);
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

    }
}
