import java.util.List;

import JavaFiles.*;
import JavaFiles.Character;

/**
 * Created by AlexC on 3/12/2015.
 */
public class EventHandler {

    private List<Character> players;
    private List<Character> enemies;


    // Constructor for an object of type EventHandler
    public EventHandler(List<Character> players, List<Character> enemies)
    {
        this.players = players;
        this.enemies = enemies;
    }

    // Called by the Event Activity
    // Uses an attack
    public String useMove(Character user, String moveName, Character target)
    {
        // figure out which move was used
        Move moveUsed = user.findMoveByName(moveName);
        // get the effect of the used move
        Effect moveEffect = moveUsed.getEffect();

        // apply the move
        // returns a list with the damage in the first index and the names of any status effects applied afterwards
        List<String> moveResult = target.hitByEffect(moveEffect);

        // break down our result to a single, formatted string
        String result = user.getName() + " used " + moveName + " against " + target.getName();
        // check if any damage was done and we need to report it
        if(Integer.parseInt(moveResult.get(0)) != 0) {
            result += " for " + moveResult.get(0) + " damage ";
        }
        // check if any status effects were applied and we need to report them
        if(moveResult.size() > 1)
        {
            result += " which inflicted: ";
            // add names of all status effects used from the rest of the list, skipping the first slot
            // which contains the damage
            for(String effectName : moveResult.subList(1,moveResult.size() - 1))
            {
                result += effectName;
                result += " ";
            }
        }


        return result;
    }
}
