package JavaFiles.Characters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 4/13/2015.
 */
public class EmpressBoss extends Character {

    //default constructor for an object of type EmpressBoss
    public EmpressBoss()
    {
        super.name = "Empress";
        super.moves = getEmpressMoves();
        super.stat = getEmpressStats();
        super.inventory = new ArrayList<Item>(); //no items for now
    }

    // returns a list of all moves an Empress has
    private List<Move> getEmpressMoves()
    {
        Move move1 = new Move("Throw Crown", "Magical", null,
                "Throws her crown", new Effect(10, new ArrayList<Character>()));

        List<Move> moves = new ArrayList<Move>();
        moves.add(move1);
        return moves;
    }

    // returns an object of type stats with the default Empress stats
    private Stat getEmpressStats()
    {
        return new Stat
                        (2000,    // Health
                        18,     // Strength
                        6,      // Intelligence
                        8,      // Agility
                        6,      // Charisma
                        14);    // Resistance
    }
}
