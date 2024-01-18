package dev.akdag.kidsstory;

public class StoryList {
    private int imageResource;
    private int colorResource;
    private String storyName;
    private String id;




    public StoryList(){}

    public StoryList(int imageResource, int colorResource, String storyName, String id) {
        this.imageResource = imageResource;
        this.colorResource = colorResource;
        this.storyName = storyName;
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
