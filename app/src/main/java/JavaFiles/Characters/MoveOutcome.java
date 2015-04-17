package JavaFiles.Characters;

/**
 * Created by Alex on 4/12/2015.
 */
public class MoveOutcome {
    private String healthRemaining;
    private String outcomeMessage;

    public MoveOutcome(int healthReamining, String outcomeMessage)
    {
        this.healthRemaining = String.valueOf(healthReamining);
        this.outcomeMessage = outcomeMessage;
    }

    // returns the amount of health left on the target after a move was used
    public String getHealthRemaining()
    {
        return this.healthRemaining;
    }

    // returns the outcome message of this move
    public String getOutcomeMessage()
    {
        return this.outcomeMessage;
    }
}
