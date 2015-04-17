package JavaFiles.Characters;

import android.media.Image;

/**
 * Created by AlexC on 3/10/2015.
 */
public class Item {
    private String description;
    private Image image;
    private Stat stat;

    // constructor for an object of type Item
    public Item(String description, Image image, Stat stat) {
        this.description = description;
        this.image = image;
        this.stat = stat;
    }

    // get the description of this item
    public String getDescription() {
        return this.description;
    }

    // get the image of this item
    public Image getImage() {
        return this.image;
    }

    // get the stats of this item
    public Stat getStat() {
        return this.stat;
    }
}
