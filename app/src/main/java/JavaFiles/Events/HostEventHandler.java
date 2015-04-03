package JavaFiles.Events;

import android.media.Image;

import java.util.*;

import JavaFiles.Characters.Character;
import JavaFiles.Characters.Effect;
import JavaFiles.Characters.Move;
import JavaFiles.Characters.MoveResult;

/**
 * Created by AlexC on 3/28/2015.
 * An event handler which is the host for the entire game
 */
public class HostEventHandler extends EventHandler {

    // default constructor for an object of type HostEventHandler
    public HostEventHandler(List<Character> players, List<Character> enemies, Image backgroundImage) {
        super(players, enemies, backgroundImage);
    }

    // Called by the Event Activity
    // Uses an attack
    public String useMove(Character user, String moveName, String targetName)
    {
        // figure out which move was used
        Move moveUsed = user.findMoveByName(moveName);
        // get the effect of the used move
        Effect moveEffect = moveUsed.getEffect();

        // figure out which target was selected
        Character target = findTargetByName(targetName);

        // apply the move
        // returns a list with the damage in the first index and the names of any status effects applied afterwards
        MoveResult moveResult = target.hitByEffect(moveEffect);
        // unpack the message from the MoveResult
        List<String> moveResultMessage = moveResult.getResultMessage();
        //un pack the resulting character from the MoveResult
        // and save its reference to overwrite the current target
        target = moveResult.getResultCharacter();

        // break down our result to a single, formatted string
        String result = user.getName() + " used " + moveName + " against " + target.getName();
        // check if any damage was done and we need to report it
        if(Integer.parseInt(moveResultMessage.get(0)) != 0) {
            result += " for " + moveResultMessage.get(0) + " damage ";
        }
        // check if any status effects were applied and we need to report them
        if(moveResultMessage.size() > 1)
        {
            result += " which inflicted: ";
            // add names of all status effects used from the rest of the list, skipping the first slot
            // which contains the damage
            for(String effectName : moveResultMessage.subList(1,moveResultMessage.size()))
            {
                result += effectName;
                result += " ";
            }
        }


        return result;
    }

    // finds the target matching the given name in our list of enemies
    private Character findTargetByName(String targetName)
    {
        for(Character character : super.getEnemies())
        {
            if(character.getName().equals(targetName))
                return character;
        }

        return null;
    }
}
