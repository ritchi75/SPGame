package JavaFiles.Stories;

import java.util.ArrayList;
import java.util.Random;

import seniorproject.game.R;

/**
 * Created by Alex on 4/8/2015.
 */
public class Stories {

    ArrayList<Story> stories = new ArrayList<Story>();
    private int storyPoint;
    private Boolean choiceMade;


    public Stories() {
        init();
    }

    // constructor for a Stories Object
    public void init(){
        storyPoint = -1;
        choiceMade = false;
        populateStories();
    }

    // Returns a list of stories which will be displayed to the user
    //  Keeps track of where we are in the story
    public Story getNextStory() {
        // advance the story
        storyPoint += 1;

        switch(storyPoint)
        {
            case 0:
                Random rng = new Random();
                return stories.get(rng.nextInt(4));
            case 1: return stories.get(4);
            case 2: return stories.get(5);
            case 3: return stories.get(6);
            case 4: return stories.get(7);
            case 5: return stories.get(8);
            case 6:
                // if the players accepted the kiss, jump to point 7
                if(choiceMade)
                    return stories.get(10);
                // otherwise return point 6
                return stories.get(9);
            case 7: return stories.get(11);
            case 8: return stories.get(12);
        }
        // something went wrong, end the game
        return null;
    }

    // returns the list of stories
    public ArrayList<Story> getStories() {
        return stories;
    }

    // removes a story from the list to prevent repeating it
    public void removeStory(Story story) {
        stories.remove(story);
    }

    // sets the choice that the players made
    public void setChoiceMade(Boolean choiceMade)
    {
        this.choiceMade = choiceMade;
    }

    // populates our list of stories with all the events and stories
    // events handle their own pictures which is why we pass null through without issue
    // stories have no boss's so we can pass null safely for them
    // only decisions need decision choices
    private void populateStories()
    {
        // store the list of images assoicated with the stories
        ArrayList<Integer> images = new ArrayList<Integer>();

        // starter stories: point 0
        // Warrior starter
        images.add(R.drawable.starter);
        images.add(R.drawable.brawl_starter1);
        images.add(R.drawable.brawl_starter2);
        stories.add(new Story("Way of the Warrior", images, false, false, null));

        // Mage starter
        images.clear();
        images.add(R.drawable.starter);
        images.add(R.drawable.mage_starter1);
        images.add(R.drawable.mage_starter2);
        stories.add(new Story("The power of magic", images, false, false, null));

        // Monk starter
        images.clear();
        images.add(R.drawable.starter);
        images.add(R.drawable.peace_starter1);
        images.add(R.drawable.peace_starter2);
        stories.add(new Story("The path of enlightenment", images, false, false, null));

        // Rogue starter
        images.clear();
        images.add(R.drawable.starter);
        images.add(R.drawable.theif_starter1);
        images.add(R.drawable.theif_starter2);
        stories.add(new Story("A cunning plan", images, false, false, null));

        // Squiggle fight: point 1
        images.clear();
        stories.add(new Story("The first battle", null, true, false, "Squiggle"));

        // follow up story: point 2
        images.clear();
        images.add(R.drawable.locked_town1);
        images.add(R.drawable.locked_town2);
        stories.add(new Story("LOCK TOWN", images, false, false, null));

        // Skeleton fight: point 3
        images.clear();
        stories.add(new Story("The next test", null, true, false, "Skeleton"));

        // Locked town continued/throne room : point 4
        images.clear();
        images.add(R.drawable.locked_town3);
        images.add(R.drawable.locked_town4);
        images.add(R.drawable.throne_room1);
        images.add(R.drawable.throne_room2);
        images.add(R.drawable.throne_room3);
        images.add(R.drawable.throne_room4);
        images.add(R.drawable.throne_room5);
        stories.add(new Story("The Royal Palace", images, false, false, null));

        // first decision event : point 5
        ArrayList<String> decisions = new ArrayList<String>();
        images.clear();
        images.add(R.drawable.decision);
        decisions.add("Accept kiss");
        decisions.add("Reject kiss");
        stories.add(new Story("Will you accept the empress's kiss?", images, false, true, null));

        // players rejected the empress's kiss : point 6a
        images.clear();
        images.add(R.drawable.tr_draw1);
        images.add(R.drawable.tr_draw2);
        images.add(R.drawable.tr_draw3);
        images.add(R.drawable.guards_to_empress);
        stories.add(new Story("The rejected kiss", images, false, false, "null"));

        // players accept the empress's kiss : point 6b
        images.clear();
        images.add(R.drawable.tr_recieve1);
        images.add(R.drawable.tr_recieve2);
        images.add(R.drawable.tr_recieve3);
        images.add(R.drawable.tr_recieve4);
        images.add(R.drawable.tr_recieve5);
        images.add(R.drawable.guards_to_empress);
        stories.add(new Story("The empress's kiss", images, false, false, "null"));

        // Empress fight : point 7
        images.clear();
        stories.add(new Story("The final battle", null, true, false, "Empress"));

        // Victory! : point 8
        images.clear();
        images.add(R.drawable.empress_defeated1);
        images.add(R.drawable.empress_defaeted2);
        images.add(R.drawable.empress_defeated3);
        images.add(R.drawable.empress_defeated4);
        images.add(R.drawable.empress_defeated5);
        images.add(R.drawable.empress_defeated6);
        images.add(R.drawable.game_win);
        stories.add(new Story("Victory", images, false, false, null));



    }

    // returns the story point we are currently on
    public int getStoryPoint()
    {
        return this.storyPoint;
    }
}