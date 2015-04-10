package JavaFiles.Characters;

import java.util.List;

/**
 * Created by AlexC on 3/26/2015.
 */
public abstract class StatusEffect extends JavaFiles.Characters.Character {
    private Character character;
    private int turnsRemaining;
    private String statusEffectName;

    // Default constructor for an object of type StatusEffect
    public StatusEffect(String statusEffectName) {
        this.statusEffectName = statusEffectName;
        turnsRemaining = 3;
    }

    // Set the character object stored inside of this status effect for use with the decorator pattern
    public Character setCharacter(Character character)
    {
        this.character = character;
        return this;
    }

    // get a reference to the character object stored in this class
    public Character getCharacter()
    {
        return this.character;
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

    // applies the effect of an attack on the character
    // if the subclass doesn't override this method then it does not affect the effect so
    // just check the character
    public MoveResult hitByEffect(Effect effect)
    {
        return this.character.hitByEffect(effect);
    }

    // returns the name of the character stored in this status effect
    public String getName()
    {
        return this.character.getName();
    }

    // returns the moves of the character stored in this status effect
    public List<Move> getMoves()
    {
        return this.character.getMoves();
    }

    // returns the names of the moves of the character stored in this status effect
    public List<String> getMoveNames()
    {
        return this.character.getMoveNames();
    }

    // returns the amount of turns remaining
    public int getTurnsRemaining()
    {
        return this.turnsRemaining;
    }

    // returns the move with the corresponding name in the character stored in this status effect
    public Move findMoveByName(String name)
    {
        return this.character.findMoveByName(name);
    }

    // returns the name of this status effect
    public String getStatusEffectName()
    {
        return this.statusEffectName;
    }

    // returns whether or not the given character has the status effect
    public boolean hasStatusEffect(StatusEffect statusEffect) {
        if(statusEffect.getStatusEffectName().equals(this.getStatusEffectName()))
            return true;

        else return this.character.hasStatusEffect(statusEffect);
    }

    // returns the base character object from the decorated character with status effects
    public Character getBaseCharacter()
    {
        return this.character.getBaseCharacter();
    }

    @Override
    //performs the end turn checks on this character
    // remove this status effect if it has run out of turns remaining
    // else check the next one
    public int endTurnCheck()
    {
        if(this.turnsRemaining == 1)
        {
            // remove it
        }
        // else decrement the turns remaining
        else {
            turnsRemaining -= 1;
        }
        return 0 + this.character.endTurnCheck();
    }
}
