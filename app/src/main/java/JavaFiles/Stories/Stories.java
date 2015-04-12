package JavaFiles.Stories;

import java.util.ArrayList;

import seniorproject.game.R;

/**
 * Created by Alex on 4/8/2015.
 */
public class Stories {

    ArrayList<Story> stories = new ArrayList<Story>();
    ArrayList<Integer> images = new ArrayList<Integer>();

    public Stories() {
        init();
    }

    public void init(){
        images.add(R.drawable.locked_town1);
        images.add(R.drawable.locked_town2);
        images.add(R.drawable.locked_town3);
        images.add(R.drawable.locked_town4);
        stories.add(new Story("LOCK TOWN", images));
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void removeStory(Story story) {
        stories.remove(story);
    }
}