package JavaFiles.Characters;

import java.util.List;

import JavaFiles.*;
import JavaFiles.Character;

/**
 * Created by AlexC on 3/26/2015.
 * Used to store the resulting character and the message after using a move on
 * said character
 */
public class MoveResult {
    private JavaFiles.Character resultCharacter;
    private List<String> resultMessage;

    // default constructor for object of type MoveResult
    public MoveResult(Character resultCharacter, List<String> resultMessage)
    {
        this.resultCharacter = resultCharacter;
        this.resultMessage = resultMessage;
    }

    // returns the result message
    public List<String> getResultMessage()
    {
        return this.resultMessage;
    }

    // returns the resultCharacter
    public Character getResultCharacter()
    {
        return this.resultCharacter;
    }
}
