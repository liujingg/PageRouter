package com.liujing.pagerouter.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.liujing.pagerouter.RouteCallback
import com.liujing.pagerouter.Router

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
        when (v.id) {
            R.id.button1 -> {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("id", "12")
                intent.putExtra("name", "intent extra")
                intent.putExtra("time", System.currentTimeMillis())
                intent.putExtra("isShow", true)
                intent.putExtra("price", 13.23f)
                startActivity(intent)
            }
            R.id.button2 -> {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("pagerouter://second?id=15&age=24&name=from uri&isShow=true&price=18.92")
                        )
                )
            }
            R.id.button3 -> Router.startActivity(
                    this,
                    Uri.parse("pagerouter://second2?id=17&age=56&name=PageRouter&isShow=true&price=123.92")
            )
            R.id.button4 -> Router.startActivity(
                    this,
                    Uri.parse("pagerouter://other?id=17&age=56&name=OtherModule&isShow=true&price=93.92"), object : RouteCallback {
                override fun onSuccess(context: Context, uri: Uri) {
                    Toast.makeText(context, "other module jump success", Toast.LENGTH_SHORT).show()
                }

                override fun onFailed(context: Context?, message: String?) {
                    Toast.makeText(context, "other module jump failed : $message", Toast.LENGTH_SHORT).show()
                }
            })
            R.id.button5 -> Router.startActivity(
                    this,
                    Uri.parse("pagerouter://kotlin?id=17&age=56&name=Kotlin&isShow=true&price=93.92")
            )
            R.id.button6 -> Router.startActivity(
                    this,
                    Uri.parse("pagerouter://myfragment?id=15&age=64&name=fragment router&isShow=true&price=18.92"))

            R.id.button7 -> Router.startActivity(
                    this,
                    Uri.parse("pagerouter://browser?url=https://github.com/liujingg/PageRouter"))
        }
    }
}
