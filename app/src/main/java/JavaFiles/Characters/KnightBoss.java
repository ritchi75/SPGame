package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/13/2015.
 */
public class KnightBoss extends Character {

    //default constructor for an object of type KnightBoss
    public KnightBoss()
    {
        super.name = "Knight";
        super.moves = getKnightMoves();
        super.stat = getKnightStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves a warrior has
    private List<Move> getKnightMoves()
    {
        Move move1 = new Move("Sword Strike", "Physical", null,
                "Slashes you with his sword", new Effect(6, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default Knight stats
    private Stat getKnightStats()
    {
        return new Stat
                        (1000,    // Health
                        18,     // Strength
                        6,      // Intelligence
                        8,      // Agility
                        6,      // Charisma
                        14);    // Resistance
    }
}
