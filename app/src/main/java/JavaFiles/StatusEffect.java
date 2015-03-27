package JavaFiles;

import java.util.List;

import JavaFiles.Characters.MoveResult;

/**
 * Created by AlexC on 3/26/2015.
 */
public abstract class StatusEffect extends Character {
    private Character character;
    private int turnsRemaining;

    // Default constructor for an object of type StatusEffect
    public StatusEffect() {
        turnsRemaining = 3;
    }

    // Set the character object stored inside of this status effect for use with the decorator pattern
    public Character setCharacter(Character character)
    {
        this.character = character;
        return this;
    }

    // Set the amount of turns remaining of this status effect
    public void setTurnsRemaining(int turnsRemaining)
    {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    // check if this status effect is on the list to be applied, if so remove it
    // then check the character contained within this object
    protected List<Character> checkAppliedStatusEffects(List<Character> effects) {
        // attempt to remove this status effect from the list to be applied
        // if this is not in the list, then this call does nothing
        effects.remove(this);

        // check for any more status effects within the character object stored in this class
        return this.character.checkAppliedStatusEffects(effects);
    }

    @Override
    public abstract MoveResult hitByEffect(Effect effect);

    @Override
    // Applies the list of status effects to this character
    public Character applyStatusEffects(List<Character> effects) {
        // set up a result to store our new character objects
        // initially, set it to this character for if we don't need to make changes
        Character resultingCharacter = this;

        // check if we have any effects left to apply
        if(effects.size() > 0) {
            // if so, apply one and call the applyStatusEffects method on the resulting Character object
            resultingCharacter = ((StatusEffect) effects.get(0)).setCharacter(this);
        }
        else if(effects.size() > 1){
            // if we have at least one more status effect to apply, apply it on the resulting object which we
            // have already modified from before
            return resultingCharacter.applyStatusEffects(effects.subList(1,effects.size()));
        }
        else {
            // otherwise we are done so return our resulting character
            // which will be wrapped once if we had 1 change, or this if we had 0 changes to make
            return resultingCharacter;
        }
        // make the compiler happy =)
        return resultingCharacter;
    }
}
