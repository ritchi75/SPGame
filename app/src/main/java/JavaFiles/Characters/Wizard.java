package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/9/2015.
 */
public class Wizard extends Character{

    // returns a new wizard
    public Wizard()
    {
        super.name = "Wizard";
        //super.moves = getWizardMoves();
        //super.stat = getWizardStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    @Override
    protected List<Character> checkAppliedStatusEffects(List<Character> effects) {
        return effects;
    }

    @Override
    public MoveResult hitByEffect(Effect effect) {
        return null;
    }

    @Override
    public Character applyStatusEffects(List<Character> effects) {
        return null;
    }
}
