package JavaFiles.Events;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 3/12/2015.
 */
public abstract class EventHandler {
    private List<Character> players;
    private List<Character> enemies;
    private Image backgroundImage;


    // Constructor for an object of type EventHandler
    public EventHandler(List<Character> players, List<Character> enemies, Image backgroundImage) {
        this.players = players;
        this.enemies = enemies;
        this.backgroundImage = backgroundImage;
    }

    // Called by the Event Activity
    // Uses an attack
    public abstract String useMove(Character user, String moveName, String targetName);

    // uses a move via bluetooth
    public abstract String useMoveBluetooth(String bluetoothMove);

    // finds the target matching the given name in our list of enemies
    private Character findTargetByName(String targetName) {
        for (Character character : enemies) {
            if (character.getName().equals(targetName))
                return character;
        }

        return null;
    }

    // returns the list of enemies
    public List<Character> getEnemies() {
        return this.enemies;
    }

    // returns the names of the players
    public ArrayList<String> getPlayerNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Character character : players) {
            names.add(character.getName());
        }
        return names;
    }

    // returns the names of the players
    public ArrayList<String> getEnemyNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Character character : enemies) {
            names.add(character.getName());
        }

        return names;
    }

    // returns the list of players
    public List<Character> getPlayers()
    {
        return this.players;
    }
}
