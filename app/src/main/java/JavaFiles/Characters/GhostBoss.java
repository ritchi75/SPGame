package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/13/2015.
 */
public class GhostBoss extends Character {

    //default constructor for an object of type GhostBoss
    public GhostBoss()
    {
        super.name = "Ghost";
        super.moves = getGhostMoves();
        super.stat = getGhostStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getGhostMoves()
    {
        Move move1 = new Move("Shadow Punch", "Magical", null,
                "Reaches through the shadows and hits you", new Effect(7, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default warrior stats
    private Stat getGhostStats()
    {
        return new Stat
                        (750,    // Health
                        18,     // Strength
                        6,      // Intelligence
                        8,      // Agility
                        6,      // Charisma
                        14);    // Resistance
    }
}
