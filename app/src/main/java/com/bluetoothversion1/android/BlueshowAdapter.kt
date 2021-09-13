package com.bluetooth.bluetoothtest

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bluetoothversion1.android.R
import kotlinx.android.synthetic.main.activity_main.view.*

class BlueshowAdapter(activity: Activity, val resourceId: Int, data: MutableList<Blueshow>) :
    ArrayAdapter<Blueshow>(activity, resourceId, data){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val bluetoothname: TextView = view.findViewById(R.id.bluetoothname)
        val bluetoothaddress: TextView = view.findViewById(R.id.bluetoothaddress)
        val bluetooth = getItem(position)
        if (bluetooth != null){
            bluetoothname.text = bluetooth.name
            //bluetoothaddress.text = bluetooth.bluetoothId.toString()
        }
        return view
    }
}