package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by Alex on 4/9/2015.
 */
public class HardBody_StatusEffect extends StatusEffect {

    // default constructor for an object of type hardbody_statusEffect
    public HardBody_StatusEffect()
    {
        super("Hard Body");
    }

    // returns an object of type hard body with a character object inside
    public HardBody_StatusEffect(Character character)
    {
        super("Hard Body");
        super.setCharacter(character);
        super.setTurnsRemaining(3);
    }

    @Override
    public MoveResult hitByEffect(Effect effect) {
        // reduce the damage done by 25%
        effect.setBase_damage((int) (effect.getBase_damage() * .75));

        return super.getCharacter().hitByEffect(effect);
    }

}
