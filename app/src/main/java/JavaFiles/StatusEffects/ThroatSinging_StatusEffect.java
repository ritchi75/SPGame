package JavaFiles.StatusEffects;

import java.util.concurrent.ConcurrentHashMap;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class ThroatSinging_StatusEffect extends StatusEffect {

    // default constructor for an object of type ThroatSinging_StatusEffect
    public ThroatSinging_StatusEffect() {
        super("Throat Singing");
    }

    // returns an object of type ThroatSinging_StatusEffect with a character inside
    // default constructor for an object of type ThroatSinging_StatusEffect
    public ThroatSinging_StatusEffect(Character character) {
        super("Throat Singing");
        super.setCharacter(character);
        super.setTurnsRemaining(4);
    }

    @Override
    // reduces the damage of an attack when a character with this status effect is hit
    public MoveResult hitByEffect(Effect effect) {
        // reduce the damage of this attack by 30%
        effect.setBase_damage((int) (effect.getBase_damage() * .7));

        return super.getCharacter().hitByEffect(effect);
    }
}
