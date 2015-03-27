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

import JavaFiles.*;


public class EventUI extends ActionBarActivity {

    String targetName;
    String moveSelected;
    ArrayList<String> playerNames;
    ArrayList<String> enemyNames;
    ArrayList<String> moveNames;
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
        //moveNames = user.getMoveNames();

        // View variables
        final TextView relayText = (TextView) findViewById(R.id.relay_box);



        // Capture buttons from layout
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // performAction()
                String currentText = ((TextView)v).getText().toString();

                // Attack
                if(currentText.equals("Attack")){
                    // loadTargets()
                }
                else if(moveNames.contains(currentText)){
                    // loadTargets()
                    moveSelected = currentText;
                }
                else{
                    currentText = targetName;
                    // defaultButtons()
                }

                relayText.setText("You've clicked Button 1");
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // performAction()
                String currentText = ((TextView)v).getText().toString();

                // Ability
                if(currentText.equals("Ability")){
                    // loadAbilities()
                }
                else if(moveNames.contains(currentText)){
                    // loadTargets()
                    moveSelected = currentText;
                }
                else{
                    currentText = targetName;
                    // defaultButtons()
                }
            }
        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // performAction()
                String currentText = ((TextView)v).getText().toString();

                // End Turn
                if(currentText.equals("End Turn")){
                    if(moveSelected != null && targetName != null){
                        //relayText.setText(handler.useMove(user,moveSelected,targetName));
                        moveSelected = null;
                        targetName = null;
                    }
                    else{
                        relayText.setText("Please select a move.");
                    }
                }
                else if(moveNames.contains(currentText)){
                    // loadTargets()
                    moveSelected = currentText;
                }
                else{
                    currentText = targetName;
                    // defaultButtons()
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
                    // loadTargets()
                    moveSelected = currentText;
                }
                else{
                    currentText = targetName;
                    // defaultButtons()
                }
                relayText.setText("You've clicked Button 4");
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

}
