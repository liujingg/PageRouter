@file:Suppress("unused")

package com.liujing.pagerouter

import android.app.Activity
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Activity.routerIntArgOr(argName: String, defaultValue: Int = 0): ReadOnlyProperty<Activity, Int> =
        ArgLazy(argName) { _, _: KProperty<*> -> readIntArgOr(argName, defaultValue) }

fun Activity.routerBooleanArgOr(argName: String, defaultValue: Boolean = false): ReadOnlyProperty<Activity, Boolean> =
        ArgLazy(argName) { _, _: KProperty<*> -> readBooleanArgOr(argName, defaultValue) }

fun Activity.routerLongArgOr(argName: String, defaultValue: Long = 0L): ReadOnlyProperty<Activity, Long> =
        ArgLazy(argName) { _, _: KProperty<*> -> readLongArgOr(argName, defaultValue) }

fun Activity.routerDoubleArgOr(argName: String, defaultValue: Double = 0.toDouble()): ReadOnlyProperty<Activity, Double> =
        ArgLazy(argName) { _, _: KProperty<*> -> readDoubleArgOr(argName, defaultValue) }

fun Activity.routerFloatArgOr(argName: String, defaultValue: Float = 0f): ReadOnlyProperty<Activity, Float> =
        ArgLazy(argName) { _, _: KProperty<*> -> readFloatArgOr(argName, defaultValue) }

fun Activity.routerStringArgOr(argName: String, defaultValue: String? = null): ReadOnlyProperty<Activity, String> =
        ArgLazy(argName) { _, _: KProperty<*> -> readStringArgOr(argName, defaultValue) }

fun Fragment.routerIntArgOr(argName: String, defaultValue: Int = 0): ReadOnlyProperty<Fragment, Int> =
        ArgLazy(argName) { _, _: KProperty<*> -> readIntArgOr(argName, defaultValue) }

fun Fragment.routerBooleanArgOr(argName: String, defaultValue: Boolean = false): ReadOnlyProperty<Fragment, Boolean> =
        ArgLazy(argName) { _, _: KProperty<*> -> readBooleanArgOr(argName, defaultValue) }

fun Fragment.routerLongArgOr(argName: String, defaultValue: Long = 0L): ReadOnlyProperty<Fragment, Long> =
        ArgLazy(argName) { _, _: KProperty<*> -> readLongArgOr(argName, defaultValue) }

fun Fragment.routerDoubleArgOr(argName: String, defaultValue: Double = 0.toDouble()): ReadOnlyProperty<Fragment, Double> =
        ArgLazy(argName) { _, _: KProperty<*> -> readDoubleArgOr(argName, defaultValue) }

fun Fragment.routerFloatArgOr(argName: String, defaultValue: Float = 0f): ReadOnlyProperty<Fragment, Float> =
        ArgLazy(argName) { _, _: KProperty<*> -> readFloatArgOr(argName, defaultValue) }

fun Fragment.routerStringArgOr(argName: String, defaultValue: String?= null): ReadOnlyProperty<Fragment, String> =
        ArgLazy(argName) { _, _: KProperty<*> -> readStringArgOr(argName, defaultValue) }


fun Activity.readIntArgOr(argName: String, defaultValue: Int): Int = readIntArg(this, argName, defaultValue)

fun Activity.readBooleanArgOr(argName: String, defaultValue: Boolean): Boolean = readBooleanArg(this, argName, defaultValue)

fun Activity.readLongArgOr(argName: String, defaultValue: Long): Long = readLongArg(this, argName, defaultValue)

fun Activity.readDoubleArgOr(argName: String, defaultValue: Double): Double = readDoubleArg(this, argName, defaultValue)

fun Activity.readFloatArgOr(argName: String, defaultValue: Float): Float = readFloatArg(this, argName, defaultValue)

fun Activity.readStringArgOr(argName: String, defaultValue: String?): String = readStringArg(this, argName, defaultValue)

fun Fragment.readIntArgOr(argName: String, defaultValue: Int): Int = readIntArg(this, argName, defaultValue)

fun Fragment.readBooleanArgOr(argName: String, defaultValue: Boolean): Boolean = readBooleanArg(this, argName, defaultValue)

fun Fragment.readLongArgOr(argName: String, defaultValue: Long): Long = readLongArg(this, argName, defaultValue)

fun Fragment.readDoubleArgOr(argName: String, defaultValue: Double): Double = readDoubleArg(this, argName, defaultValue)

fun Fragment.readFloatArgOr(argName: String, defaultValue: Float): Float = readFloatArg(this, argName, defaultValue)

fun Fragment.readStringArgOr(argName: String, defaultValue: String?): String = readStringArg(this, argName, defaultValue)

fun readIntArg(activity: Activity, argName: String, defaultValue: Int): Int {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getInt(argName, defaultValue)
}

fun readIntArg(fragment: Fragment, argName: String, defaultValue: Int): Int {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getInt(argName, defaultValue)
}

fun readBooleanArg(activity: Activity, argName: String, defaultValue: Boolean): Boolean {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getBoolean(argName, defaultValue)
}

fun readBooleanArg(fragment: Fragment, argName: String, defaultValue: Boolean): Boolean {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getBoolean(argName, defaultValue)
}

fun readLongArg(activity: Activity, argName: String, defaultValue: Long): Long {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getLong(argName, defaultValue)
}

fun readLongArg(fragment: Fragment, argName: String, defaultValue: Long): Long {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getLong(argName, defaultValue)
}

fun readDoubleArg(activity: Activity, argName: String, defaultValue: Double): Double {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getDouble(argName, defaultValue)
}

fun readDoubleArg(fragment: Fragment, argName: String, defaultValue: Double): Double {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getDouble(argName, defaultValue)
}

fun readFloatArg(activity: Activity, argName: String, defaultValue: Float): Float {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getFloat(argName, defaultValue)
}

fun readFloatArg(fragment: Fragment, argName: String, defaultValue: Float): Float {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getFloat(argName, defaultValue)
}

fun readStringArg(activity: Activity, argName: String, defaultValue: String?): String {
    return BundleUri(activity.intent?.extras, activity.intent?.data).getString(argName, defaultValue)
}

fun readStringArg(fragment: Fragment, argName: String, defaultValue: String?): String {
    return BundleUri(fragment.arguments, fragment.activity?.intent?.data).getString(argName, defaultValue)
}

class ArgLazy<in REF, OUT : Any>(private val argName: String, private val initializer: (REF, KProperty<*>) -> OUT?) : ReadOnlyProperty<REF, OUT> {
    private object EMPTY

    private var arg: Any = EMPTY

    override fun getValue(thisRef: REF, property: KProperty<*>): OUT {
        if (arg == EMPTY) {
            arg = requireNotNull(initializer(thisRef, property)) { "Not found arg '$argName' from arguments." }
        }
        @Suppress("UNCHECKED_CAST")
        return arg as OUT
    }
}