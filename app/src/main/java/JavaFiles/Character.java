package JavaFiles;

import java.util.ArrayList;
import java.util.List;

import JavaFiles.Characters.MoveResult;

/**
 * Created by AlexC on 3/10/2015.
 */
public abstract class Character {

    protected String name;
    protected List<Move> moves;
    protected Stat stat;
    protected List<Item> inventory;

    // Used to change(raise or lower) the hp of a character
    public void ChangeHPBy(int amount) {
        this.stat.modifyHealth(amount);
    }

    // get a list of all moves usable by this character
    public List<Move> getMoves() {
        return this.moves;
    }

    // get the items in the users inventory
    public List<Item> getItems() {
        return this.inventory;
    }

    // get the name of this character
    public String getName()
    {
        return this.name;
    }

    // returns the move with a matching name
    // if none are found it returns null (Ut-oh)
    public Move findMoveByName(String name)
    {
        for(Move move : moves)
        {
            if(name.equals(move.getName()))
            {
                return move;
            }
        }
        return null;
    }

    // checks which status effects this character already has and returns a list with all new ones to be applied
    protected abstract List<Character> checkAppliedStatusEffects(List<Character> effects);
    // if its a status effect class, remove its name from the list of effects if it is there; possibly refresh time on effect?
    // then call the same method on the character object it stores
    // if its a default job class then return the list of status effects as it remains
    // all status effects which remain in the list the player doesn't already have and should be wrapped around the outermost class
    // which hitByEffect was called on so return the list back there


    // applies the effect of a move on this character
    public abstract MoveResult hitByEffect(Effect effect);
    // get the damage the move inflicts
    //int damage = effect.getBase_damage();
    // modify damage if this status effect does that!!!
    // lower this classes health by the amount of damage
    //stat.modifyHealth(damage); *** DO THE RECURSION IN THIS CALL

    // get the status effects from the move that were applied
    //List<Character> effects = applyStatusEffect(effect.getStatus_effects());
    // status effects add their name to a list and call the next method, continue until we hit the base class
    // then pass a list of currently afflicted statuses back up

    // create an array list to return our list of damages and status effects applied to this character
    //List<String> result = new ArrayList<String>();

    //result.add(damage + "");
    //for(Character effect : effects)
    //{
    //      result.add(effect.getName());
    //}
    // return the list with the names of first, the damage inflicted, and secondly the names of all effects applied
    //return result;

    // applies the status effects stored in the list
    public abstract Character applyStatusEffects(List<Character> effects);
}
