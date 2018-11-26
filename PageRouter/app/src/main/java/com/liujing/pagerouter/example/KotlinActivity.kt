package com.liujing.pagerouter.example

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.liujing.pagerouter.*
import com.liujing.pagerouter.annotation.RouterActivity

@RouterActivity("kotlin")
class KotlinActivity : AppCompatActivity() {
    private val id: Int by routerIntArgOr("id")
    private val name: String by routerStringArgOr("name")
    private val price: Float by routerFloatArgOr("price")
    private val time: Long by routerLongArgOr("time")
    private val isShow: Boolean by routerBooleanArgOr("isShow", false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        val textview = findViewById<TextView>(R.id.params_text)
        val builder = javaClass.simpleName + '\n'.toString() +
                "id:" + id.toString() + '\n'.toString() +
                "name:" + name + '\n'.toString() +
                "isShow:" + isShow.toString() + '\n'.toString() +
                "price:" + price.toString() + '\n'.toString() +
                "time:" + time.toString() + '\n'.toString()
        textview.text = builder
    }
}