package com.liujing.pagerouter.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liujing.pagerouter.Callback

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button1).setOnClickListener(this)
        findViewById<View>(R.id.button2).setOnClickListener(this)
        findViewById<View>(R.id.button3).setOnClickListener(this)
        findViewById<View>(R.id.button4).setOnClickListener(this)
        findViewById<View>(R.id.button5).setOnClickListener(this)
        findViewById<View>(R.id.button6).setOnClickListener(this)
        findViewById<View>(R.id.button7).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        when (v.id) {
            R.id.button1 -> {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("id", "1")
                intent.putExtra("name", "intent extra")
                intent.putExtra("isShow", true)
                intent.putExtra("price", 13.23f)
                intent.putExtra("time", currentTime)
                startActivity(intent)
            }

            R.id.button2 -> {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("pagerouter://second?id=2&name=from uri&isShow=true&price=18.92&time=$currentTime")))
            }

            R.id.button3 ->
                RouterManager.start(this,Uri.parse("pagerouter://second2?id=3&name=PageRouter&isShow=true&price=123.92&time=$currentTime"))

            R.id.button4 ->
                RouterManager.start(this,Uri.parse("pagerouter://other?id=4&name=other module&isShow=true&price=34.92&time=$currentTime"), object : Callback {
                    override fun onSuccess(context: Context, uri: Uri) {
                        Toast.makeText(context, "other module jump success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailed(context: Context, uri: Uri?, errorCode: Int, message: String?) {
                        Toast.makeText(context, "other module jump failed : $message", Toast.LENGTH_SHORT).show()
                    }
                })

            R.id.button5 ->
                RouterManager.start(this,Uri.parse("pagerouter://kotlin?id=5&name=Kotlin&isShow=true&price=93.92&time=$currentTime"))

            R.id.button6 ->
                RouterManager.start(this,Uri.parse("pagerouter://myfragment?id=15&name=fragment router&isShow=true&price=18.92&time=$currentTime"))

            R.id.button7 ->
                RouterManager.start(this,Uri.parse("pagerouter://browser?url=https://github.com/liujingg/PageRouter"))
        }
    }
}
