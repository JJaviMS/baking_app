package com.example.evilj.bakingapp.models;

/**
 * Created by JjaviMS on 13/03/2018.
 *
 * @author JJaviMS
 *         <p>
 *         Auxiliar class to help saving the steps
 *         </p>
 */

public class Steps {

    private String shorDesc;
    private String desc;
    private String videoUrl;
    private String thumbnailUrl;

    public Steps(String shorDesc, String desc, String videoUrl, String thumbnailUrl) {
        this.shorDesc = shorDesc;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
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


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Steps steps = (Steps) o;

        if (shorDesc != null ? !shorDesc.equals(steps.shorDesc) : steps.shorDesc != null)
            return false;
        if (desc != null ? !desc.equals(steps.desc) : steps.desc != null) return false;
        if (videoUrl != null ? !videoUrl.equals(steps.videoUrl) : steps.videoUrl != null)
            return false;
        return thumbnailUrl != null ? thumbnailUrl.equals(steps.thumbnailUrl) : steps.thumbnailUrl == null;
    }

}
