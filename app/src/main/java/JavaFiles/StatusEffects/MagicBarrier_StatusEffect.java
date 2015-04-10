package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class MagicBarrier_StatusEffect extends StatusEffect {
    // default constructor for an object of type MagicBarrier_StatusEffect
    public MagicBarrier_StatusEffect()
    {
        super("Magic Barrier");
    }

    // returns an object of type MagicBarrier_statusEffect with a character object inside
    public MagicBarrier_StatusEffect(Character character) {
        super("Magic Barrier");
        super.setCharacter(character);
        super.setTurnsRemaining(3);
    }

    @Override
    // reduces the damage of an attack when a character with this status effect is hit
    public MoveResult hitByEffect(Effect effect) {
        // reduce the damage of this attack by 30%
        effect.setBase_damage((int) (effect.getBase_damage() * .7));

        return super.getCharacter().hitByEffect(effect);
    }

}
