package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by Alex on 4/9/2015.
 */
public class Rage_StatusEffect extends StatusEffect {

    // default constructor for an object of type Rage_statusEffect
    public Rage_StatusEffect()
    {
        super("Rage");
    }

    // returns a rage status effect with a character inside of it
    public Rage_StatusEffect(Character character)
    {
        super("Rage");
        super.setCharacter(character);
        super.setTurnsRemaining(2);
    }

    @Override
    public MoveResult hitByEffect(Effect effect) {
        if(super.getCharacter().hasStatusEffect(this))
            effect.setBase_damage((int) (effect.getBase_damage() * 1.3));

        return super.getCharacter().hitByEffect(effect);
    }
}
