package com.naitiks.bluetoothadapter;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter = null;
    private final int MULTI_PER_CODE = 99;
    private TextView status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_on_off);
        status = (TextView) findViewById(R.id.status);
        if(bluetoothAdapter.isEnabled()){
            switchCompat.setEnabled(true);
            status.setText("ON");
        }else{
            status.setText("OFF");
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    onBluetooth();
                }else{
                    offBluetooth();
                }
            }
        });
    }


    private void checkPermission(){
        int bluetoothPer = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH);

        int bluetoothAdminPer = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_ADMIN);

        if(bluetoothPer != PackageManager.PERMISSION_GRANTED || bluetoothAdminPer != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN},
                    MULTI_PER_CODE);

        }
    }

    private void onBluetooth(){
        checkPermission();
        bluetoothAdapter.enable();
        status.setText("ON");
    }

    private void offBluetooth(){
        checkPermission();
        bluetoothAdapter.disable();
        status.setText("OFF");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTI_PER_CODE: {
                if ((grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        grantResults.length > 1
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
