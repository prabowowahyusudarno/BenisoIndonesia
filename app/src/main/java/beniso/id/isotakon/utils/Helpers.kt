package beniso.id.isotakon.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.AppCompatButton
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import beniso.id.isotakon.R
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import io.realm.Realm
import io.realm.RealmConfiguration
//import kotlinx.android.synthetic.main.layout_login_expired.view.*
import okhttp3.OkHttpClient
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher

/**
 * Created by Prabowo Wahyu Sudarno
 */
object Helpers {
    fun showToast(context: Context, message: String, isShowLong: Boolean) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, if (isShowLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
        }
    }

    //only can be called in activity
    fun setWindowScrollable(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun getAppVersion(context: Context): String {
        var versionName: String = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            versionName = "0.0.0.0"
            e.printStackTrace()
        }
        return versionName
    }

//    fun defaultHttpClientNormal(context: Context): OkHttpClient {
//        return setTimeOut(context.resources.getInteger(R.integer.timeout_normal))
//    }

    fun setTimeOut(timeOutSeconds: Int): OkHttpClient {
        return OkHttpClient().newBuilder()
                .readTimeout(timeOutSeconds.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeOutSeconds.toLong(), TimeUnit.SECONDS)
                .readTimeout(timeOutSeconds.toLong(), TimeUnit.SECONDS)
                .build()
    }



    fun showMessageDialog(context: Context, title: String, message: String, positiveBtnText: String, onPositive: MaterialDialog.SingleButtonCallback, isCancelAble: Boolean) {
        MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveBtnText)
                .onPositive(onPositive)
                .cancelable(isCancelAble)
                .build().show()
    }

    fun showProgressDialog(context: Context, title: String, msg: String): MaterialDialog {
        return MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .progress(true, 0)
                .cancelable(false)
                .build()
    }

//    fun showExpiredDialog(context: Context, title: String, username: String,
//                          positiveBtnText: String, onPositive: MaterialDialog.SingleButtonCallback,
//                          negativeBtnText: String, onNegative: MaterialDialog.SingleButtonCallback,
//                          isCancelAble: Boolean) {
//
//        var dialog: MaterialDialog = MaterialDialog.Builder(context)
//                .customView(R.layout.layout_login_expired, true)
//                .positiveText(positiveBtnText)
//                .onPositive(onPositive)
//                .negativeText(negativeBtnText)
//                .onNegative(onNegative)
//                .cancelable(isCancelAble)
//                .show()
//
//        dialog.view.tedtUsername.setText(username)
//    }

    fun showDialogInfo(context: Context,
                       @StringRes
                       titleRes: Int,
                       positiveBtnText: String,
                       positiveListener: DialogInterface.OnDismissListener,
                       contentRes: String) {
        MaterialDialog.Builder(context)
                .limitIconToDefaultSize()
                .title(titleRes)
                .content(contentRes)
                .positiveText(positiveBtnText)
                .dismissListener(positiveListener)
                .autoDismiss(true)
                .canceledOnTouchOutside(false)
                .show()
    }

    fun customViewDialog(context: Context, layout: Int, title: String, cancelAble: Boolean): MaterialDialog {

        return MaterialDialog.Builder(context)
                .customView(layout, false)
                .cancelable(cancelAble)
                .build()

    }

    fun customViewDialogScroll(context: Context, layout: Int, title: String, cancelAble: Boolean, positiveBtnText: String, onPositive: MaterialDialog.SingleButtonCallback, negativeBtnText: String, onNegative: MaterialDialog.SingleButtonCallback): MaterialDialog {

        return MaterialDialog.Builder(context)
                .customView(layout, false)
                .cancelable(cancelAble)
                .positiveText(positiveBtnText)
                .onPositive(onPositive)
                .negativeText(negativeBtnText)
                .onNegative(onNegative)
                .build()

    }

    fun customViewDialogCustom(context: Context, layout: Int, title: String, cancelAble: Boolean, positiveBtnText: String, onPositive: MaterialDialog.SingleButtonCallback, negativeBtnText: String, onNegative: MaterialDialog.SingleButtonCallback): MaterialDialog {

        return MaterialDialog.Builder(context)
                .customView(layout, false)
                .title(title)
                .cancelable(cancelAble)
                .positiveText(positiveBtnText)
                .onPositive(onPositive)
                .negativeText(negativeBtnText)
                .onNegative(onNegative)
                .build()

    }

    fun showMessageDialog(context: Context, title: String, message: String, positiveBtnText: String, onPositive: MaterialDialog.SingleButtonCallback,
                          negativeBtnText: String, onNegative: MaterialDialog.SingleButtonCallback, isCancelAble: Boolean) {
        MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveBtnText)
                .onPositive(onPositive)
                .negativeText(negativeBtnText)
                .onNegative(onNegative)
                .cancelable(isCancelAble)
                .build()
                .show()
    }

    fun showListDialog(context: Context, title: String, contents: MutableList<String>,
                       onItemSelected: MaterialDialog.ListCallback,
                       cancelAble: Boolean) {
        MaterialDialog.Builder(context)
                .title(title)
                .items(contents)
                .itemsCallback(onItemSelected)
                .cancelable(cancelAble).build()
                .show()

    }


    fun showListRadioDialog(context: Context, title: String, contents: MutableList<String?>,
                            positiveBtnText: String,
                            negativeBtnText: String, onNegative: MaterialDialog.SingleButtonCallback,
                            onItemSelected: MaterialDialog.ListCallbackSingleChoice,
                            cancelAble: Boolean) {
        MaterialDialog.Builder(context)
                .title(title)
                .items(contents)
                .itemsCallbackSingleChoice(0, onItemSelected)
                .positiveText(positiveBtnText)
                .negativeText(negativeBtnText)
                .onNegative(onNegative)
                .cancelable(cancelAble)
                .show()
    }


    fun showListDialogWac(context: Context, contents: MutableList<String>,
                          onItemSelected: MaterialDialog.ListCallback,
                          cancelAble: Boolean) {
        MaterialDialog.Builder(context)
                .items(contents)
                .itemsCallback(onItemSelected)
                .cancelable(cancelAble).build()
                .show()

    }

    fun generateGUID(): String {
        return UUID.randomUUID().toString()
    }

    fun setRealmConfiguration(context: Context) {
        Realm.init(context)
        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder()
                        .name(context.getString(R.string.realm_file_name))
//                        .encryptionKey(Const.getRealmKey())
                        .deleteRealmIfMigrationNeeded()
                        .build())
    }

    fun getIcon(context: Context, iconId: IIcon, colorResource: Int,
                size: Int): Drawable {
        return IconicsDrawable(context)
                .icon(iconId)
                .color(colorResource)
                .sizeDp(size)
    }


    fun setCompoundIconDropDown(editText: TextInputEditText, context: Context, icon: IIcon) {
        var iconics: Drawable
        iconics = if (icon == null) {
            ActivityCompat.getDrawable(context, R.drawable.ic_arrow_done)!!
        } else {
            Helpers.getIcon(context, icon, ActivityCompat.getColor(context, R.color.colorPrimary), 12)
        }

        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconics, null)
    }

    fun setCompoundIconButton(mButton: AppCompatButton, context: Context, icon: IIcon, position: String) {

        var iconics: Drawable = if (icon == null) {
            ActivityCompat.getDrawable(context, R.drawable.abc_ic_arrow_drop_right_black_24dp)!!
        } else {
            Helpers.getIcon(context, icon, ActivityCompat.getColor(context, R.color.colorPrimary), 12)
        }


        when (position) {
            "left" -> {
                mButton.setCompoundDrawablesWithIntrinsicBounds(iconics, null, null, null)
            }
            "top" -> {
                mButton.setCompoundDrawablesWithIntrinsicBounds(null, iconics, null, null)
            }
            "right" -> {
                mButton.setCompoundDrawablesWithIntrinsicBounds(null, null, iconics, null)
            }
            "bottom" -> {
                mButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, iconics)
            }
        }
    }

//    private fun getInformation(realm: Realm): Information {
//        var result = Information()
//        result = InformationHelper().getInformation(realm)
//        return result
//    }

    fun getVersionInfo(context: Context, type: String): String {
        var result = ""
        var packageInfo: PackageInfo = getPackageInfo(context)
        if (packageInfo != null) {
            result = packageInfo.versionName.toString()
            if (type == "code") {
                result = packageInfo.versionCode.toString()
            }
        }
        return result
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View = activity.currentFocus
        if (view == null)
            view = View(activity)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setHideKeyboard(activity: Activity) {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        if (activity.currentFocus != null) {
            val inputMethodManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus.windowToken, 0)
        }
    }

    fun initDrawer() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
                if (imageView == null) return
                Glide.with(imageView.context).load(uri).placeholder(placeholder).into(imageView)
            }

            override fun cancel(imageView: ImageView?) {
                if (imageView == null) return
                Glide.clear(imageView)
            }
        })
    }

    fun setMainActionBar(actionBar: android.support.v7.app.ActionBar, title: String, buttonback: Boolean) {
        actionBar.setDisplayHomeAsUpEnabled(buttonback)
        if (!TextUtils.isEmpty(title))
            actionBar.title = title
    }

    fun getPackageInfo(context: Context): PackageInfo {
        var result = PackageInfo()
        try {
            result = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun setInputLayoutError(inputLayout: TextInputLayout, message: String) {
        inputLayout.error = message
    }

    fun clearInputLayoutError(inputLayout: TextInputLayout) {
        inputLayout.error = null
    }

//    fun getInformation(realm: Realm, type: String): String {
//        var result = ""
//        var information: Information = getInformation(realm)
//        if (information != null) {
//            when (type) {
//                "ticketno" -> {
//                    result = information.ticketNo
//                }
//                "bacode" -> {
//                    result = information.baCode
//                }
//                "uname" -> {
//                    result = information.uname
//                }
//                "password" -> {
//                    result = information.password
//                }
//                "date" -> {
//                    result = information.dateInfo
//                }
//                "userId" -> {
//                    result = information.userId
//                }
//                "companycode" -> {
//                    result = information.companyCode
//                }
//                "IsSSCRecall" -> {
//                    result = information.isSSCRecall
//                }
//                "IsCustomerTaxRegulation" -> {
//                    result = information.isCustomerTaxRegulation.toString()
//                }
//            }
//        }
//        return result
//    }

    @SuppressLint("MissingPermission")
    fun getIMEI(context: Context): String {
        var result = ""
        var telecomManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        result = telecomManager.deviceId
        //result = "359896060667597"
        return result
    }

    fun getPublicKey(modulusStr: String, exponentStr: String): PublicKey {
        var pubKey: PublicKey? = null
        try {
            val modulusBytes = Base64.decode(modulusStr, Base64.DEFAULT)
            val exponentBytes = Base64.decode(exponentStr, Base64.DEFAULT)
            val modulus = BigInteger(1, modulusBytes)
            val exponent = BigInteger(1, exponentBytes)

            val rsaPubKey = RSAPublicKeySpec(modulus, exponent)
            val fact = KeyFactory.getInstance("RSA")
            pubKey = fact.generatePublic(rsaPubKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return pubKey!!
    }

    fun doEncrypt(value: String, modulus: String, exponent: String): String {
        var result: String = ""

        try {
//            val publicKeyVal: String = Const.getPublicKey()
            val cipher: Cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            val publicKey = getPublicKey(modulus, exponent)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val cipherText = cipher.doFinal(value.toByteArray(charset("UTF-8")))
            result = Base64.encodeToString(cipherText, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun doEncodeOffline(value: String): String {
        var result: String = ""

        try {
            result = Base64.encodeToString(value.toByteArray(charset("UTF-8")), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getMacAddress(context: Context): String {
        var result = ""
        val wm: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wm.connectionInfo

        result = info.macAddress
        return result
    }

    fun getIpAddress(context: Context): String {
        val wm: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wi: WifiInfo = wm.connectionInfo
        var ip = wi.ipAddress.toString()
        return ip
    }

    fun isInternetConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val result: Boolean
        result = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return result
    }


    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun getPref(context: Context, prefName: String, key: String): String {
        var pref = context.getSharedPreferences(prefName, 0)
        return pref.getString(key, "")
    }

    fun getPrefBool(context: Context, prefName: String, key: String): Boolean {
        var pref = context.getSharedPreferences(prefName, 0)
        return pref.getBoolean(key, false)
    }

    fun getPrefInt(context: Context, prefName: String, key: String): Int {
        var pref = context.getSharedPreferences(prefName, 0)
        return pref.getInt(key, 0)
    }

    fun savePref(context: Context, prefName: String, key: String, value: String?) {
        val pref = context.getSharedPreferences(prefName, 0)
        var editor = pref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun savePref(context: Context, prefName: String, key: String, value: Boolean?) {
        val pref = context.getSharedPreferences(prefName, 0)
        var editor = pref.edit()
        editor.putBoolean(key, false)
        editor.commit()
    }

    fun savePref(context: Context, prefName: String, key: String, value: Int?) {
        val pref = context.getSharedPreferences(prefName, 0)
        var editor = pref.edit()
        if (value != null) {
            editor.putInt(key, value)
        }
        editor.commit()
    }


    fun getShownTimeHourFromString(stringDate: String?): String {
        var result = "00.00"
        if (stringDate == null)
            return result
        if (stringDate != "") {
            if (stringDate == "00:00:00") {
                return result
            }
            try {
                var sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.US)
                val tempDate = sf.parse(stringDate)
                if (tempDate != null) {
                    sf = SimpleDateFormat("HH:mm", Locale.US)
                }
                result = sf.format(tempDate)
            } catch (e: java.text.ParseException) {
                e.printStackTrace()
            }

        }
        return result
    }


    fun string2Date(date: String, pattern: String): Date? {
        try {
            return SimpleDateFormat(pattern, Locale.US).parse(date)
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }

        return null
    }

    fun date2String(date: Date, pattern: String): String {
        try {
            return SimpleDateFormat(pattern, Locale.US).format(date)
        } catch (ignore: Exception) {
            ignore.printStackTrace()
            return ""
        }
    }


    fun getDateTimeNowMinute(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val second = 0

        val today = (year.toString() + "-" + pad(month) + "-" + pad(day) + " "
                + pad(hour) + ":" + pad(minute) + ":" + pad(second))
        return today
    }

    fun pad(c: Int): String {
        return if (c >= 10)
            c.toString()
        else
            "0" + c.toString()
    }

    fun compareTime(targetDate: Date?, checkDate: Date?): Boolean {
        var isValid = false
        if (targetDate != null && checkDate != null) {
            if (checkDate.after(targetDate)) {
                isValid = true
            }
        }
        return isValid
    }


    // remove "0" in .0 or .300 decimal format
    fun formateDecimal(value: Double?): String {
        val formatter = DecimalFormat("0.#")
        return formatter.format(value)
    }

    fun convertDateFormat(stringDate: String, recentFormat: String, wantedFormat: String): String {
        val date = Helpers.string2Date(stringDate, recentFormat)
        val dateFor = SimpleDateFormat(wantedFormat)
        return dateFor.format(date)

    }

    fun removeInputLayoutError(inputLayout: TextInputLayout) {
        inputLayout.error = ""
    }

    fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }

    fun compareTime8to5(): Boolean {
        var isValid = false
        val pattern = "HH:mm"
        // valid jika time after 8.00 n before 17.00
        val time8clock = Helpers.string2Date("08:00", pattern)
        val time17clock = Helpers.string2Date("17:00", pattern)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val timeNow = Helpers.string2Date(hour.toString() + ":" + minute, pattern)
        if (timeNow != null && time8clock != null && time17clock != null) {
            if (timeNow!!.after(time8clock)) {
                if (timeNow!!.before(time17clock)) {
                    isValid = true
                }
            }
        }
        return isValid
    }

    fun getShownTimeFromString(stringDate: String): String {

        var result = ""
        if (stringDate == null)
            return result
        if (stringDate != "") {
            if (stringDate == "00:00:00") {
                return result
            }
            try {
                var sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.US)
                val tempDate = sf.parse(stringDate)
                if (tempDate != null) {
                    sf = SimpleDateFormat("HH:mm:ss", Locale.US)
                }
                result = sf.format(tempDate)
            } catch (e: java.text.ParseException) {
                e.printStackTrace()
            }

        }
        return result

    }

    fun getShownDateFromString(stringDate: String): String {

        var result = ""
        if (stringDate == null)
            return result
        if (stringDate != "") {
            if (stringDate == "00:00:00") {
                return result
            }
            try {
                var sf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.US)
                val tempDate = sf.parse(stringDate)
                if (tempDate != null) {
                    sf = SimpleDateFormat("dd MMM yyyy", Locale.US)
                }
                result = sf.format(tempDate)
            } catch (e: java.text.ParseException) {
                e.printStackTrace()
            }

        }
        return result

    }

    fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false)
        return resizedBitmap
    }

    fun cropString(data: String, awal: Int, akhir: Int): String {
        var print = ""
        val Length = (Math.floor((akhir - awal).toDouble()) / 10).toInt()
        if (data.length > Length) {
            print = print + data.substring(0, Length)
        } else {
            print = print + data
        }

        return print
    }

    fun setDateNow(indicator: String): String {

        var result = ""
        var df = SimpleDateFormat("dd MMM yyyy")
        var tf = SimpleDateFormat("HH:mm:ss")
        var ff = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = df.format((Calendar.getInstance().time))
        var time = tf.format((Calendar.getInstance().time))
        var full = ff.format((Calendar.getInstance().time))
        if (indicator.toLowerCase().equals("time")) {
            result =  time
        }

        else if (indicator.toLowerCase().equals("date")) {
            result =  date
        }

        else if (indicator.toLowerCase().equals("full")){
            result = full
        }

        return result

    }


}