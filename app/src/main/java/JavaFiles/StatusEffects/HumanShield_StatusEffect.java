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
    }

    // constructor for an object of type HumanShield_StatusEffect
    // pass the super the character to store and change the turns remaining to one
    public HumanShield_StatusEffect(Character character)
    {
        super.setCharacter(character);
        super.setTurnsRemaining(1);
    }

    @Override
    // applies a status effect to this character
    public MoveResult hitByEffect(Effect effect) {
        // the move does not inflict damage because of this status effect
        int damage = 0;

        //lower this classes health by the amount of damage
        stat.modifyHealth(damage);

        // create a list of strings and add the damage value of 0 to it to display it to the player
        List<String> result = new ArrayList<String>();
        result.add(damage + "");

        // return the move result with this character since nothing has changed
        // and the result of the move (string saying 0 damage)
        return new MoveResult(this, result);
    }
}
