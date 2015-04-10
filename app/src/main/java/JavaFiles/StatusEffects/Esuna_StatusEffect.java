package JavaFiles.StatusEffects;

import JavaFiles.Characters.EndTurnResult;
import JavaFiles.Characters.Stat;
import JavaFiles.Characters.StatusEffect;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Esuna_StatusEffect extends StatusEffect {

    // default constructor for an object of Esuna_statusEffect
    public Esuna_StatusEffect() {
        super("Esuna");
    }

    @Override
    //performs the end turn checks on this character
    // cleanse will remove all status effects which are applied to the character
    public EndTurnResult endTurnCheck(EndTurnResult result)
    {
        result.clearStatusEffects();
        return result;
    }
}
