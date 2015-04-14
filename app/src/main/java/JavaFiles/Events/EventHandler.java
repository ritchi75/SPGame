package JavaFiles.Events;

import android.media.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import JavaFiles.Characters.Character;
import JavaFiles.Characters.EmpressBoss;
import JavaFiles.Characters.GhostBoss;
import JavaFiles.Characters.KnightBoss;
import JavaFiles.Characters.MoveOutcome;
import JavaFiles.Characters.RobotBoss;
import JavaFiles.Characters.SkeletonBoss;
import JavaFiles.Characters.SquiggleBoss;

/**
 * Created by AlexC on 3/12/2015.
 */
public abstract class EventHandler {
    private List<Character> players;
    private List<Character> enemies;


    // Constructor for an object of type EventHandler
    public EventHandler(List<Character> players, String enemyName) {
        this.players = players;
        Character boss = loadBoss(enemyName);
        ArrayList<Character> enemy = new ArrayList<>();
        enemy.add(boss);
        this.enemies = enemy;
    }

    // Called by the Event Activity
    // Uses an attack
    public abstract MoveOutcome useMove(String user, String moveName, String targetName);

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

    // update the list containing the characters
    public void updateCharacter(Character character)
    {
        for(int i = 0; i < enemies.size(); i++)
        {
            if(enemies.get(i).getName().equals(character.getName())) {
                enemies.set(i, character);
                break;
            }
        }

        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getName().equals(character.getName()))
            {
                players.set(i, character);
                break;
            }
        }
    }

    // end turn logic
    public int endTurn()
    {
        return 0;
    }

    // returns a boss' move
    public String getBossMove(){
    return "Attack";
    }

    // returns the boss's hp
    public int getBossHP()
    {
        return this.enemies.get(0).getHP();
    }

    // returns all of the player's hp levels
    public ArrayList<Integer> getPlayerHP()
    {
        ArrayList<Integer> result = new ArrayList<Integer>();

        for(Character character : players)
        {
            result.add(character.getHP());
        }

        return result;
    }

    // returns an object of the corresponding boss
    private Character loadBoss(String bossName)
    {
        switch(bossName) {
            case "Squiggle": return new SquiggleBoss();
            case "Empress": return new EmpressBoss();
            case "Ghost": return new GhostBoss();
            case "Knight": return new KnightBoss();
            case "Robot": return new RobotBoss();
        }

        // otherwise it must be a skeleton
        return new SkeletonBoss();
    }
}
