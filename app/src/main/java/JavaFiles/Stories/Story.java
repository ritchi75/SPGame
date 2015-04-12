package JavaFiles.Stories;

import java.util.ArrayList;

/**
 * Created by Matt on 4/8/2015.
 */
public class Story {

    String title;
    ArrayList<Integer> image;

    public Story(String title, ArrayList<Integer> image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;

    }

    public ArrayList<Integer> getImage() {
        return image;
    }


}