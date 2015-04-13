package seniorproject.game;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import JavaFiles.Bluetooth.BluetoothService;
import JavaFiles.Bluetooth.Constants;
import JavaFiles.Characters.*;
import JavaFiles.Characters.Character;
import JavaFiles.Events.EventHandler;
import JavaFiles.Events.HostEventHandler;
import JavaFiles.Stories.Stories;
import JavaFiles.Stories.Story;

/**
 * ----------READ ME----------
 * As of now this only allows for a two way communication.  4 way communication seems to be a bit more complicated and is a work in progress.
 * One problem with this app is once another activity is open the connection seems to be lost.  I need to solve this problem first.
 * Meanwhile I set it up so that as long as your within this activity you should be able to successfully pass Strings between two devices.
 * All you need to do is call the 'sendMessage()' method and pass in the desired String within the 'send String' set action listener method.
 * This way you can at least test features of our game as I begin to work on improving the bluetooth capabilities.
 * ----------READ ME----------
 */
public class MainActivity extends ActionBarActivity {

    Context context;
    private static final String LOG = "Matt";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private BluetoothAdapter mBluetoothAdapter = null;
    private StringBuffer mOutStringBuffer;
    private BluetoothService mBluetoothService = null;
    private String mConnectedDeviceName = null;


    TextView mainTextView;
    TextView sentMessage;
    TextView findTextView;
    TextView textView;
    TextView title;

    TextView relay_box;
    TextView enemyHp;
    TextView playerhp;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    //SurfaceView stage;
    ImageView mainImage;

    int layer = 0;
    int story = 0;
    private String messageRead = "";
    Stories stories;
    ArrayList<Integer> storyImages = new ArrayList<Integer>(); //Current string of story images.
    boolean win = true;

    private String targetName;
    private String moveSelected;
    private ArrayList<String> playerNames;
    private ArrayList<String> enemyNames;
    private List<String> moveNames;
    private Character user;
    private EventHandler handler;

    Button host;
    Button join;
    Button sendString;

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
        //Otherwise keep connecting
        //  if (mBluetoothService == null){
        setOnClickListeners();
        startActivity(0);
        // Intent intent = new Intent(MainActivity.this, activity_test.class);
        // startActivity(intent);
        //  }
    }

    public void setOnClickListeners() {
        Log.d("MATT", "setOnClickListeners()");
        button1.setOnClickListener(new View.OnClickListener() { //The variable name is misleading, make the current device discoverable.
            public void onClick(View v) {
                switch (layer) {
                    case 0: //find devices
                        makeDiscoverable();
                        break;
                    case 1: //Story point 1
                        if(story != 3) {
                            if(story != 0) {
                                story--;
                                mainImage.setImageResource(storyImages.get(story));
                            }
                        }
                        else {
                            story = 0;
                            Log.d(LOG, "READ");
                            button1.setBackgroundColor(Color.LTGRAY);//Imitate a toggle button.
                            button2.setBackgroundColor(Color.DKGRAY);
                            sendMsg("STORYPOINT1"); //Tell other device you chose an option.
                            if (messageRead.equals("STORYPOINT1")) { //Did you read in a message(did the other player pick?)
                                messageRead = "";
                                Log.d(LOG, messageRead);
                                button2.setBackgroundColor(Color.LTGRAY);
                                startActivity(2); //Start event Activity
                            } else if (messageRead.equals("STORYPOINT2")) { //You both disagree, just start the first option.
                                messageRead = "";
                                button2.setBackgroundColor(Color.LTGRAY);
                                startActivity(2);
                            }
                        }
                        break;
                    case 2: //Attack
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
                                startActivity(2); //Start event Activity
                            } else if (messageRead.equals("STORYPOINT1")) {
                                messageRead = "";
                                startActivity(2);
                            }
                            //startActivity(2);
                        }
                        break;

                    case 2: //Ability
                        break;
                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //End turn
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Defend
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

    public void init() {
        //Connect Activity
       /* mainTextView = (TextView) findViewById(R.id.mainTextView);
        sentMessage = (TextView) findViewById(R.id.sentMessage);
        host = (Button) findViewById(R.id.host);
        join = (Button) findViewById(R.id.join);
        sendString = (Button) findViewById(R.id.sendString);
        findTextView = (TextView) findViewById(R.id.findTextView);
        textView = (TextView) findViewById(R.id.textView);
        title = (TextView) findViewById(R.id.title);*/

        stories = new Stories();
        //Event UI
        relay_box = (TextView) findViewById(R.id.relay_box);
        enemyHp = (TextView) findViewById(R.id.enemyhp);
        playerhp = (TextView) findViewById(R.id.playerhp);
        button1 = (Button) findViewById(R.id.button1); //host
        button2 = (Button) findViewById(R.id.button2); //join
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        //stage = (SurfaceView) findViewById(R.id.stage);
        mainImage = (ImageView) findViewById(R.id.mainImage);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetoothService = new BluetoothService(context, mHandler);
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        context = this;
    }

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
        switch (toLayer) {
            case 0: //The connect activity
                relay_box.setVisibility(View.VISIBLE);
                relay_box.setText("ADVENTURE PALS!");
                button1.setVisibility(View.VISIBLE); //"Find Devices"
                button1.setText("Find Devices");
                button2.setVisibility(View.VISIBLE); //"Join Devices"
                button2.setText("Join");
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
                break;
            case 2: //The boss activity
                relay_box.setVisibility(View.VISIBLE);
                enemyHp.setVisibility(View.VISIBLE);
                playerhp.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                button1.setText("ATTACK");
                button2.setVisibility(View.VISIBLE);
                button2.setText("ABILITY");
                button3.setVisibility(View.VISIBLE);
                button3.setText("END TURN");
                button4.setVisibility(View.VISIBLE);
                button4.setText("DEFEND");
                //stage.setVisibility(View.VISIBLE);
                mainImage.setVisibility(View.VISIBLE);
                sendMsg("start2"); //Make sure the other device is on the same activity.
                startBossActivity();
                break;
            case 3: //The win activity
                relay_box.setVisibility(View.VISIBLE);
                mainImage.setVisibility(View.VISIBLE);
                startEndGameActivity();

                startActivity(0);
                break;
        }
    }

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

    public void startBossActivity() {

    }

    public void startEndGameActivity() {
        //This should just reset all character values.
        if(win == true)
            mainImage.setImageResource(R.drawable.game_win);
        else{
            mainImage.setImageResource(R.drawable.game_lose);

        }

    }

    public void readMessage(String message) {
        messageRead=message;
        //force an activity change:
        switch(messageRead)
        {
            case "start1":
                startActivity(1);
                break;
            case "start2":
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
        if (handler instanceof HostEventHandler) {
            // keep of a list of the strings to be used to update the UI
            List<String> results = new ArrayList<String>();

            // wait until we receive another move
            while (messageRead.equals("")) {
            }
            // get the string from the other bluetooth connection if there is one }
            String otherPlayerMove = messageRead;

            // clear out the message read
            messageRead = "";

            // get the move that the boss will use
            String bMove = handler.getBossMove();

            // when movesThisTurn has all 3 moves, parse them out:
            String[] hostMove = bluetoothMove.split("##");
            String[] playerTwoMove = otherPlayerMove.split("##");
            String[] bossMove = bMove.split("##");

            // Host first, then player two, then boss
            String hostsMove = handler.useMove(hostMove[0], hostMove[1], hostMove[2]);
            String playerTwosMove = handler.useMove(playerTwoMove[0], playerTwoMove[1], playerTwoMove[2]);
            String bossesMove = handler.useMove(bossMove[0], bossMove[1], bossMove[2]);

            // set relay text to hostsMove
            this.relay_box.setText(hostsMove);

            // send message to player two
            sendMsg(playerTwosMove);

            // do end turn logic
            // 0 = battle still going
            // 1 = victory
            // 2 = defeat
            int eventStatusCode = handler.endTurn();
            // send the status code to the other player
            sendMsg(Integer.toString(eventStatusCode));
            // return the status code
            return handler.endTurn();

        }
        else //player is not host
        {
            // send our move to the host
            sendMsg(bluetoothMove);
            // wait for a response back from the host
            while(messageRead.equals(""));

            // set our relay text to the read message
            this.relay_box.setText(messageRead);

            // clear out message read
            messageRead = "";

        }
        // wait till we recieve a message from the host
        while(messageRead.equals(""));

        int battleStatusCode = Integer.parseInt(messageRead);
        messageRead = "";

        return battleStatusCode;
    }

    //<------------------------------BLUETOOTH STUFF PAST THIS LINE------------------------------>

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
     * Establish connection with other divice
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