package JavaFiles.StatusEffects;

import JavaFiles.Characters.Character;
import JavaFiles.Characters.Effect;
import JavaFiles.Characters.MoveResult;
import JavaFiles.Characters.StatusEffect;

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

    @Override
    public boolean hasStatusEffect(StatusEffect statusEffect) {
        if(statusEffect.getStatusEffectName().equals(this.getStatusEffectName()))
            return true;

        else return super.getCharacter().hasStatusEffect(statusEffect);
    }
}
