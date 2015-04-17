package JavaFiles.StatusEffects;

import java.util.Random;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class SmokeScreen_StatusEffect extends StatusEffect {

    // default constructor for an object of type SmokeScreen_statusEffect
    public SmokeScreen_StatusEffect() {
        super("Smokescreen");
    }

    // returns an object of type smokescreen_StatusEffect with a character object stored inside
    public SmokeScreen_StatusEffect(Character character) {
        super("Smokescreen");
        super.setCharacter(character);
        super.setTurnsRemaining(2);
    }

    @Override
    // reduces the damage of an attack when a character with this status effect is hit
    public MoveResult hitByEffect(Effect effect) {
        // 70% chance to get hit by this attack
        Random random = new Random();
        // if we roll a 7 or above, prevent all damage
        if(random.nextInt(10) >= 7) {
            effect.setBase_damage((int) (0));
        }

        return super.getCharacter().hitByEffect(effect);
    }
}
