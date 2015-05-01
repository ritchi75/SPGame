package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import JavaFiles.StatusEffects.Defend_StatusEffect;
import JavaFiles.StatusEffects.HardBody_StatusEffect;
import JavaFiles.StatusEffects.HumanShield_StatusEffect;
import JavaFiles.StatusEffects.Stunned_StatusEffect;

/**
 * Created by Alex on 4/13/2015.
 */
public class RobotBoss extends Character{

    //default constructor for an object of type RobotBoss
    public RobotBoss()
    {
        super.name = "Robot";
        super.moves = getRobotMoves();
        super.stat = getRobotStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a robot has
    private List<Move> getRobotMoves()
    {
        Move move1 = new Move("Shoot Laser", "Magical", null,
                "Shoots his laser gun", new Effect(6, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default robot stats
    private Stat getRobotStats()
    {
        return new Stat
                        (100,    // Health
                        18,     // Strength
                        6,      // Intelligence
                        8,      // Agility
                        6,      // Charisma
                        14);    // Resistance
    }
}
