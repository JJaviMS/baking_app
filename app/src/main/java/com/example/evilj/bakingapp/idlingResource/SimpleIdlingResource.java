package com.example.evilj.bakingapp.idlingResource;

import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by JjaviMS on 17/05/2018.
 *
 * @author JJaviMS
 */
public class SimpleIdlingResource implements android.support.test.espresso.IdlingResource {

    @Nullable private volatile ResourceCallback mResourceCallback;

    private AtomicBoolean mIsIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    public void setIsIdle (boolean bool){
        mIsIdle.set(bool);
        if (isIdleNow() && mResourceCallback!=null){
            mResourceCallback.onTransitionToIdle();
        }
    }
}
