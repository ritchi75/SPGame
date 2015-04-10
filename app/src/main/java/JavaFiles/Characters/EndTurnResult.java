package JavaFiles.Characters;

import java.util.ArrayList;

/**
 * Created by AlexC on 4/10/2015.
 */
public class EndTurnResult {

    private int damage;
    private ArrayList<Character> characters;

    // return an EndTurnResult with only the damage saved
    public EndTurnResult(int damage)
    {
        this.damage = damage;
        this.characters = new ArrayList<Character>();
    }

    // default constructor for an object of type EndTurnResult
    public EndTurnResult(int damage, ArrayList<Character> characters)
    {
        this.damage = damage;
        this.characters = characters;
    }

    // returns the damage stored in this end turn result
    public int getDamage()
    {
        return this.damage;
    }

    // sets the damage of this endTurnResult
    public void addDamage(int damage)
    {
        this.damage += damage;
    }

    // returns the stored character
    public ArrayList<Character> getEffects()
    {
        return  this.characters;
    }

    // set the character
    public void addEffect(Character character)
    {
        this.characters.add(character);
    }

    // removes all status effects from this endTurnResult
    public void clearStatusEffects()
    {
        this.characters.clear();
    }
}
