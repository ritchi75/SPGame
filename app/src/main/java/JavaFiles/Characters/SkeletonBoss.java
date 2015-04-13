package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/13/2015.
 */
public class SkeletonBoss extends Character {

    //default constructor for an object of type SkeletontBoss
    public SkeletonBoss()
    {
        super.name = "Skeleton";
        super.moves = getSkeletonMoves();
        super.stat = getSkeletonStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getSkeletonMoves()
    {
        Move move1 = new Move("Bone Wack", "Physical", null,
                "Hits you with his femur", new Effect(7, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default warrior stats
    private Stat getSkeletonStats()
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
