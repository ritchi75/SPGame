package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaFiles.StatusEffects.Burned_StatusEffect;
import JavaFiles.StatusEffects.Cleanse_StatusEffect;
import JavaFiles.StatusEffects.Defend_StatusEffect;
import JavaFiles.StatusEffects.HardBody_StatusEffect;
import JavaFiles.StatusEffects.HumanShield_StatusEffect;
import JavaFiles.StatusEffects.MagicBarrier_StatusEffect;
import JavaFiles.StatusEffects.Slowed_StatusEffect;
import JavaFiles.StatusEffects.Stunned_StatusEffect;

/**
 * Created by Alex on 4/9/2015.
 */
public class Wizard extends Character{

    // returns a new wizard
    public Wizard()
    {
        super.name = "Wizard";
        super.moves = getWizardMoves();
        super.stat = getWizardStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getWizardMoves()
    {
        Move move1 = new Move("Pyro Blast", "Magical", null,
                "Attacks with magical damage. Chance to burn target", new Effect(8, new ArrayList<Character>(Arrays.asList(new Character[]{new Burned_StatusEffect()}))));
        Move move2 = new Move("Magic Barrier", "Magical", null,
                "Reduces damage from incoming attacks for 3 turns", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new MagicBarrier_StatusEffect()}))));
        Move move3 = new Move("Ice Blast", "Magical", null,
                "Attacks with magical damage. Chance to slow target", new Effect(6, new ArrayList<Character>(Arrays.asList(new Character[] {new Slowed_StatusEffect()}))));
        Move move4 = new Move("Cleanse", "Magical", null,
                "Remove all status effects from the target", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new Cleanse_StatusEffect()}))));
        Move move5 = new Move("Attack", "Magical", null,
                "A basic Attack", new Effect(10, new ArrayList<Character>()));
        Move move6 = new Move("Defend", "Physical", null,
                "Defend yourself",new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new Defend_StatusEffect()}))));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        moves.add(move2);
        moves.add(move3);
        moves.add(move4);
        moves.add(move5);
        moves.add(move6);

        return moves;
    }

    // creates the stats for a wizard
    public Stat getWizardStats()
    {
        return new Stat
                       (120,    // Health
                        8,     // Strength
                        18,      // Intelligence
                        10,      // Agility
                        10,      // Charisma
                        10);    // Resistance
    }


}
