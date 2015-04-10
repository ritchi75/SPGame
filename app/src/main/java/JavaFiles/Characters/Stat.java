package JavaFiles.Characters;

/**
 * Created by AlexC on 3/10/2015.
 */
public class Stat {
    private int health;
    private int strength;
    private int intelligence;
    private int agility;
    private int charisma;
    private int resistance;

    // constructor for an object of type stat
    public Stat(int health, int strength, int intelligence,
                int agility, int charisma, int resistance) {
        this.health = health;
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.charisma = charisma;
        this.resistance = resistance;
    }

    // get the current health value
    public int getHealth() {
        return this.health;
    }

    // modify the health value
    public void modifyHealth(int healthChange) {
        this.health -= healthChange;
    }

    // get the current strength value
    public int getStrength() {
        return this.strength;
    }

    // modify the strength value
    public void modifyStrength(int strengthChange) {
        this.strength += strengthChange;
    }

    // get the current intelligence value
    public int getIntelligence() {
        return this.intelligence;
    }

    // modify the intelligence value
    public void modifyIntelligence(int intelligenceChange) {
        this.intelligence += intelligenceChange;
    }

    // get the current agility value
    public int getAgility() {
        return this.agility;
    }

    // modify the agility value
    public void modifyAgility(int agilityChange) {
        this.agility = agilityChange;
    }

    // get the current charisma value
    public int getCharisma() {
        return this.charisma;
    }

    // modify the charisma value
    public void modifyCharisma(int charismaChange) {
        this.charisma += charismaChange;
    }

    // get the current resistance value
    public int getResistance() {
        return this.resistance;
    }

    // modify the resistance value
    public void modifyResistance(int resistanceChange) {
        this.resistance += resistanceChange;
    }

}
