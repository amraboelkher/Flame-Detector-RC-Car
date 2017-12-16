package com.example.root.FlameDetectorUsingRCCar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {

    private final String DeviceAddress = "00:21:13:00:63:38" ;
    public static String Tag = "Bluetooth";
    private final String DeviceName = "HC-05";
    private static int TOBLUETOOTH = 1;
    private final UUID Port_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static BluetoothDevice device ;
    private static BluetoothSocket socket;
    public static OutputStream _OutputStream;
    public static InputStream _InputStream;
    Button start , stop , send , clear ;
    static TextView _TextView;
    EditText _EditText;
    public static boolean connectedDevice = false;
    byte buffer[];
    int bufferPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        start = (Button)(findViewById(R.id.Start));
        stop = (Button)(findViewById(R.id.Stop));
        send = (Button)(findViewById(R.id.Send));
        clear = (Button)(findViewById(R.id.Clear));

        _TextView = (TextView)(findViewById(R.id.text)) ;
        _EditText = (EditText)(findViewById(R.id.edit)) ;
        init(connectedDevice);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initConnection() && connectBluetooth()){
                    init(connectedDevice);
                    Toast.makeText(getApplicationContext(),"connection established !", Toast.LENGTH_SHORT).show();

                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _OutputStream.close();
                    _InputStream.close();
                    socket.close();
                    connectedDevice = false;
                    Toast.makeText(getApplicationContext(),"connection closed ! ", Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"opps something went wrong", Toast.LENGTH_SHORT).show();

                }
                init(connectedDevice);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _TextView.setText("");
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = _EditText.getText().toString();
                message.concat("\n");
                if( sendMessage(message) == true)
                    _TextView.setText("Your message have been sent !");
            }
        });




    }
    private void init(Boolean active){
        start.setEnabled(!active);
        stop.setEnabled(active);
        clear.setEnabled(true);
        send.setEnabled(active);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();

        boolean x = connectedDevice;
        intent.putExtra("active" , Boolean.toString(x));
        setResult(RESULT_OK , intent);
        finish();
    }
    public static boolean sendMessage(String S){
        try{
            _OutputStream.write(S.getBytes());

            return true;

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean initConnection(){
        boolean Devicefound = false;
        BluetoothAdapter _BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(_BluetoothAdapter == null){
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth ! ", Toast.LENGTH_SHORT).show();
        }
        else {
            if(!_BluetoothAdapter.isEnabled()){
                Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enable , 0);
                try{
                    Thread.sleep(1000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            Set<BluetoothDevice> BluetoothDevices = _BluetoothAdapter.getBondedDevices();
            if(BluetoothDevices.isEmpty()){
                Toast.makeText(getApplicationContext(),"come on the Device isn't even paired ! ",Toast.LENGTH_SHORT).show();
            }
            else {
                for(BluetoothDevice _BluetoothDevice : BluetoothDevices){


                    if(_BluetoothDevice.getAddress().equals(DeviceAddress)){
                        device = _BluetoothDevice;
                        Devicefound = true;
                        Toast.makeText(getApplicationContext(),"Connected to the required device", Toast.LENGTH_SHORT).show();
                    }
                    if(_BluetoothDevice.getAddress().equals(DeviceAddress)){
                        device = _BluetoothDevice;
                        Devicefound = true;
                    }
                }
            }
        }
        return Devicefound;
    }
    public boolean connectBluetooth(){
        boolean connected = true;
        try{
            socket = device.createRfcommSocketToServiceRecord(Port_UUID);
            socket.connect();
        }
        catch(Exception e){
            e.printStackTrace();
            connected = false;
            Toast.makeText(getApplicationContext(),"Error ", Toast.LENGTH_SHORT).show();

        }
        if(connected){
            try{
                connectedDevice = true;
                _OutputStream = socket.getOutputStream();
                _InputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                connected = false;
                Toast.makeText(getApplicationContext(),"Error ", Toast.LENGTH_SHORT).show();

            }
        }
        return connected;
    }

}
