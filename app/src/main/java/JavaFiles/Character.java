package JavaFiles;

import java.util.List;

/**
 * Created by AlexC on 3/10/2015.
 */
public class Character {

    private List<Move> moves;
    private Stat stat;
    private List<Item> inventory;

    // Used to change(raise or lower) the hp of a character
    public void ChangeHPBy(int amount) {
        this.stat.modifyHealth(amount);
    }

    // get a list of all moves usable by this character
    public List<Move> getMoves() {
        return this.moves;
    }

    // get the items in the users inventory
    public List<Item> getItems() {
        return this.inventory;
    }

}
