package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaFiles.StatusEffects.Defend_StatusEffect;
import JavaFiles.StatusEffects.Esuna_StatusEffect;
import JavaFiles.StatusEffects.HardBody_StatusEffect;
import JavaFiles.StatusEffects.HumanShield_StatusEffect;
import JavaFiles.StatusEffects.Stunned_StatusEffect;
import JavaFiles.StatusEffects.ThroatSinging_StatusEffect;

/**
 * Created by AlexC on 4/10/2015.
 */
public class Monk extends Character {

    // default constructor for an object of type warrior
    public Monk()
    {
        super.name = "Monk";
        super.moves = getMonkMoves();
        super.stat = getMonkStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getMonkMoves()
    {
        Move move1 = new Move("Heal", "Magical", null,
                "Heals a teammate", new Effect(-50, new ArrayList<Character>()));
        Move move2 = new Move("Holy Light", "Magical", null,
                "Does Holy Damage to a target", new Effect(12, new ArrayList<Character>()));
        Move move3 = new Move("Throat Singing", "Magical", null,
                "Increases resistance of target for 3 turns", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new ThroatSinging_StatusEffect()}))));
        Move move4 = new Move("Esuna", "magical", null,
                "Removes all status effects from the target", new Effect(0, new ArrayList<Character>(Arrays.asList(new Character[] {new Esuna_StatusEffect()}))));
        Move move5 = new Move("Attack", "Physical", null,
                "A basic Attack", new Effect(2, new ArrayList<Character>()));
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
    private Stat getMonkStats()
    {
        return new Stat
                        (100,    // Health
                        8,     // Strength
                        14,      // Intelligence
                        8,      // Agility
                        16,      // Charisma
                        12);    // Resistance
    }
}
