package com.example.evilj.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by JjaviMS on 13/03/2018.
 *
 * @author JJaviMS
 * <p>
 * Auxiliar class to help saving the steps
 * </p>
 */

public class Steps implements Parcelable {

    private String shorDesc;
    private String desc;
    private String videoUrl;
    private String imageUrl;


    public Steps(String shorDesc, String desc, String videoUrl, String imageUrl) {
        this.shorDesc = shorDesc;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
    }

    public Steps(Parcel parcelable) {
        this.shorDesc = parcelable.readString();
        this.desc = parcelable.readString();
        this.videoUrl = parcelable.readString();
        this.imageUrl = parcelable.readString();
    }

    public String getShorDesc() {
        return shorDesc;
    }


    public String getDesc() {
        return desc;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public String getImageUrl(){return imageUrl;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Steps steps = (Steps) o;
        return Objects.equals(shorDesc, steps.shorDesc) &&
                Objects.equals(desc, steps.desc) &&
                Objects.equals(videoUrl, steps.videoUrl);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shorDesc);
        parcel.writeString(desc);
        parcel.writeString(videoUrl);
        parcel.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Steps> CREATOR =
            new Creator<Steps>() {
                @Override
                public Steps createFromParcel(Parcel parcel) {
                    return new Steps(parcel);
                }

                @Override
                public Steps[] newArray(int i) {
                    return new Steps[i];
                }
            };

    public static Steps[] parseParcelable(Parcelable[] parcelables) {
        return Arrays.copyOf(parcelables, parcelables.length, Steps[].class);
    }
}
