package JavaFiles;

import java.util.List;

import JavaFiles.Characters.MoveResult;

/**
 * Created by AlexC on 3/26/2015.
 */
public class StatusEffect extends Character {
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

    @Override
    protected List<Character> checkAppliedStatusEffects(List<Character> effects) {
        return null;
    }

    @Override
    public MoveResult hitByEffect(Effect effect) {
        return null;
    }

    @Override
    public Character applyStatusEffects(List<Character> effects) {
        return null;
    }
}
