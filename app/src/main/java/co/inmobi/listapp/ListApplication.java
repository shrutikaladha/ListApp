package co.inmobi.listapp;

import android.app.Application;

import co.inmobi.listapp.dagger.ApplicationComponent;
import co.inmobi.listapp.dagger.DaggerApplicationComponent;

public class ListApplication extends Application {

    ApplicationComponent appComponent = DaggerApplicationComponent.create();

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
