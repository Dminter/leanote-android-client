package com.zncm.leanote.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.zncm.leanote.MyApplication;
import com.zncm.leanote.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MX on 3/11 0011.
 */
public class XUtil {
    public static Drawable initIconWhite(Iconify.IconValue iconValue) {
        return new IconDrawable(MyApplication.getInstance().ctx, iconValue).colorRes(R.color.white).sizeDp(24);
    }

    public static Drawable initIconDark(Iconify.IconValue iconValue) {
        return new IconDrawable(MyApplication.getInstance().ctx, iconValue).colorRes(R.color.icon_dark).sizeDp(24);
    }
    public static void dismissShowDialog(DialogInterface dialog, boolean flag) {
        if (flag) {
            dialog.dismiss();
        }
    }

    public static void autoKeyBoardShow(final EditText editText) {
        new Timer().schedule(new TimerTask() {
                                 public void run() {
                                     InputMethodManager inputManager =
                                             (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                     inputManager.showSoftInput(editText, 0);
                                 }
                             },
                500);
    }

    public static void tShort(String string) {
        Toast.makeText(MyApplication.getInstance().ctx, string, Toast.LENGTH_SHORT).show();
    }

    public static void tLong(String string) {
        Toast.makeText(MyApplication.getInstance().ctx, string, Toast.LENGTH_LONG).show();
    }


    public static void debug(Object string) {
        try {
            Log.i("[ln]", String.valueOf(string));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean notEmptyOrNull(String string) {
        if (string != null && !string.equalsIgnoreCase("null") && string.trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static <T> boolean listNotNull(List<T> t) {
        if (t != null && t.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
