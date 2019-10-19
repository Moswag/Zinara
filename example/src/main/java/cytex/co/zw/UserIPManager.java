package cytex.co.zw;

/**
 * Created by wmoswa on 1/30/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UserIPManager {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "IPPref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_IP = "ip";


    // Constructor
    public UserIPManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createIPSession(String ip){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing ip in pref
        editor.putString(KEY_IP, ip);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */




    /**
     * Get stored session data
     * */
    public HashMap<String, String> getIPDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();



        //phonenumber
        user.put(KEY_IP, pref.getString(KEY_IP, null));





        // return user
        return user;
    }

    /**
     * Clear session details
     * */



    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}