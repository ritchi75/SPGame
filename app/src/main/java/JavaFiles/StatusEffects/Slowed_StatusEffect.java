package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Slowed_StatusEffect extends StatusEffect {

    // default constructor for an object of type Slowed_statusEffect
    public Slowed_StatusEffect()
    {
        super("Slowed");
    }

    // returns an object of type slowed_statusEffect with a character object inside
    public Slowed_StatusEffect(Character character) {
        super("Slowed");
        super.setCharacter(character);
        super.setTurnsRemaining(3);
    }
}
