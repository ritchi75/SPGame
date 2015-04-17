package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by Alex on 4/9/2015.
 */
public class Stunned_StatusEffect extends StatusEffect {

    // default constructor for an object of type stunned_status effect
    public Stunned_StatusEffect()
    {
        super("Stunned");
    }

    // creates an object of type stunned_status effect with a character object inside of it
    public Stunned_StatusEffect(Character character)
    {
        super("Stunned");
        super.setCharacter(character);
        super.setTurnsRemaining(1);
    }
}
