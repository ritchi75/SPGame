package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by Alex on 4/9/2015.
 */
public class Defend_StatusEffect extends StatusEffect {

    // default constructor for an object of type Defend_StatusEffect
    public Defend_StatusEffect()
    {
        super("Defend");
    }

    // returns an object of type Defend_StatusEffect with a character inside
    public Defend_StatusEffect(Character character)
    {
        super("Defend");
        super.setCharacter(character);
        super.setTurnsRemaining(1);
    }

    @Override
    // reduces the damage of an attack when a character with this status effect is hit
    public MoveResult hitByEffect(Effect effect) {
        // reduce the damage of this attack by 30%
        effect.setBase_damage((int) (effect.getBase_damage() * .7));

        return super.getCharacter().hitByEffect(effect);
    }

}
