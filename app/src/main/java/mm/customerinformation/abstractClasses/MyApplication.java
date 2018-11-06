package mm.customerinformation.abstractClasses;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Az on 2017-07-17.
 */

public class MyApplication extends Application {
    private static MyApplication singleton;

    private static Context mContext;
    public static String myString = "Qasim";
    ProgressDialog progressDialog;
    ////////////varibleName




    private static ProgressDialog pDialog;



    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }



    /////////////////////////////////////////////////////
    public void StoreSharedData(String VaribleName, String VaribleValue) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(VaribleName, VaribleValue);
        editor.apply();

    }
    public String GetAppVersion(){
        String versionName="";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
        }catch (Exception e){
            String error=e.getMessage();
        }
        return  versionName;
    }

    public String getSharedData(String VaribleName) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getString(VaribleName, "");
    }

    public void DisplayToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    /////Helping Method
    public static String getDate() {
        String data_string = "";
        try {
            data_string = java.text.DateFormat.getDateTimeInstance()
                    .format(Calendar.getInstance().getTime()).toString()
                    .replace(' ', '-');
            return data_string;
        } catch (Exception e) {
            Log.e("getDate()", e.getStackTrace().toString());
            return data_string;
        }

    }

    public static String getShortdate() {
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void showProgressbar(Context cntxt, String msg) {
        try {
            progressDialog = new ProgressDialog(cntxt, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage(msg); // Setting Message
            progressDialog.setTitle("Connecting Server"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);

        } catch (Exception e) {
            DisplayToast("showProgressbar" + e.getMessage());
        }
    }


    public void hideProgressbar() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {

        String ee=e.getMessage();
        }

    }
    ////

}
