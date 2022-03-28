package co.tournam.schedule;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import co.tournam.api.PersistentCookieStore;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CookieHandler.setDefault(new CookieManager(new PersistentCookieStore(getApplicationContext()), CookiePolicy.ACCEPT_ALL));
    }
}
