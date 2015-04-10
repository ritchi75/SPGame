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
    public int endTurnCheck()
    {
        int turnsRemaining = super.getTurnsRemaining();

        if(turnsRemaining== 1)
        {
            // remove it
        }
        // else decrement the turns remaining
        else {
            super.setTurnsRemaining(turnsRemaining - 1);
        }

        // add 2 damage to be done
        return 2 + super.getCharacter().endTurnCheck();
    }
}
