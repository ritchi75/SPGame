package seniorproject.game;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import JavaFiles.Characters.Character;
import JavaFiles.Characters.Warrior;
import JavaFiles.Events.EventHandler;
import JavaFiles.Events.HostEventHandler;

/**
 * The EventUI Activity controls the activity_event_ui XML layout.
 * It's main task is to provide functionality for users while engaged in an event,
 * encounter, or boss fight.
 */
public class EventUI extends ActionBarActivity {

    private String targetName;
    private String moveSelected;
    private ArrayList<String> playerNames;
    private ArrayList<String> enemyNames;
    private List<String> moveNames;
    private Character user;
    private EventHandler handler;

    /**
     * Android Activity default method. Adds functionality to XML layout.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_ui);

        // Initialize Fields
        List<Character> players = new ArrayList<>();
        List<Character> enemies = new ArrayList<>();
        players.add(new Warrior());
        enemies.add(new Warrior());
        handler = new HostEventHandler(players,enemies,null);
        playerNames = handler.getPlayerNames();
        enemyNames = handler.getEnemyNames();
        user = players.get(0);
        moveNames = user.getMoveNames();

        // Text to be displayed in the relay_box at the top of the screen.
        final TextView relayText = (TextView) findViewById(R.id.relay_box);

        // The stage in which to draw to.
        // SurfaceView stageView = (SurfaceView) findViewById(R.id.stage);

        /**
         * Button 1 is used for:
         *   Event         Attack action
         *   Event         Target option
         *   Event         Ability option
         *   Start Screen  Host action
         *   Story         First option
         *
         */
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // Attack action - click to choose
                if(currentText.equals("Attack")){
                    moveSelected = "Attack";
                    //loadTargets();
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    //loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    //loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        /**
         * Button 2 is used for:
         *   Event         Ability action
         *   Event         Target option
         *   Event         Ability option
         *   Start Screen  Join action
         *   Story         Second option
         *
         */
        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // Ability action - click to choose
                if(currentText.equals("Ability")){
                    //loadAbilities();
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    //loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    //loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        /**
         * Button 3 is used for:
         *   Event         End Turn action
         *   Event         Target option
         *   Event         Ability option
         *
         */
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // End Turn - ends turn so that the action chosen may be performed
                if(currentText.equals("End Turn")){
                    if(moveSelected != null && targetName != null){
                        String moveUsed = user.getName() + "##" + moveSelected + "##" + targetName;
                        relayText.setText(handler.useMove(user.getName(),moveSelected,targetName));
                        moveSelected = null;
                        targetName = null;
                    }
                    else{
                        relayText.setText("Please select a move.");
                        // Grey out button
                    }
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    //loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    //loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        /**
         * Button 4 is used for:
         *   Event         Defend action
         *   Event         Target option
         *   Event         Ability option
         *
         */
        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // Defend - click to choose
                if(currentText.equals("Defend")){
                    moveSelected = "Defend";
                    targetName = "Not Null";
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    //loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    //loadDefaultButtons();
                    // Activate button color
                }
            }
        });
    }


    /**
     * Android Activity default method
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_ui, menu);
        return true;
    }

    /**
     * Android Activity default method
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Activity controls from superclass
     */
    @Override
    protected void onStart() { super.onStart(); }
    @Override
    protected void onRestart() { super.onRestart(); }
    @Override
    protected void onResume() { super.onResume(); }
    @Override
    protected void onPause() { super.onPause(); }
    @Override
    protected void onStop() { super.onStop(); }
    @Override
    protected void onDestroy() { super.onDestroy(); }

    /**
     * Sets each button to the targets name the list of target names
     */
//    private void loadTargets()
//    {
//        // get a list of the buttons
//        ArrayList<Button> buttons = getButtonList();
//
//        // loop through all enemy names for button text
//        int i = 0;
//        for(; i < enemyNames.size(); i ++)
//        {
//            buttons.get(i).setText(enemyNames.get(0));
//        }
//
//        // loop through all friendly names for button text
//        for(int j = 0; j < playerNames.size(); j++)
//        {
//            buttons.get(i).setText(playerNames.get(j));
//            i++;
//        }
//
//        // check if all buttons were wiped of text
//         for(; i < buttons.size(); i++)
//         {
//                buttons.get(i).setText("");
//         }
//    }
//
//    /**
//     * Sets the text in the four buttons to the ability names for the character
//     */
//    private void loadAbilities()
//    {
//        // get a list of all the buttons
//        List<Button> buttons = getButtonList();
//
//        // loop through all buttons and set the text
//        for(int i = 0; i < buttons.size(); i++)
//        {
//            if(i < moveNames.size())
//                buttons.get(i).setText(moveNames.get(i));
//        }
//    }
//
//    /**
//     * Loads the default text back into the four buttons
//     */
//    private void loadDefaultButtons()
//    {
//        // get a reference to the buttons
//        ArrayList<Button> buttons = getButtonList();
//
//        // set each default text
//        buttons.get(0).setText("Attack");
//        buttons.get(1).setText("Ability");
//        buttons.get(2).setText("End Turn");
//        buttons.get(3).setText("Defend");
//
//    }
//
//    /**
//     * Gets a list of the four buttons we need references to
//     * @return ArrayList of buttons
//     */
//    private ArrayList<Button> getButtonList()
//    {
//        // get a reference to the buttons
//        Button button1 = (Button)findViewById(R.id.button1);
//        Button button2 = (Button)findViewById(R.id.button2);
//        Button button3 = (Button)findViewById(R.id.button3);
//        Button button4 = (Button)findViewById(R.id.button4);
//
//        // save the buttons to a list
//        ArrayList<Button> buttons = new ArrayList<Button>();
//        buttons.add(button1);
//        buttons.add(button2);
//        buttons.add(button3);
//        buttons.add(button4);
//
//        return buttons;
//    }
}
