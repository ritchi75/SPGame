package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaFiles.StatusEffects.Defend_StatusEffect;
import JavaFiles.StatusEffects.HardBody_StatusEffect;
import JavaFiles.StatusEffects.HumanShield_StatusEffect;
import JavaFiles.StatusEffects.Stunned_StatusEffect;

/**
 * Created by AlexC on 3/26/2015.
 */
public class Warrior extends Character {


    // default constructor for an object of type warrior
    public Warrior()
    {
        super.name = "Warrior";
        super.moves = getWarriorMoves();
        super.stat = getWarriorStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getWarriorMoves()
    {
        Move move1 = new Move("Human Shield", "Physical", null,
                "Defend a teammate from the next attack(Always goes first)", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new HumanShield_StatusEffect()}))));
        Move move2 = new Move("Staggering Slash", "Physical", null,
                "Chance to stun target for 1 turn", new Effect(8, new ArrayList<Character>(Arrays.asList(new Character[] {new Stunned_StatusEffect()}))));
        Move move3 = new Move("Hard Body", "Physical", null,
                 "Increases Resistance for 3 turns(Does not stack)", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new HardBody_StatusEffect()}))));
        Move move4 = new Move("Rage", "Physical", null,
                 "Increases damage if it is used consecutively", new Effect(5, new ArrayList<Character>(Arrays.asList(new Character[] {new Stunned_StatusEffect()}))));
        Move move5 = new Move("Attack", "Physical", null,
                 "A basic Attack", new Effect(14, new ArrayList<Character>()));
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
    private Stat getWarriorStats()
    {
        return new Stat
                (200,    // Health
                18,     // Strength
                6,      // Intelligence
                8,      // Agility
                6,      // Charisma
                14);    // Resistance
    }
}
