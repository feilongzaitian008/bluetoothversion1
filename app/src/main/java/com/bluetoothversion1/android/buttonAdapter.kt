package com.example.recyclerview

import android.bluetooth.BluetoothSocket
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bluetoothversion1.android.R
import com.bluetoothversion1.android.button
import java.io.IOException
import java.io.OutputStream
import kotlin.concurrent.thread


class buttonAdapter(val buttonList: List<button>, var  btSocket: BluetoothSocket) : RecyclerView.Adapter<buttonAdapter.ViewHolder>() {

    private var outStream: OutputStream? = null
    //private var btSocket: BluetoothSocket? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.buttonImage)
        val fruitName: TextView = view.findViewById(R.id.buttonText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_button, parent, false)
        val viewHolder = ViewHolder(view)

        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val buttonChange = buttonList[position]
            when(buttonChange.name){
                "起背" ->  send(byteArrayOf(0x11.toByte()))
                "降背" ->  send(byteArrayOf(0x12.toByte()))
                "抬腿" ->  send(byteArrayOf(0x13.toByte()))
                "落腿" ->  send(byteArrayOf(0x14.toByte()))

                "向左翻身" ->  send(byteArrayOf(0x21.toByte()))
                "向右翻身" ->  send(byteArrayOf(0x22.toByte()))
                "便门开启" ->  send(byteArrayOf(0x23.toByte()))
                "便门关闭" ->  send(byteArrayOf(0x24.toByte()))

                "换袋" ->  send(byteArrayOf(0x31.toByte()))
                "打包" ->  send(byteArrayOf(0x32.toByte()))
                "烘干" ->  send(byteArrayOf(0x33.toByte()))
                "洗便" ->  send(byteArrayOf(0x34.toByte()))

                "妇洗" ->  send(byteArrayOf(0x41.toByte()))
                "水温" ->  send(byteArrayOf(0x42.toByte()))
                "风温" ->  send(byteArrayOf(0x43.toByte()))
                "座温" ->  send(byteArrayOf(0x44.toByte()))

                "喷杆前移" ->  send(byteArrayOf(0x51.toByte()))
                "加" ->  send(byteArrayOf(0x52.toByte()))
                "移动清洗" ->  send(byteArrayOf(0x53.toByte()))
                "冲洗马桶" ->  send(byteArrayOf(0x54.toByte()))

                "喷杆后移" ->  send(byteArrayOf(0x61.toByte()))
                "减" ->  send(byteArrayOf(0x62.toByte()))
                "除臭" ->  send(byteArrayOf(0x63.toByte()))
                "呼救" ->  send(byteArrayOf(0x64.toByte()))

                "一键坐起" ->  send(byteArrayOf(0x71.toByte()))
                "一键躺平" ->  send(byteArrayOf(0x72.toByte()))
                "急停" ->  send(byteArrayOf(0x73.toByte()))
                "复位" ->  send(byteArrayOf(0x74.toByte()))


            }
//
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = buttonList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }

    override fun getItemCount() = buttonList.size

    private fun send(i: ByteArray){
        //val arr = byteArrayOf(0x08.toByte())
        thread {

            try {
                outStream = btSocket!!.outputStream
            } catch (e: IOException) {

            }
            //val msgBuffer = i
            try {
                outStream!!.write(i)
                outStream!!.flush()
            } catch (e: IOException) {

            }

        }
    }

}