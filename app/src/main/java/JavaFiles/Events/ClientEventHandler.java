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
    public ClientEventHandler(String enemyName) {
        super(null, enemyName);
    }

    public MoveOutcome useMove(String userName, String moveName, String targetName) {
        return null;
    }
}
