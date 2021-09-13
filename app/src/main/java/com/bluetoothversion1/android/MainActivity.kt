package com.bluetoothversion1.android

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bluetooth.bluetoothtest.Blueshow
import com.bluetooth.bluetoothtest.BlueshowAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStream
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {


    lateinit var mReceiver: BluetoothReceiver
    private var list: MutableList<Blueshow> = mutableListOf()
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var outStream: OutputStream? = null
    private var btSocket: BluetoothSocket? = null
    var bluetoothaddress:String?=null
    var bluetoothSta: Int = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        permissionsRequest()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.blue -> Bluetooth_test()
            R.id.button1 -> connectbluetooth()
        }
        return true
    }



     private fun Bluetooth_test() {
        mBluetoothAdapter!!.startDiscovery()
    }

    private fun connectbluetooth(){
        val intent = Intent(this, Main2Activity::class.java)
        if(bluetoothaddress!=null)
        {
            intent.putExtra("extra_data1", bluetoothaddress)
        }


        startActivity(intent)
    }

    private fun permissionsRequest() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe {
                if (it) {
                    var intentFilter = IntentFilter()
                    intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
                    intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED) //绑定状态变化
                    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) //开始扫描
                    intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) //扫描结束
                    mReceiver = BluetoothReceiver()
                    registerReceiver(mReceiver, intentFilter)
                } else {
                    Toast.makeText(this, " 权限未开启", Toast.LENGTH_SHORT).show()
                }
            }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, " Device does not support Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            if (!mBluetoothAdapter!!.isEnabled) {
                var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, 1)
            }
        }
    }

    inner class BluetoothReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

                    if (device!!.name != null) { //过滤掉设备名称为null的设备
                        if (list.indexOf(Blueshow(device, device.name)) == -1) { //防止重复添加
                            list!!.add(Blueshow(device, device.name))
                            if(device.name=="HULICHUANG"){
                                bluetoothaddress=device.address
                            }
                         }
                }
                var adapter = BlueshowAdapter(this@MainActivity, R.layout.bluetooth_item, list)
                listView1.adapter=adapter
                listView1.setOnItemClickListener{_, _, position, _->
                    //手动配对，完成配对后重新扫描即可
                    val method = BluetoothDevice::class.java.getMethod("createBond")
                    val list1 = list[position]
                    method.invoke(list1.bluetoothId)
                    bluetoothaddress = list[position].bluetoothId.toString()
                    Toast.makeText(context, bluetoothaddress.toString(), Toast.LENGTH_SHORT).show()
                    bluetoothSta=2
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }


}
