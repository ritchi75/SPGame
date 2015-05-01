package JavaFiles.Stories;

import java.util.ArrayList;

/**
 * Created by Matt + Alex on 4/8/2015.
 */
public class Story {

    String title;
    ArrayList<Integer> image;
    private boolean isEvent;
    private boolean isDecision;
    private String bossName;

    public Story(String title, ArrayList<Integer> image, Boolean isEvent, Boolean isDecision, String bossName) {
        this.title = title;
        // make deep copies of all the images
        if(image != null)
            this.image = new ArrayList<Integer>(image);
        else
            this.image = new ArrayList<Integer>();
        this.isEvent = isEvent;
        this.isDecision = isDecision;
        this.bossName = bossName;
    }

    // Returns the title of this story
    public String getTitle() {
        return title;

    }

    // Returns the image associated with this story
    public ArrayList<Integer> getImage() {
        return image;
    }

    // Returns true if this story is an event, false if it is not
    public Boolean isEvent() {
        return this.isEvent;
    }

    // Returns true if this story is a decision, false if it is not
    public Boolean isDecision()
    {
        return this.isDecision;
    }

    // Returns the boss associated with this event-story
    public String getBossName()
    {
        return this.bossName;
    }

    // Returns the number of images in this story
    public int getNumImages()
    {
        return this.image.size();
    }
}