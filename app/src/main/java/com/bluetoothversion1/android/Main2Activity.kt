package com.bluetoothversion1.android

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.recyclerview.buttonAdapter
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class Main2Activity : AppCompatActivity() {

    private val buttonList = ArrayList<button>()
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        MyThread().start()
        initFruits()
        val layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = buttonAdapter(buttonList, btSocket!!)
        recyclerView.adapter = adapter



    }


    inner class MyThread:Thread(){
        override fun run(){
            val MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"
            //val arr = byteArrayOf(0x00.toByte(), 0x20.toByte(), 0x04.toByte(), 0xBE.toByte(), 0x0A.toByte(), 0x0B.toByte())
            val address = "00:20:04:BE:0A:0B"
            val extraData = intent.getStringExtra("extra_data1")
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val device = mBluetoothAdapter!!.getRemoteDevice(extraData)
            try {
                btSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID))
            } catch (e: IOException) {

            }


            mBluetoothAdapter!!.cancelDiscovery()
            try {
                btSocket!!.connect()

            } catch (e: IOException) {
                try {
                    btSocket!!.close()

                } catch (e2: IOException) {
                }

            }

            fun onCancelled() {
                try {
                    btSocket!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun initFruits(){
        buttonList.add(button("??????", R.drawable.backlifting))   //11
        buttonList.add(button("??????", R.drawable.fallingback))   //12
        buttonList.add(button("??????", R.drawable.liftlegs))      //13
        buttonList.add(button("??????", R.drawable.dropleg))   //14

        buttonList.add(button("????????????", R.drawable.turnleft))   //21
        buttonList.add(button("????????????", R.drawable.turnright))   //22
        buttonList.add(button("????????????", R.drawable.opendoor))   //23
        buttonList.add(button("????????????", R.drawable.closedoor))   //24

        buttonList.add(button("??????", R.drawable.changebag))   //31
        buttonList.add(button("??????", R.drawable.packing))   //32
        buttonList.add(button("??????", R.drawable.dry))   //33
        buttonList.add(button("??????", R.drawable.hipwashing))   //34

        buttonList.add(button("??????", R.drawable.womenwashing))   //41
        buttonList.add(button("??????", R.drawable.watertemperature))   //42
        buttonList.add(button("??????", R.drawable.windtemperature))   //43
        buttonList.add(button("??????", R.drawable.cushiontemperature))   //44

        buttonList.add(button("????????????", R.drawable.nozzleforward))   //51
        buttonList.add(button("???", R.drawable.plus))   //22
        buttonList.add(button("????????????", R.drawable.mobilecleaning))   //53
        buttonList.add(button("????????????", R.drawable.flushtoilet))   //54

        buttonList.add(button("????????????", R.drawable.nozzlebackward))   //61
        buttonList.add(button("???", R.drawable.reduce))   //62
        buttonList.add(button("??????", R.drawable.deodorization))   //63
        buttonList.add(button("??????", R.drawable.sos))   //64

        buttonList.add(button("????????????", R.drawable.situp))   //71
        buttonList.add(button("????????????", R.drawable.lieflat))   //72
        buttonList.add(button("??????", R.drawable.pause))   //73
        buttonList.add(button("??????", R.drawable.reset))   //74

    }
}
