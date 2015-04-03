package JavaFiles.StatusEffects;

import java.util.ArrayList;
import java.util.List;

import JavaFiles.*;
import JavaFiles.Character;
import JavaFiles.Characters.MoveResult;

/**
 * Created by AlexC on 3/27/2015.
 * A status effect which makes all damage done 0
 * and prevents any status effects
 */
public class HumanShield_StatusEffect extends StatusEffect {

    // default constructor for an object of type HumanShield_StatusEffect
    public HumanShield_StatusEffect() {
        super("Human Shield");
    }

    // constructor for an object of type HumanShield_StatusEffect
    // pass the super the character to store and change the turns remaining to one
    public HumanShield_StatusEffect(Character character)
    {
        super("Human Shield");
        super.setCharacter(character);
        super.setTurnsRemaining(1);
    }

    @Override
    // applies a status effect to this character
    public MoveResult hitByEffect(Effect effect) {
        // the move does not inflict damage because of this status effect
        effect.setBase_damage(0);

        return super.getCharacter().hitByEffect(effect);
    }
}
