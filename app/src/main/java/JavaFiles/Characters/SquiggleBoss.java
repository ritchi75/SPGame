package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/13/2015.
 */
public class SquiggleBoss extends Character {

    //default constructor for an object of type SquiggleBoss
    public SquiggleBoss()
    {
        super.name = "Squiggle";
        super.moves = getSquiggleMoves();
        super.stat = getSquiggleStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a squiggle has
    private List<Move> getSquiggleMoves()
    {
        Move move1 = new Move("Squiggle", "Physical", null,
                "Squiggle squiggle", new Effect(8, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default squiggle stats
    private Stat getSquiggleStats()
    {
        return new Stat
                        (500,    // Health
                        18,     // Strength
                        6,      // Intelligence
                        8,      // Agility
                        6,      // Charisma
                        14);    // Resistance
    }
}
