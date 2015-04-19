package seniorproject.game;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import JavaFiles.Bluetooth.BluetoothService;
import JavaFiles.Bluetooth.Constants;
import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;
import JavaFiles.Events.ClientEventHandler;
import JavaFiles.Events.EventHandler;
import JavaFiles.Events.HostEventHandler;
import JavaFiles.Stories.Stories;
import JavaFiles.Stories.Story;

/**
 * Adventure Pals is a bluetooth game which consists of three modules.
 * The first module is the connection screen, this is the first screen the user sees.
 * The second screen shows the story, which will lead up to a battle
 * The third screen will be the battle itself, once the battle has ended it will go to the second module.
 */
public class MainActivity extends ActionBarActivity {

    Context context;
    private static final String LOG = "Matt"; //Solely used to debug our code.
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private BluetoothAdapter mBluetoothAdapter = null;
    private StringBuffer mOutStringBuffer;
    private BluetoothService mBluetoothService = null;
    private String mConnectedDeviceName = null;

    // XML Layout TextView, ImageView, and Button objects
    TextView mainTextView;
    TextView relay_box;
    TextView enemyHp;
    TextView playerhp;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    // StageView works on one phone, but not both while connected via Bluetooth
    //SurfaceView stage;
    ImageView mainImage;

    // Music Players for loading music for its corresponding screen
    MediaPlayer fightMP3 = new MediaPlayer();
    MediaPlayer mainMP3 = new MediaPlayer();
    MediaPlayer otherMP3 = new MediaPlayer();

    int layer = 0; //The current activity the device is on.
    int story = 0;
    private String messageRead = ""; //The message read in from the other phone.
    Stories stories; //A list of all the stories in the game.
    ArrayList<Integer> storyImages = new ArrayList<Integer>(); //Current string of story images used in the story module..
    boolean win = true; //Did the player win?

    //Event UI Fields
    private String targetName;
    private String moveSelected;
    private ArrayList<String> playerNames;
    private ArrayList<String> enemyNames;
    private List<String> moveNames;
    private Character user;
    private Character playerTwo;
    private EventHandler handler;
    String bossChosen;
    private boolean sentMsg;

    // Character Data
    private ArrayList<Character> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ui);
        init();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            mainTextView.setText("Bluetooth is not supported");
        }
        //If bluetooth is not on request it to be enabled.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT); //REQUEST_ENABLE_BT is a field.
        }
        //  if (mBluetoothService == null){
        setOnClickListeners();
        startActivity(0);
    }

    /**
     * Sets on click listeners
     */
    public void setOnClickListeners() {
        Log.d("MATT", "setOnClickListeners()");
        button1.setOnClickListener(new View.OnClickListener() { //The variable name is misleading, make the current device discoverable.
            public void onClick(View v) {
                switch (layer) {
                    case 0: //find devices
                        makeDiscoverable();
                        break;
                    case 1: //Story point 1(The previous button)
                        if(story != 3) { //Once the user is at the end of the string of picture texts it should go to the else statement
                            if(story != 0) {
                                story--;
                                mainImage.setImageResource(storyImages.get(story));
                            }
                        }
                        else { //Makes the button send the user to the next activity.
                            story = 0;
                            Log.d(LOG, "READ");
                            button1.setBackgroundColor(Color.LTGRAY);//Imitate a toggle button.
                            button2.setBackgroundColor(Color.DKGRAY);
                            sendMsg("STORYPOINT1"); //Tell other device you chose an option.
                            if (messageRead.equals("STORYPOINT1")) { //Did you read in a message(did the other player pick?)
                                messageRead = "";
                                Log.d(LOG, messageRead);
                                button2.setBackgroundColor(Color.LTGRAY);
                                bossChosen = "Robot";
                                startActivity(2); //Start event Activity

                            } else //(messageRead.equals("STORYPOINT2")) { //You both disagree, just start the first option.
                            {
                                messageRead = "";
                                button2.setBackgroundColor(Color.LTGRAY);
                                bossChosen = "Squiggle";
                                startActivity(2);
                            }
                        }
                        break;
                    case 2: //Attack
                        // Get button text
                        String currentText = ((TextView)v).getText().toString();

                        // Attack action - click to choose
                        if(currentText.equals("Attack")){
                            Log.i(LOG, "ATTACK");
                            moveSelected = "Attack";
                            loadTargets();
                        }
                        // Ability option - click to choose
                        else if(moveNames.contains(currentText)){
                            moveSelected = currentText;
                            loadTargets();
                        }
                        // Target option - click to choose
                        else{
                            targetName = currentText;
                            loadDefaultButtons();
                            // Activate button color
                        }
                        break;

                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { //Call an intent to bring up a listView of paired devices.
            public void onClick(View v) {
                switch (layer) {
                    case 0: //Join

                        Intent serverIntent = new Intent(context, DeviceListActivity.class);
                        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                        break;
                    case 1: //Story point 2
                        if(story != 3) {
                            story++;
                            if(story == 3) {
                                button1.setText("Choose 1");
                                button2.setText("Choose 2");
                            }
                            mainImage.setImageResource(storyImages.get(story));
                        }
                        else {
                            story--;
                            Log.d(LOG, messageRead);
                            sendMsg("STORYPOINT2"); //Tell other device you chose an option.
                            button2.setBackgroundColor(Color.LTGRAY);//Imitate a toggle button.
                            button1.setBackgroundColor(Color.DKGRAY);

                            if (messageRead.equals("STORYPOINT2")) { //Did you read in a message(did the other player pick?){
                                messageRead = "";
                                Log.d(LOG, messageRead);
                                button1.setBackgroundColor(Color.LTGRAY);
                                bossChosen = "Robot";
                                startActivity(2); //Start event Activity
                               sendMsg("start2"); //Make sure the other device is on the same activity.
                            } else if (messageRead.equals("STORYPOINT1")) {
                                messageRead = "";
                                button1.setBackgroundColor(Color.LTGRAY);
                                startActivity(2); //Start event activity
                                bossChosen = "Squiggle";
                                startActivity(2);
                                sendMsg("start2"); //Make sure the other device is on the same activity.
                            }
                            //startActivity(2);
                        }
                        break;

                    case 2: //Ability
                        // Get button text
                        String currentText = ((TextView)v).getText().toString();

                        // Ability action - click to choose
                        if(currentText.equals("ABILITY")){
                            Log.i(LOG, "ABILITY");

                            loadAbilities();
                        }
                        // Ability option - click to choose
                        else if(moveNames.contains(currentText)){
                            moveSelected = currentText;
                            loadTargets();
                        }
                        // Target option - click to choose
                        else{
                            targetName = currentText;
                            loadDefaultButtons();
                            // Activate button color
                        }
                        break;
                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //End Turn
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // End Turn - ends turn so that the action chosen may be performed
                if(currentText.equals("END TURN")){
                    if(moveSelected != null && targetName != null){
                        Log.i(LOG, "END TURN");
                        String moveUsed = user.getName() + "##" + moveSelected + "##" + targetName;
                        int statusCode = useMoveBluetooth(moveUsed);
                        if(statusCode == 1)
                        {
                            // player won, go to next story
                            relay_box.setText("You Win!");
                        }
                        else if(statusCode == 2)
                        {
                            //player lost, go to main menu?
                            relay_box.setText("You lose :(");
                        }
                        moveSelected = null;
                        targetName = null;
                    }
                    else{
                        relay_box.setText("Please select a move.");
                        // Grey out button
                    }
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Defend
                // Get button text
                String currentText = ((TextView)v).getText().toString();

                // Defend - click to choose
                if(currentText.equals("Defend")){
                    Log.i(LOG, "DEFEND");
                    moveSelected = "Defend";
                    targetName = user.getName();
                }
                // Ability option - click to choose
                else if(moveNames.contains(currentText)){
                    moveSelected = currentText;
                    loadTargets();
                }
                // Target option - click to choose
                else{
                    targetName = currentText;
                    loadDefaultButtons();
                    // Activate button color
                }
            }
        });
    /*    sendString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Matt", "TEST");
                sendMessage("Hello World!");
            }
        });*/
    }

    /**
     * Initialize method - creates instances of all objects and variables used
     */
    public void init() {
        stories = new Stories(); //initializes a new story object which contains a list of all the stories in the game.
        //Event UI

        //Button initialization
        relay_box = (TextView) findViewById(R.id.relay_box);
        enemyHp = (TextView) findViewById(R.id.enemyhp);
        playerhp = (TextView) findViewById(R.id.playerhp);
        button1 = (Button) findViewById(R.id.button1); //host
        button2 = (Button) findViewById(R.id.button2); //join
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        //stage = (SurfaceView) findViewById(R.id.stage);
        mainImage = (ImageView) findViewById(R.id.mainImage);

        // Music Player
        fightMP3 = MediaPlayer.create(this, R.raw.fight);
        mainMP3 = MediaPlayer.create(this, R.raw.main);
        otherMP3 = MediaPlayer.create(this, R.raw.other);


        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetoothService = new BluetoothService(context, mHandler);
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        context = this;
    }

    /**
     * The startActivity method switches from the current activity (or module) to the next module
     * according to what happens next in the game. Once one phone switches, it tells the other
     * phone it's connected to to switch to the same module.
     * @param toLayer  Switch to another layer. 1 is the Story Module, and 2 is the Event Module.
     *                 0 is the Connect Module, but you have no need to switch to it. 3 is the
     *                 Win Module where you WIN!
     */
    public void startActivity(int toLayer) {
        relay_box.setVisibility(View.GONE);
        enemyHp.setVisibility(View.GONE);
        playerhp.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        //stage.setVisibility(View.GONE);
        mainImage.setVisibility(View.GONE);
        layer = toLayer;
        // End all music
        if(mainMP3.isPlaying()){ mainMP3.stop(); }
        if(fightMP3.isPlaying()){ fightMP3.stop(); }
        if(otherMP3.isPlaying()){ otherMP3.stop(); }
        switch (toLayer) {
            case 0: //The connect activity
                relay_box.setVisibility(View.VISIBLE);
                relay_box.setText("ADVENTURE PALS!");
                button1.setVisibility(View.VISIBLE); //"Find Devices"
                button1.setText("Find Devices");
                button2.setVisibility(View.VISIBLE); //"Join Devices"
                button2.setText("Join");
                // Begin main screen music & loop
                mainMP3.setLooping(true);
                mainMP3.start();
                break;
            case 1: //The story activity
                relay_box.setVisibility(View.VISIBLE); //"Title"
                //stage.setVisibility(View.VISIBLE); //The stage
                mainImage.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE); //Choice 1
                button1.setText("Previous");
                button2.setVisibility(View.VISIBLE); //Choice 2
                button2.setText("Next");
                //sendMessage("start1"); //Make sure the other device is on the same activity.
                startStoryActivity();
                // Begin main screen music & loop
                otherMP3.setLooping(true);
                otherMP3.start();
                break;
            case 2: //The boss activity
                relay_box.setVisibility(View.VISIBLE);
                enemyHp.setVisibility(View.VISIBLE);
                playerhp.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                button1.setText("Attack");
                button2.setVisibility(View.VISIBLE);
                button2.setText("ABILITY");
                button3.setVisibility(View.VISIBLE);
                button3.setText("END TURN");
                button4.setVisibility(View.VISIBLE);
                button4.setText("Defend");
                //stage.setVisibility(View.VISIBLE);
                mainImage.setVisibility(View.VISIBLE);
                //sendMsg("start2"); //Make sure the other device is on the same activity.
                startBossActivity();
                fightMP3.setLooping(true);
                fightMP3.start();
                break;
            case 3: //The win activity
                relay_box.setVisibility(View.VISIBLE);
                mainImage.setVisibility(View.VISIBLE);
                startEndGameActivity();
                sendMsg("start3");
                startActivity(0);
                mainMP3.setLooping(true);
                mainMP3.start();
                break;
        }
    }

    /**
     * Grabs a new story from the stories class and loads it into the storyimages list to be used to display in the story module.
     */
    public void startStoryActivity() {
        storyImages.clear(); //Get a new story.
        ArrayList<Story> storyBook = stories.getStories(); //A list of all stories in the game.
        if(storyBook.isEmpty()) {
            startActivity(3);
        }
        else {
            Random random = new Random();
            int rand = random.nextInt(storyBook.size()); //Pick a random story.
            Story temp = storyBook.get(rand);
            storyImages = temp.getImage(); //Fill the story arraylist with images.
            relay_box.setText(temp.getTitle());
            mainImage.setImageResource(storyImages.get(0)); //Show the first image.
            stories.removeStory(temp); //Remove the story from the story book so we don't repeat.
        }
    }

    // starts a fight event
    public void startBossActivity() {

        players = new ArrayList<Character>();
        Random rng = new Random();
        int characterNumber = rng.nextInt(4);

        if(characterNumber == 0)
        {
            user = new Warrior();
        }
        else if(characterNumber == 1)
        {
            user = new Wizard();
        }
        else if(characterNumber == 2)
        {
            user = new Rogue();
        }
        else
        {
            user = new Monk();
        }
        players.add(user);


        loadBossImage();

        this.handler = new HostEventHandler(players, bossChosen);
        this.playerNames = handler.getPlayerNames();
        this.moveNames = user.getMoveNames();
        this.enemyNames = handler.getEnemyNames();
    }

    /*
    *   Displays the image for the appropriate boss depending on user character and bossChose
     */
    private void loadBossImage()
    {
        if(bossChosen.equals("Squiggle"))
        {
            if(user instanceof Warrior || user instanceof Monk)
            {
                this.mainImage.setImageResource(R.drawable.km_squiggle);
            }
            else
            {
                this.mainImage.setImageResource(R.drawable.wr_squiggle);
            }
        }
        else if(bossChosen.equals("Empress"))
        {
            if(user instanceof Warrior || user instanceof Monk)
            {
                this.mainImage.setImageResource(R.drawable.km_trueempress);
            }
            else
            {
                this.mainImage.setImageResource(R.drawable.wr_trueempress);
            }
        }
        else if(bossChosen.equals("Robot"))
        {
            if(user instanceof Warrior || user instanceof Monk)
            {
                this.mainImage.setImageResource(R.drawable.km_robot);
            }
            else
            {
                this.mainImage.setImageResource(R.drawable.wr_robot);
            }
        }
        else if(bossChosen.equals("Skeleton"))
        {
            if(user instanceof Warrior || user instanceof Monk)
            {
                this.mainImage.setImageResource(R.drawable.km_skeleton);
            }
            else
            {
                this.mainImage.setImageResource(R.drawable.wr_skeleton);
            }
        }
        else //ghost
        {
            if(user instanceof Warrior || user instanceof Monk)
            {
                this.mainImage.setImageResource(R.drawable.km_ghost);
            }
            else
            {
                this.mainImage.setImageResource(R.drawable.wr_ghost);
            }
        }
    }

    /**
     * Displays whether you one or lost the game.
     */
    public void startEndGameActivity() {
        //This should just reset all character values.
        if(win == true)
            mainImage.setImageResource(R.drawable.game_win);
        else{
            mainImage.setImageResource(R.drawable.game_lose);

        }

    }

    //------------------------------------------------------
    //----------------- EventUI Methods --------------------
    //------------------------------------------------------
    /**
     * Sets each button to the targets name the list of target names
     */
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

    /**
     * Sets the text in the four buttons to the ability names for the character
     */
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

    /**
     * Loads the default text back into the four buttons
     */
    private void loadDefaultButtons()
    {
        Log.i("loadDeafultButtons", "ALAX");
        // get a reference to the buttons
        ArrayList<Button> buttons = getButtonList();

        // set each default text
        buttons.get(0).setText("Attack");
        buttons.get(1).setText("ABILITY");
        buttons.get(2).setText("END TURN");
        buttons.get(3).setText("Defend");
    }

    /**
     * Gets a list of the four buttons we need references to
     * @return ArrayList of buttons
     */
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

    //Gets called when a message is read.
    public void readMessage(String message) {
        messageRead=message; //This is used throughout the application.
        //force an activity change:
        switch(messageRead)
        {
            case "start1":
                if(layer != 1) //Only change if the device is not on the same activity
                    startActivity(1);
                break;
            case "start2":
                if(layer != 2) //Only change if the device is not on the same activity
                    startActivity(2);
                break;
            case "start3":
                if(layer != 3) //Only change if the device is not on the same activity
                    startActivity(3);
                bossChosen = "Squiggle";
                startActivity(2);
                break;
        }
    }

    // uses bluetooth to wait for all inputs then activate them in the proper order
    // 0 = battle still ongoing
    // 1 = victory
    // 2 = lost
    public int useMoveBluetooth(String bluetoothMove) {
        // if the player is host
            // keep of a list of the strings to be used to update the UI
            List<String> results = new ArrayList<String>();


            // get the move that the boss will use
            String bMove = handler.getBossMove();

            // when movesThisTurn has both moves, parse them out:
            String[] hostMove = bluetoothMove.split("##");
            String[] bossMove = bMove.split("##");

            // Host first, then player two, then boss
            MoveOutcome hostsMove = handler.useMove(hostMove[0], hostMove[1], hostMove[2]);
            MoveOutcome bossesMove = handler.useMove(bossMove[0], bossMove[1], bossMove[2]);

            // set relay text to hostsMove
            this.relay_box.setText(hostsMove.getOutcomeMessage());
            // update the boss's hp
            int bossHP = handler.getBossHP();
            this.enemyHp.setText(bossHP + "");
            this.playerhp.setText(handler.getPlayers().get(0).getHP() + "");


            // do end turn logic
            // 0 = battle still going
            // 1 = victory
            // 2 = defeat
            int eventStatusCode = handler.endTurn();

            // return the status code
            return handler.endTurn();

        }


    //<------------------------------BLUETOOTH STUFF PAST THIS LINE------------------------------>

    /**
     * sends a message to the other device.
     * @param message The message to be sent.
     */
    private void sendMsg(String message) {
        Log.d(LOG, "sendMessage(" + message + ")");

        /*// Check that we're actually connected before trying anything
        if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }*/

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String handler = "Handler1";
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.d(handler, "STATE_CONNECTED");

                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Log.d(handler, "STATE_CONNECTING");
                            break;
                        case BluetoothService.STATE_LISTEN:
                            Log.d(handler, "STATE_LISTEN");
                            break;
                        case BluetoothService.STATE_NONE:
                            Log.d(handler, "STATE_NONE");
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    Log.d(handler, "MESSAGE_WRITE");
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Toast.makeText(context, "Sent: " + writeMessage, Toast.LENGTH_SHORT);
                    break;
                case Constants.MESSAGE_READ:
                    Log.d(handler, "MESSAGE_READ");
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    readMessage(readMessage);
                    Toast.makeText(context, "Received: " + readMessage, Toast.LENGTH_SHORT);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:

                    Log.d(handler, "MESSAGE_DEVICE_NAME");
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != context) {
                        Toast.makeText(context, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(MainActivity.this, activity_test.class);
                        //startActivity(intent);
                        //We are connected, hide this activity.
                        //sendMsg("start1");
                        /*Random rng = new Random();
                        boolean done = false;
                        int ourNumber;
                        int theirNumber;

                         ourNumber = rng.nextInt(100000000);
                         sendMsg(String.valueOf(ourNumber));
                         theirNumber = Integer.parseInt(messageRead);*/



                        startActivity(1);                                       //<--START-ACTIVITY-->
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    Log.d(handler, "MESSAGE_TOAST");
                    /*if (null != context) {
                        Toast.makeText(context, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }*/
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) { //Seems to only be called from the list adapter
        Log.d(LOG, "onActivityResult");

        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so start the new activity
                    //Intent intent = new Intent(MainActivity.this, activity_test.class);
                    //startActivity(intent);
                    //Temporarily reveal a button for test purposes only
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(LOG, "BT not enabled");
                    //Toast.makeText(context, R.string.bt_not_enabled_leaving,
                    //       Toast.LENGTH_SHORT).show();
                    //context.finish();
                }
        }
    }

    /**
     * Establish connection with other device
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        Log.d(LOG, "connectDevice");
        Log.d(LOG, data.toString());

        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothService.connect(device, secure);
    }



    private void makeDiscoverable() {
        Log.d(LOG, "ensureDiscoverable()");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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


}