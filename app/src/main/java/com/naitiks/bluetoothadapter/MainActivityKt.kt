package com.naitiks.bluetoothadapter

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.widget.TextView
import android.widget.Toast

class MainActivityKt : AppCompatActivity() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val MULTI_PER_CODE = 99
    private var status: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val switchCompat = findViewById(R.id.switch_on_off) as SwitchCompat
        status = findViewById(R.id.status) as TextView
        if (bluetoothAdapter!!.isEnabled) {
            switchCompat.isEnabled = true
            status!!.text = "ON"
        } else {
            status!!.text = "OFF"
        }
        switchCompat.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                onBluetooth()
            } else {
                offBluetooth()
            }
        }
    }


    private fun checkPermission() {
        val bluetoothPer = ContextCompat.checkSelfPermission(this@MainActivityKt,
                Manifest.permission.BLUETOOTH)

        val bluetoothAdminPer = ContextCompat.checkSelfPermission(this@MainActivityKt,
                Manifest.permission.BLUETOOTH_ADMIN)

        if (bluetoothPer != PackageManager.PERMISSION_GRANTED || bluetoothAdminPer != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivityKt,
                    arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN),
                    MULTI_PER_CODE)

        }
    }

    private fun onBluetooth() {
        checkPermission()
        bluetoothAdapter!!.enable()
        status!!.text = "ON"
    }

    private fun offBluetooth() {
        checkPermission()
        bluetoothAdapter!!.disable()
        status!!.text = "OFF"
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MULTI_PER_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults.size > 1
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivityKt, "Permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivityKt, "Permission not granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
