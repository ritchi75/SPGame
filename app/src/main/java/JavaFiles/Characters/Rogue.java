package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaFiles.StatusEffects.Defend_StatusEffect;
import JavaFiles.StatusEffects.HardBody_StatusEffect;
import JavaFiles.StatusEffects.HumanShield_StatusEffect;
import JavaFiles.StatusEffects.Poison_StatusEffect;
import JavaFiles.StatusEffects.SmokeScreen_StatusEffect;
import JavaFiles.StatusEffects.Stunned_StatusEffect;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Rogue extends Character {

    public Rogue()
    {
        super.name = "Rogue";
        super.moves = getRogueMoves();
        super.stat = getRogueStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getRogueMoves()
    {
        Move move1 = new Move("Assassinate", "Physical", null,
                "High damage strike", new Effect(15, new ArrayList<Character>()));
        Move move2 = new Move("Coated Blade", "Physical", null,
                "Chance to poison target", new Effect(8, new ArrayList<Character>(Arrays.asList(new Character[] {new Poison_StatusEffect()}))));
        Move move3 = new Move("Smoke Screen", "Physical", null,
                "Increases target's evasion", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new SmokeScreen_StatusEffect()}))));
        Move move4 = new Move("Substitute", "Physical", null,
                "Lets an inanimate object take the damage of the next attack", new Effect(5, new ArrayList<Character>(Arrays.asList(new Character[] {new Stunned_StatusEffect()}))));
        Move move5 = new Move("Attack", "Physical", null,
                "A basic Attack", new Effect(3, new ArrayList<Character>()));
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

    // returns an object of type stats with the default warrior stats
    private Stat getRogueStats()
    {
        return new Stat
                        (140,    // Health
                        18,     // Strength
                        4,      // Intelligence
                        16,      // Agility
                        12,      // Charisma
                        12);    // Resistance
    }
}
