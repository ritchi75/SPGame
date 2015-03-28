package seniorproject.game;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import JavaFiles.*;
import JavaFiles.Characters.Warrior;


public class EventUI extends ActionBarActivity {

    String targetName;
    String moveSelected;
    ArrayList<String> playerNames;
    ArrayList<String> enemyNames;
    List<String> moveNames;
    JavaFiles.Character user;
    JavaFiles.EventHandler handler;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ui);

        playerNames = new ArrayList<>();
        enemyNames = new ArrayList<>();
        //user = new Warrior();
        moveNames = user.getMoveNames();
        //enemyNames.add("lol");
        //playerNames.add("p1");
        //playerNames.add("p2");

        // View variables
        final TextView relayText = (TextView) findViewById(R.id.relay_box);



        // Capture buttons from layout
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentText = ((TextView)v).getText().toString();

                // Attack
                if(currentText.equals("Attack")){
                    moveSelected = "Attack";
                    loadTargets();
                }
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String currentText = ((TextView)v).getText().toString();

                // Ability
                if(currentText.equals("Ability")){
                    loadAbilities();
                }
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String currentText = ((TextView)v).getText().toString();

                // End Turn
                if(currentText.equals("End Turn")){
                    if(moveSelected != null && targetName != null){
                        relayText.setText(handler.useMove(user,moveSelected,targetName));
                        moveSelected = null;
                        targetName = null;
                    }
                    else{
                        relayText.setText("Please select a move.");
                        // Grey out button
                    }
                }
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String currentText = ((TextView)v).getText().toString();

                // Defend
                if(currentText.equals("Defend")){
                    moveSelected = "Defend";
                    targetName = "Not Null";
                }
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });
    }


    /**
     *
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
     *
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
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // sets each button to the targets name the list of target names
    private void loadTargets()
    {
        // get a list of the buttons
        ArrayList<Button> buttons = getButtonList();

        // loop through all enemy names for button text
        int i = 0;
        for(; i < enemyNames.size(); i ++)
        {
            buttons.get(i).setText(enemyNames.get(0));
        }

        // loop through all friendly names for button text
        for(int j = 0; j < playerNames.size(); j++)
        {
            buttons.get(i).setText(playerNames.get(j));
            i++;
        }

        // check if all buttons were wiped of text
         for(; i < buttons.size(); i++)
         {
                buttons.get(i).setText("");
         }
    }

    // sets the text in the four buttons to the ability names for the character
    private void loadAbilities()
    {
        // get a list of all the buttons
        List<Button> buttons = getButtonList();

        // loop through all buttons and set the text
        for(int i = 0; i < buttons.size(); i++)
        {
            if(i < moveNames.size())
                buttons.get(i).setText(moveNames.get(i));
        }
    }

    // loads the default text back into the four buttons
    private void loadDefaultButtons()
    {
        // get a reference to the buttons
        ArrayList<Button> buttons = getButtonList();

        // set each default text
        buttons.get(0).setText("Attack");
        buttons.get(1).setText("Ability");
        buttons.get(2).setText("End Turn");
        buttons.get(3).setText("Defend");

    }

    // gets a list of the four buttons we need references to
    private ArrayList<Button> getButtonList()
    {
        // get a reference to the buttons
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);

        // save the buttons to a list
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);

        return buttons;
    }
}
