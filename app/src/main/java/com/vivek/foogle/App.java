package com.vivek.foogle;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by vivek on 7/16/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize global stuff for Yourney
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "ldLLk7tGzDfPYFI1OqIofSkxVRUm1x5q2al4AqtG", "YQLb7V46e5Pjeu0W4LG7d81l1t9M8RhVF5my7dwQ");

    }
}