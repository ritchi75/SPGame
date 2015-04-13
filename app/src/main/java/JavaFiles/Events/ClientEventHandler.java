package JavaFiles.Events;

import android.media.Image;

import java.util.List;

import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 4/5/2015.
 */
public class ClientEventHandler extends EventHandler{

    // default constructor for an object of type HostEventHandler
    public ClientEventHandler(List<Character> players, List<Character> enemies, Image backgroundImage) {
        super(players, enemies, backgroundImage);
    }

    public String useMove(String userName, String moveName, String targetName) {
        return null;
    }

    // sends out a bluetooth message to the host of which move this user selected
    // returns the string with the message to display along with updated target/user information
    public String useMoveBluetooth(String bluetoothMove)
    {
        // send this message to the client
        // return the message returned by the client
        return null;
    }
}
