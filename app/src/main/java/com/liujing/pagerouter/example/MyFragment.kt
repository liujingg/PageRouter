package com.liujing.pagerouter.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.liujing.pagerouter.*
import com.liujing.pagerouter.annotation.RouterFragment

@RouterFragment(value = ["myfragment"], activityClazz = FragmentContainerActivity::class)
class MyFragment: Fragment() {

    private val mId: Int by routerIntArgOr("id")
    private val name: String by routerStringArgOr("name")
    private val price: Float by routerFloatArgOr("price")
    private val time: Long by routerLongArgOr("time")
    private val isShow: Boolean by routerBooleanArgOr("isShow", false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_common, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textview = view.findViewById<TextView>(R.id.params_text)
        val builder = javaClass.simpleName + '\n'.toString() +
                "id:" + mId.toString() + '\n'.toString() +
                "name:" + name + '\n'.toString() +
                "isShow:" + isShow.toString() + '\n'.toString() +
                "price:" + price.toString() + '\n'.toString() +
                "time:" + time.toString() + '\n'.toString()
        textview.text = builder
    }
}