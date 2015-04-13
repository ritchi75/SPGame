package JavaFiles.Events;

import android.media.Image;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import JavaFiles.Characters.Character;
import JavaFiles.Characters.Effect;
import JavaFiles.Characters.EndTurnResult;
import JavaFiles.Characters.Move;
import JavaFiles.Characters.MoveOutcome;
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

    // Called by the Event Activity
    // use an attack
    public MoveOutcome useMove(String userName, String moveName, String targetName)
    {

        // get a reference to the user
        Character user = findTargetByName(userName);

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
        target = moveResult.getResultCharacter();
        //overwrite the corresponding character with this new one
        super.updateCharacter(target);

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


        MoveOutcome moveOutcome = new MoveOutcome(target.getHP(), result);
        return moveOutcome;
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
    public String getBossMove()
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

    // checks for dead characters and removes status effects which have expired from characters
    // at the end of a turn
    // returns an int describing the outcome of the battle:
    // 0 - battle is not over
    // 1 - the player defeated the boss; exit to next story
    // 2 - the boss defeated all the players; retry/exit to story
    public int endTurn()
    {
        // keep track of whether or not all bosses are dead
        int battleStatusCode = 0;
        // story how many players are dead
        int numDeadPlayers = 0;

        // store how much damage was done to the character during this step
        EndTurnResult result;

        // do end turn logic for all characters
        for(Character character : this.getPlayers())
        {
            // do the end turn checks
            result = character.endTurnCheck(new EndTurnResult(0));
            // perform the end turn damage to the character
            character.endTurnDamage(result.getDamage());
            // update the character object with the applied status effects
            super.updateCharacter(character.getBaseCharacter().applyStatusEffects(result.getEffects()));
            //check if this player has died
            if(character.getHP() <= 0)
                numDeadPlayers++;
        }

        // check if all players are dead
        if(numDeadPlayers == getPlayers().size())
            battleStatusCode = 2;

        // do the end turn checks for the boss
        for(Character character : this.getEnemies())
        {
            // do the end turn check
            result = character.endTurnCheck(new EndTurnResult(0));
            // perform the end turn damage to the character
            character.endTurnDamage(result.getDamage());
            // update the character object with the applied status effects
            super.updateCharacter(character.getBaseCharacter().applyStatusEffects(result.getEffects()));
            //check if the boss died and the players win
            if(character.getHP() <= 0)
                battleStatusCode = 1;
        }

        // if all players and boss die simultaneously, the players win
        return battleStatusCode;

    }
}
