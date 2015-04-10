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

    @Override
    public abstract MoveResult hitByEffect(Effect effect);

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
    public abstract boolean hasStatusEffect(StatusEffect statusEffect);

}
