package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Poison_StatusEffect extends StatusEffect {

    // default constructor for an object of type Poison_StatusEffect
    public Poison_StatusEffect()
    {
        super("Poison");
    }

    // creates an object of type Poison_StatusEffect
    public Poison_StatusEffect(Character character) {
        super("Poison");
        super.setCharacter(character);
        super.setTurnsRemaining(3);
    }

    @Override
    //performs the end turn checks on this character
    // remove this status effect if it has run out of turns remaining
    // else check the next one
    public EndTurnResult endTurnCheck(EndTurnResult result) {
        int turnsRemaining = super.getTurnsRemaining();

        // add our damage to be dealt regardless if the status effect is expiring this turn or not
        // does decreasing damage as the turns remaining decrease
        // continually apply poison to keep damage up
        result.addDamage(2 * turnsRemaining);

        if (turnsRemaining == 1) {
            return super.getCharacter().endTurnCheck(result);
        }
        // else decrement the turns remaining
        else {
            turnsRemaining -= 1;
            // add this to the list so it is kept wrapped around the character class later
            result.addEffect(this);
        }

        return super.getCharacter().endTurnCheck(result);
    }
}
