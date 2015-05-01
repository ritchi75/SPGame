package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexC on 3/10/2015.
 */
public abstract class Character {

    protected String name;
    protected List<Move> moves;
    protected Stat stat;
    protected List<Item> inventory;

    // Used to change(raise(negative) or lower(positive)) the hp of a character
    public void ChangeHPBy(int amount) {
        this.stat.modifyHealth(amount);
    }

    // changes the hp without going through the status effects checks
    // used for burn/poison damage
    public void endTurnDamage(int damage)
    {
        this.stat.modifyHealth(damage);
    }

    // returns this character's remaining HP
    public int getHP()
    {
        return this.stat.getHealth();
    }

    // get a list of all moves usable by this character
    public List<Move> getMoves() {
        return this.moves;
    }

    // get a list of all move names usable by this character
    public List<String> getMoveNames() {
        List<String> moveNames = new ArrayList<String>();

        // get each move's name
        for (Move move : moves) {
            moveNames.add(move.getName());
        }

        return moveNames;

    }

    // get the items in the users inventory
    public List<Item> getItems() {
        return this.inventory;
    }

    // get the name of this character
    public String getName() {
        return this.name;
    }

    // returns the move with a matching name
    // if none are found it returns null (Ut-oh)
    public Move findMoveByName(String name) {
        for (Move move : moves) {
            if (name.equals(move.getName())) {
                return move;
            }
        }
        return null;
    }

    // returns true if a character has the given status effect, false if it does not
    // if this is being called on this object then it must not have it, return false
    public boolean hasStatusEffect(StatusEffect statusEffect) {
        return false;
    }

    // returns the list of status effects to apply to this character
    // if it is being called on a base character, no further changes need to be made to the list so return it
    protected List<Character> checkAppliedStatusEffects(List<Character> effects) {
        return effects;
    }

    // applies a status effect to this character
    public MoveResult hitByEffect(Effect effect) {
        //get the damage the move inflicts
        int damage = effect.getBase_damage();

        //lower this classes health by the amount of damage
        stat.modifyHealth(damage);

        // get the status effects from the move that were applied
        List<Character> effects = checkAppliedStatusEffects(effect.getStatus_effects());
        //status effects add their name to a list and call the next method, continue until we hit the base class
        //then pass a list of currently afflicted statuses back up

        // apply the status effects by wrapping this class with them
        // and get a reference to the final character object with the effects applied
        Character resultingCharacter = applyStatusEffects(effects);

        // create an array list to return our list of damages and status effects applied to this character
        ArrayList<String> result = new ArrayList<String>();

        result.add(damage + "");

        for (Character efct : effects) {
            StatusEffect se = (StatusEffect) efct;
            result.add(se.getStatusEffectName());
        }

        // return the move result with the resulting character and a list of strings which has
        // the damage inflicted, and secondly the names of all effects applied
        return new MoveResult(resultingCharacter, result);
    }

    // Applies the list of status effects to this character
    // use recursion to prevent references from overlapping
    public Character applyStatusEffects(List<Character> effects) {
        // set up a result to store our new character objects
        // initially, set it to this character for if we don't need to make changes
        Character resultingCharacter = this;

        // check if we have any effects left to apply
        if (effects.size() > 0) {
            // if so, apply one and call the applyStatusEffects method on the resulting Character object
            resultingCharacter = ((StatusEffect) effects.get(0)).setCharacter(this);
        } else if (effects.size() > 1) {
            // if we have at least one more status effect to apply, apply it on the resulting object which we
            // have already modified from before
            return resultingCharacter.applyStatusEffects(effects.subList(1, effects.size()));
        } else {
            // otherwise we are done so return our resulting character
            // which will be wrapped once if we had 1 change, or this if we had 0 changes to make
            return resultingCharacter;
        }
        // make the compiler happy =)
        return resultingCharacter;
    }

    // gets the base character
    // returns this since only the base characters don't override this method
    public Character getBaseCharacter()
    {
        return this;
    }

    // performs the check on what status effects need to be removed and the damage done
    public EndTurnResult endTurnCheck(EndTurnResult result)
    {
        return result;
    }

    // fully heals a characters hp before the next encounter
    public void fullHeal()
    {
        this.stat.modifyHealth(-100);
    }
}
