package seniorproject.game;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {

    TextView test;
    Button disconnect;

    public final int REQUEST_ENABLE_BT = 1;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final String NAME = "bluetooth";
    BluetoothAdapter mBluetoothAdapter;
    private String temp = "";
    public final int DISCOVERY_REQUEST = 1;
    public String toastText ="";
    private BluetoothDevice remoteDevice;
    BroadcastReceiver discoveryResult; //This is used in the 'findDevices' method to discover devices.
    Set<BluetoothDevice> devicesArray; //The Set of devices that this device has previously connected to.
    ArrayList<String> pairedDevices; //Will hold a string of the paired devices.  Not needed
    ArrayList<String> discoveredDevices; //Will hold a string of the discovered devices.  Not needed
    IntentFilter filter;
    BroadcastReceiver receiver;

    // Create a BroadcastReceiver for ACTION_FOUND
    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);

            if(state == BluetoothAdapter.STATE_ON) {
                Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_SHORT).show();
            }
            /*String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }*/
        }
    };
    // Register the BroadcastReceiver
    //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    //registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if(mBluetoothAdapter == null) {
            test.setText("This device does not support bluetooth");
        }

        if(!mBluetoothAdapter.isEnabled()) {
            test.setText("Bluetooth is off.");
        }
        else {
            test.setText("Bluetooth is on.");
        }

        getPairedDevices(); //Try to find devies which the device has previously connected to.
        startDiscovery(); //Now find other devies.
        /*
        *  NOTE: You do not need to enable discovery if the device is initiating the connection.
        *        That is, if you are trying to 'join' a 'lobby' you don't need to enable discovery.
        *        However, if you are hosting a lobby you must enable discovery so other devices can find you.
        *        A connection is initiated with the server device's MAC address.
        *        A connection is established if both devices have connected to a BluetoothSocket on the same RFCOMM channel.
         */

        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scanModeChanged = BluetoothAdapter.ACTION_SCAN_MODE_CHANGED; //Not sure what this is for
                String beDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE; //Activity to ask user to make their device discoverable
                IntentFilter filter = new IntentFilter(scanModeChanged);
                registerReceiver(bluetoothState, filter);
                startActivityForResult(new Intent(beDiscoverable), DISCOVERY_REQUEST);
            }
        });



    }//End of onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CANCELED) { //The user was asked to turn on bluetooth the bitch said no
            Toast.makeText(getApplicationContext(), "You gotta enable bluetooth to continue", Toast.LENGTH_LONG).show();
            finish();//Stop the app
        }
        if(requestCode == DISCOVERY_REQUEST) {
            Toast.makeText(getApplicationContext(), "Discovery in progress", Toast.LENGTH_LONG).show();
            findDevices();
        }
    }

    private void startDiscovery() {
        mBluetoothAdapter.cancelDiscovery();//cancel the discovery if it was already discovering.
        mBluetoothAdapter.startDiscovery();
    }

    public void getPairedDevices() {
        devicesArray = mBluetoothAdapter.getBondedDevices();
        if(devicesArray.size() > 0) {
            //There are paired devices
            for(BluetoothDevice device : devicesArray) { //Take the devices out and display information
                temp = temp + device.getName() + " " + device.getAddress();
                pairedDevices.add(device.getName()); //Add the device to the device arrayList
            }
            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
            test.setText(temp);
        }
        test.setText(temp);
    }//End of getPairedDevices()

    public void init() {
        test = (TextView) findViewById(R.id.test);
        disconnect = (Button) findViewById(R.id.disconnect);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//Initializes our bluetooth adapter
        pairedDevices = new ArrayList<String>();

        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);//We need to filter out all the broadcasts we dont need
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Create a BroadcastReceiver to receive device discoery
                //This alows you to find unpaired devices.
                String action = intent.getAction();//action will hold what we filtered.

                if(BluetoothDevice.ACTION_FOUND.equals(action)) { //It found a device
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    for(int a = 0; a < pairedDevices.size(); a++) { //Check to see if the device is already paired
                        if (device.getName().equals(pairedDevices.get(0))) {
                            Toast.makeText(getApplicationContext(), device.getName() + " Paired", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();
                    discoveredDevices.add(deviceName);
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    //Do shit
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                }

                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    //Some bitch shut off bluetooth during out game.
                    if(mBluetoothAdapter.getState() == mBluetoothAdapter.STATE_OFF) {
                        Intent temp = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //So turn that shit back on
                        startActivityForResult(temp, 1);
                        //Be sure to insult him.
                        Toast.makeText(getApplicationContext(), "You a lil bitch...", Toast.LENGTH_LONG).show();
                    }
                }
            }//end of onReceive
        }; //end of receiver
        registerReceiver(receiver, filter);//Filters out the broadcasts and passes it to receiver.
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//Now we wanna filter for ACTION_DISCOVERY_STARTED.
        registerReceiver(receiver, filter);//Filters out the broadcasts and passes it to receiver.
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//Now we wanna filter for ACTION_DISCOVERY_FINISHED.
        registerReceiver(receiver, filter);//Filters out the broadcasts and passes it to receiver.
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);//Now we wanna filter for ACTION_STATE_CHANGED.
        registerReceiver(receiver, filter);
    }//end of init()

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(discoveryResult);//Add this to the onPause method or it will crash when android calls onPause().
    }

    private void findDevices() {
        String lastUsedRemoteDevice = getLastUsedRemoteBTDevice();
        if(lastUsedRemoteDevice != null) {
            toastText = "Checking for known paired devices, namely: " + lastUsedRemoteDevice;
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
            //Check if this device is in a list of currently visible paired devices.
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            for(BluetoothDevice pairedDevice : pairedDevices) {
                if(pairedDevice.getAddress().equals(lastUsedRemoteDevice)) {
                    //It has found a paired device
                    toastText = "Found device: " + pairedDevice.getName() + "@" + lastUsedRemoteDevice;
                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                    remoteDevice = pairedDevice;
                }
            }
        }//end if
        if(remoteDevice == null) { //No paired devices discovered.
            toastText = "Starting discovery for remote devices..";
            Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
            if(mBluetoothAdapter.startDiscovery()) {
                toastText = "Discovery thread started...Scanning for devices";
                Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
                registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));//filter out all the broadcasts
            }
        }
    }//End find devices



    private String getLastUsedRemoteBTDevice() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String result = prefs.getString("LAST_REMOTE_DEVICE_ADDRESS", null);
        return result;
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept(); //Start listening for connection requests.  Returns a bluetoothSocket if successful
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    manageConnectedSocket(socket);
                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public void manageConnectedSocket(BluetoothSocket socket) {
            //The device has connected, do stuff with the connection.
        }

        /** Will cancel the listening socket, and cause the thread to finish */
        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
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
