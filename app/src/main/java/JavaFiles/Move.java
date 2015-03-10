package JavaFiles;

import android.media.Image;

import JavaFiles.Effect;

/**
 * Created by AlexC on 3/10/2015.
 */
public class Move {

    private String name;
    private String type;
    private Image image;
    private String description;
    private Effect effect;

    // constructor for an instance of Move
    public Move(String name, String type, Image image, String description, Effect effect) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.description = description;
        this.effect = effect;
    }

    // gets the name of this move
    public String getName() {
        return this.name;
    }

    // gets the type of this move
    public String getType() {
        return this.type;
    }

    // gets the image associated with this move
    public Image getImage() {
        return this.image;
    }

    // gets the description of this move
    public String getDescription() {
        return this.description;
    }

    // gets the effect of this move
    public Effect getEffect() {
        return this.effect;
    }
}

