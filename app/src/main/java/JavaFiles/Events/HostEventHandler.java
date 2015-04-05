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

    private List<String> movesThisTurn;

    // default constructor for an object of type HostEventHandler
    public HostEventHandler(List<Character> players, List<Character> enemies, Image backgroundImage) {
        super(players, enemies, backgroundImage);
    }

    // uses bluetooth to wait for all inputs then activate them in the proper order
    public String useMoveBluetooth(String bluetoothMove)
    {
        // keep of a list of the strings to be used to update the UI
        List<String> results = new ArrayList<String>();

        // get the string from the other bluetooth connection if there is one
        String otherPlayerMove = "";

        // get the move that the boss will use
        String bossMove = ""; //getBossMove();

        // when movesThisTurn has all 3 moves, check for which order to execute in and do it

        //send the appropriate return messages via bluetooth/internally\

        // call end turn logic to check for status effects leaving/dead characters
        return null;
    }

    // Called by the Event Activity
    // use an attack
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

    // finds the target matching the given name in our list of enemies and players
    private Character findTargetByName(String targetName)
    {
        // check enemy list for a match
        for(Character character : super.getEnemies())
        {
            if(character.getName().equals(targetName))
                return character;
        }

        // check player list for a match
        for(Character character : super.getPlayers())
        {
            if(character.getName().equals(targetName))
                return character;
        }
        // bad attack, return the enemy just to assume it was cast on it
        return super.getEnemies().get(0);
    }

    // picks a move and target for the boss to use this turn
    private String getBossMove()
    {
        Character boss = super.getEnemies().get(0);
        String bossTarget = getBossTarget();
        String bossMoveName = getBossMoveName(boss);
        String userName = boss.getName();

        String result = "";

        result += userName + "##" + bossMoveName + "##" + bossTarget;
        return result;
    }

    // picks the name of a target for the boss
    private String getBossTarget()
    {
        int size = super.getPlayers().size();

        // if there is only one player, return that player's name
        if(size == 1)
            return super.getPlayers().get(0).getName();
        else { // else return a random character
            Random rng = new Random();
            return super.getPlayers().get(rng.nextInt(size)).getName();
        }
    }

    // picks the move the boss will use
    private String getBossMoveName(Character boss)
    {
        Random rng = new Random();
        // return a random move name within the size of the list
        return boss.getMoveNames().get(rng.nextInt(boss.getMoveNames().size()));
    }
}
