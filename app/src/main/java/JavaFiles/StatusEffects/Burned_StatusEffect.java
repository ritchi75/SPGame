package JavaFiles.StatusEffects;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Burned_StatusEffect extends StatusEffect {

    // default constructor for an object of type Burned_statusEffect
    public Burned_StatusEffect()
    {
        super("Burned");
    }

    // returns an object of type Burned_statusEffect with a character object stored inside
    public Burned_StatusEffect(Character character)
    {
        super("Burned");
        super.setCharacter(character);
        super.setTurnsRemaining(3);
    }

    @Override
    //performs the end turn checks on this character
    // remove this status effect if it has run out of turns remaining
    // else check the next one
    public EndTurnResult endTurnCheck(EndTurnResult result) {
        int turnsRemaining = super.getTurnsRemaining();

        // add our 2 damage to be dealt regardless if the status effect is expiring this turn or not
        result.addDamage(2);

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
