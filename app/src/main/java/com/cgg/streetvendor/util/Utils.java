package com.cgg.streetvendor.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cgg.streetvendor.BuildConfig;
import com.cgg.streetvendor.R;
import com.cgg.streetvendor.ui.view.DashboardActivity;
import com.cgg.streetvendor.ui.view.DownloadActivity;
import com.cgg.streetvendor.ui.view.LoginActivity;
import com.cgg.streetvendor.ui.view.MainActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.Manifest.permission.READ_PHONE_STATE;

public class Utils {

    private static final int MEDIA_TYPE_IMAGE = 1;
    private static Uri fileUri;
    private static Uri imageUri;
    private static String dirPath;
    private static File mediaFile;

    public static void loadSpinnerData(Context context, ArrayList<String> arrayList, Spinner spinner) {
        try {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_text, arrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner_text);
            spinner.setAdapter(spinnerArrayAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void customLogoutAlert(Activity activity, String title, String msg,
                                         SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }


                        Intent newIntent = new Intent(activity, LoginActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customAlertExit(Activity activity, String title, String msg,
                                       SharedPreferences.Editor editor) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        editor.putString(AppConstants.MEMBER_DATA, "");
                        editor.commit();

                        Intent newIntent = new Intent(activity, DashboardActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(newIntent);
                        activity.finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void ShowPlayAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnYes = dialog.findViewById(R.id.btDialogYes);
                Button btnNo = dialog.findViewById(R.id.btDialogNo);
                btnNo.setVisibility(View.GONE);
                btnYes.setText("Update");
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        try {
                            Intent localIntent = new Intent("android.intent.action.VIEW",
                                    Uri.parse("market://details?id=" + activity.getPackageName()));
                            activity.startActivity(localIntent);
                            activity.finish();
                        } catch (Exception e) {

                            Toast.makeText(activity, activity.getString(R.string.google_play), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customSyncLanguageAlert(Activity activity, String fromSCreen) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(activity.getResources().getString(R.string.languagealertTitle));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                if (LocaleHelper.getLanguage(activity).equalsIgnoreCase("te")) {
                    dialogMessage.setText(activity.getResources().getString(R.string.languagealertinfo));
                } else {
                    dialogMessage.setText(activity.getResources().getString(R.string.languagealertinfo_tel));
                }


                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                no.setText(activity.getString(R.string.no));
                btDialogYes.setText(activity.getString(R.string.yes));
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (LocaleHelper.getLanguage(activity).equalsIgnoreCase("te")) {
                            LocaleHelper.setLocale(activity, "en");
                        } else {
                            LocaleHelper.setLocale(activity, "te");
                        }
                        activity.finish();
                        if (fromSCreen.equalsIgnoreCase("dashboard")) {
                            activity.startActivity(activity.getIntent());
                        } else {
                            activity.startActivity(new Intent(activity, DashboardActivity.class));
                        }

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        int ageInt = age;

        return Integer.toString(ageInt);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getPreviousDate() {
        String result = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            result = s.format(new Date(cal.getTimeInMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    public static String getDeviceID(Context context) {
        String deviceID = null;
        try {
            ContextCompat.checkSelfPermission(context, READ_PHONE_STATE);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                deviceID = Settings.Secure.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                deviceID = null;
                deviceID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if (deviceID == null)
                    deviceID = Settings.Secure.getString(context.getContentResolver(), "android_id");
                if (deviceID == null)
                    deviceID = "NODeviceID";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceID;
    }

    public static String getVersionName(Context context) {
        String version;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "";
            e.printStackTrace();
        }
        return version;
    }

    private static void displayPhotoDialogBox(byte[] photo, Context context, String name) {
        final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.photo_dialog);
        final ImageView imageView = dialog.findViewById(R.id.image);
        ImageView close = dialog.findViewById(R.id.close);
        final ProgressBar pb = dialog.findViewById(R.id.progressbar);
        final TextView tv = dialog.findViewById(R.id.header_tv);
        tv.setText(name);
        pb.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(photo)
                .asBitmap()
                .error(R.drawable.male_user)
                .placeholder(R.drawable.loader_black1)
                .listener(new RequestListener<byte[], Bitmap>() {
                    @Override
                    public boolean onException(Exception e, byte[] model, Target<Bitmap> target, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, byte[] model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        PhotoViewAttacher pAttacher;
                        pAttacher = new PhotoViewAttacher(imageView);
                        pAttacher.update();
                        return false;
                    }
                })
                .into(imageView);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public static void hideKeyboard(Context context, View mView) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr != null && conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static void openSettings(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    public static void takeSCImage(Context activity, View view, String typeData) {
        try {
            dirPath = new File(activity.getExternalFilesDir(null), "SVS").getPath();
            fileUri = getOutputMediaFileUri(activity, typeData);
            Bitmap bitmap = getScreenShot(view);
            if (bitmap != null) {
                store(bitmap, activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Bitmap getScreenShot(View ll) {
        ll.setDrawingCacheEnabled(true);
        ll.buildDrawingCache(true);
        return Bitmap.createBitmap(ll.getDrawingCache());
    }

    private static void store(Bitmap bm, Context activity) {
        try {
            FileOutputStream fOut = new FileOutputStream(mediaFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            shareImage(activity, fileUri);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void shareImage(Context activity, Uri fileUri) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");

            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            try {
                activity.startActivity(Intent.createChooser(intent, "Project Data"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "No App Available", Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Uri getOutputMediaFileUri(Context activity, String typeData) {
        File imageFile = getOutputMediaFile(typeData);
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    activity,
                    activity.getPackageName() + ".provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

    private static File getOutputMediaFile(String typeData) {
        File mediaStorageDir = new File(dirPath);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + typeData + getTimeStamp() + ".jpg");

        return mediaFile;
    }

    private static String getTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        return simpleDateFormat.format(new Date());
    }

    //    public static void customSectionSaveAlert(final Activity activity, String msg, String title) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_success);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        activity.startActivity(new Intent(activity, InstMenuMainActivity.class)
//                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        activity.finish();
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customAcademicSectionSaveAlert(final Activity activity, String msg, String title) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_success);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        activity.finish();
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getRandomCurrentDateTime() {
//        return new SimpleDateFormat("ddMMYYhhmm", Locale.getDefault()).format(new Date());
//    }
//
//    public static String getCurrentDateTime() {
//        return new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss", Locale.getDefault()).format(new Date());
//    }
//
//    public static String getCurrentDateTimeFormat() {
//        return new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.getDefault()).format(new Date());
//    }
//
//    public static String getCurrentDateTimeDisplay() {
//        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault()).format(new Date());
//    }
//
//    public static String getCurrentDate() {
//        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//    }
//
//
    public static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //    public static void customExitAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
////                        activity.startActivity(new Intent(activity, LoginActivity.class));
//                        Intent newIntent = new Intent(activity, QuitAppActivity.class);
//                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(newIntent);
//                        activity.finish();
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void customCloseAppAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        Intent newIntent = new Intent(activity, QuitAppActivity.class);
//                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(newIntent);
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void customChangeAppAlert(Activity activity, String title, String msg, InstMainViewModel instMainViewModel, SharedPreferences.Editor editor) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        editor.putString(AppConstants.INST_ID, "");
//                        editor.putString(AppConstants.INST_NAME, "");
//                        editor.putInt(AppConstants.DIST_ID, -1);
//                        editor.putInt(AppConstants.MAN_ID, -1);
//                        editor.putInt(AppConstants.VILL_ID, -1);
//                        editor.putString(AppConstants.DIST_NAME, "");
//                        editor.putString(AppConstants.MAN_NAME, "");
//                        editor.putString(AppConstants.VIL_NAME, "");
//                        editor.putString(AppConstants.LAT, "");
//                        editor.putString(AppConstants.LNG, "");
//                        editor.putString(AppConstants.ADDRESS, "");
//
//                        editor.commit();
//                        instMainViewModel.deleteAllInspectionData();
//
//                        Intent newIntent = new Intent(activity, DashboardActivity.class);
//                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(newIntent);
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customLogoutAlert(Activity activity, String title, String msg, InstMainViewModel instMainViewModel, SharedPreferences.Editor editor) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        editor.putString(AppConstants.INST_ID, "");
//                        editor.putString(AppConstants.INST_NAME, "");
//                        editor.putInt(AppConstants.DIST_ID, -1);
//                        editor.putInt(AppConstants.MAN_ID, -1);
//                        editor.putInt(AppConstants.VILL_ID, -1);
//                        editor.putString(AppConstants.DIST_NAME, "");
//                        editor.putString(AppConstants.MAN_NAME, "");
//                        editor.putString(AppConstants.VIL_NAME, "");
//                        editor.putString(AppConstants.LAT, "");
//                        editor.putString(AppConstants.LNG, "");
//                        editor.putString(AppConstants.ADDRESS, "");
//
//                        editor.commit();
//                        instMainViewModel.deleteAllInspectionData();
//
//                        Intent newIntent = new Intent(activity, LoginActivity.class);
//                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        activity.startActivity(newIntent);
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void customHomeAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        activity.startActivity(new Intent(activity, InstMenuMainActivity.class)
//                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customDiscardAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_exit);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button exit = dialog.findViewById(R.id.btDialogExit);
//                exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        activity.finish();
//                    }
//                });
//
//                Button cancel = dialog.findViewById(R.id.btDialogCancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customDistanceAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_error);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btnOK = dialog.findViewById(R.id.btDialogYes);
//                btnOK.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getRandomNumberString() {
//        // It will generate 6 digit random Number.
//        // from 0 to 999999
//        Random rnd = new Random();
//        int number = rnd.nextInt(999999);
//        // this will convert any number sequence into 6 character.
//        String randomNum = String.format("%06d", number);
//        String time = getRandomCurrentDateTime();
//        return randomNum.concat(time);
//    }
//
    public static void customErrorAlert(Context activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_error);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnOK = dialog.findViewById(R.id.btDialogYes);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customErrorAlertExitPhoto(Context activity, String title, String msg, byte[] photo, String name) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_error_photo);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                ImageView imageView = dialog.findViewById(R.id.iv_photo);
                dialogMessage.setText(msg);
                Button btnOK = dialog.findViewById(R.id.btDialogYes);
                Glide.with(activity)
                        .load(photo)
                        .asBitmap()
                        .error(R.drawable.male_user)
                        .placeholder(R.drawable.loader_black1)
                        .into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.displayPhotoDialogBox(photo, activity, name);
                    }
                });


                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customErrorAlertExit(Context activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_error);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnOK = dialog.findViewById(R.id.btDialogYes);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


//
//    public static void ShowPlayAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_information);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btnYes = dialog.findViewById(R.id.btDialogYes);
//                Button btnNo = dialog.findViewById(R.id.btDialogNo);
//                btnNo.setVisibility(View.GONE);
//                btnYes.setText("Update");
//                btnYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        try {
//                            Intent localIntent = new Intent("android.intent.action.VIEW",
//                                    Uri.parse("market://details?id=" + activity.getPackageName()));
//                            activity.startActivity(localIntent);
//                            activity.finish();
//                        } catch (Exception e) {
//                            Toast.makeText(activity, activity.getString(R.string.google_play), Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//

    public static void customSaveAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_success);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        activity.startActivity(new Intent(activity, DashboardActivity.class));
                        activity.finish();
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void customSuccessAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_success);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });


                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    //
//    public static void customSyncSuccessAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_success);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void ShowDeviceSessionAlert(Activity activity, String title, String msg, InstMainViewModel instMainViewModel) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_error);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button yes = dialog.findViewById(R.id.btDialogYes);
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (dialog.isShowing())
//                            dialog.dismiss();
//                        instMainViewModel.deleteAllInspectionData();
//
//                        SharedPreferences sharedPreferences = TWDApplication.get(activity).getPreferences();
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.commit();
//
//                        activity.startActivity(new Intent(activity, LoginActivity.class));
//                        activity.finish();
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customTimeAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_information);
//
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button yes = dialog.findViewById(R.id.btDialogYes);
//                Button no = dialog.findViewById(R.id.btDialogNo);
//                no.setVisibility(View.GONE);
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        activity.startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//

    public static void customSyncAlertCancel(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_sync);

                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(activity, DownloadActivity.class));
                        activity.finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customSyncAlertDownload(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_download);

                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btnDownload = dialog.findViewById(R.id.btDialogDownload);
                Button btnYes = dialog.findViewById(R.id.btDialogYes);
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(activity, DownloadActivity.class));
                        activity.finish();
                    }
                });
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void customSyncAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_sync);

                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.finish();
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        activity.startActivity(new Intent(activity, DownloadActivity.class));
                        activity.finish();
                    }
                });
                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
//
//    public static void customSchoolSyncAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_sync);
//
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button yes = dialog.findViewById(R.id.btDialogYes);
//                Button no = dialog.findViewById(R.id.btDialogNo);
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        activity.finish();
//                        dialog.dismiss();
//                    }
//                });
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        activity.startActivity(new Intent(activity, SchoolSyncActivity.class));
//                        activity.finish();
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customSchemeSyncAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_sync);
//
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button yes = dialog.findViewById(R.id.btDialogYes);
//                Button no = dialog.findViewById(R.id.btDialogNo);
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        activity.finish();
//                        dialog.dismiss();
//                    }
//                });
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        activity.startActivity(new Intent(activity, SchemeSyncActivity.class));
//                        activity.finish();
//                    }
//                });
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void customWarningAlert(Context context, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_warning);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customWarningAlert(Activity context, String title, String msg, final boolean goBack) {
//        try {
//            final Dialog dialog = new Dialog(context);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_warning);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                            if (goBack)
//                                context.finish();
//                        }
//                    }
//                });
//
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void customSaveAlert(Activity activity, String title, String msg) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_confirmation);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(title);
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
//                btDialogNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//
//                        SaveListener saveListener = (SaveListener) activity;
//                        saveListener.submitData();
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void showProfile(Activity activity) {
//        try {
//            final Dialog dialog = new Dialog(activity);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_dialog_profile);
//                dialog.setCancelable(false);
//                TextView tvUserName = dialog.findViewById(R.id.user_name);
//                TextView tvUserId = dialog.findViewById(R.id.user_id);
//                TextView tvDesig = dialog.findViewById(R.id.designation);
//                TextView tvPlace = dialog.findViewById(R.id.place_of_work);
//                SharedPreferences sharedPreferences = TWDApplication.get(activity).getPreferences();
//                String userName = sharedPreferences.getString(AppConstants.OFFICER_NAME, "");
//                tvUserName.setText(userName);
//                String userId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
//                tvUserId.setText(userId);
//                String designation = sharedPreferences.getString(AppConstants.OFFICER_DES, "");
//                tvDesig.setText(designation);
//                String place = sharedPreferences.getString(AppConstants.OFF_PLACE_OF_WORK, "");
//                tvPlace.setText(place);
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void showToast(Context context, String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//    }
//
//    public static float calcDistance(Location crtLocation, Location desLocation) {
//        return crtLocation.distanceTo(desLocation); // in meters
//    }
//
//    public static double round(double value, int places) {
//        if (places < 0) throw new IllegalArgumentException();
//
//        long factor = (long) Math.pow(10, places);
//        value = value * factor;
//        long tmp = Math.round(value);
//        return (double) tmp / factor;
//    }
//
//    private static Uri fileUri;
//    private static Uri imageUri;
//    private static final int MEDIA_TYPE_IMAGE = 1;
//    final static String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/CTW";
//    private static File mediaFile;
//    private static Bitmap bitmap;
//
//    public static void takeSCImage(Activity activity, ScrollView llScroll, String title, int len) {
//        try {
//
//            int totalHeightOfScrollView = llScroll.getChildAt(0).getHeight();
//
//            bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), totalHeightOfScrollView);
//            createPdf(activity, bitmap, title, len);
//
////            fileUri = getOutputMediaFileUri(activity, MEDIA_TYPE_IMAGE);
////
////            if (bitmap != null) {
////                store(bitmap, activity);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void takeSCImageSchool(Activity activity, ScrollView llScroll, String title, int len, ArrayList<ScrollView> scrollViews) {
//        try {
//
//            int totalHeightOfScrollView = llScroll.getChildAt(0).getHeight();
//
////            bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(),totalHeightOfScrollView);
//            createPdfSchool(activity, title, len,scrollViews);
//
////            fileUri = getOutputMediaFileUri(activity, MEDIA_TYPE_IMAGE);
////
////            if (bitmap != null) {
////                store(bitmap, activity);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    static File filePath;
//
//
//    private static void createPdf(Activity activity, Bitmap bitmap, String title, int len) {
//        try {
//            WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//            //  Display display = wm.getDefaultDisplay();
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            float hight = displaymetrics.heightPixels;
//            float width = displaymetrics.widthPixels;
//
//            int convertHighet = (int) hight, convertWidth = (int) width;
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//
//            PdfDocument document = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                document = new PdfDocument();
//                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//                PdfDocument.Page page = document.startPage(pageInfo);
//
//                Canvas canvas = page.getCanvas();
//                Paint paint = new Paint();
//                canvas.drawPaint(paint);
//
//                document.finishPage(page);
//
//                bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//
//                paint.setColor(Color.BLUE);
//                canvas.drawBitmap(bitmap, 0, 0, null);
//                document.finishPage(page);
//
//                // write the document content
//                File targetPdf = new File(activity.getExternalFilesDir(null) + "/Android/data/"
//                        + "Files/");
//
//                if (!targetPdf.exists()) {
//                    if (!targetPdf.mkdirs()) {
//                        Log.d("TAG", "Oops! Failed create " + "Android File Upload"
//                                + " directory");
//                    }
//                }
//
//
//                filePath = new File(targetPdf.getPath() + "/" + title + ".pdf");
//                filePath = new File(filePath.getPath());
//                try {
//                    filePath.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    document.writeTo(new FileOutputStream(filePath));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//                }
//
//                // close the document
//                document.close();
//                Toast.makeText(activity, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();
//
//    //            openGeneratedPDF(activity);
//
//                shareImage(activity, fileUri);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private static void createPdfSchool(Activity activity, String title, int len, ArrayList<ScrollView> scrollViews) {
//        int totWei = 0, totHei = 0;
//        PdfDocument document = null;
//        Paint paint = new Paint();
//        for (int i = 0; i < scrollViews.size(); i++) {
//
//            float hight = scrollViews.get(i).getHeight();
//            float width = scrollViews.get(i).getWidth();
//            int convertHighet = (int) hight, convertWidth = (int) width;
//            totHei += convertHighet;
//            totWei += convertWidth;
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                document = new PdfDocument();
//                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//                PdfDocument.Page page = document.startPage(pageInfo);
//
//                Canvas canvas = page.getCanvas();
//
//                canvas.drawPaint(paint);
//                bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//                paint.setColor(Color.BLUE);
//                canvas.drawBitmap(bitmap, 0, 0, null);
//                document.finishPage(page);
//            }
//
//
//        }
//
//
//        // write the document content
//        File targetPdf = new File(activity.getExternalFilesDir(null) + "/Android/data/"
//                + "Files/");
//
//        if (!targetPdf.exists()) {
//            if (!targetPdf.mkdirs()) {
//                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
//                        + " directory");
//            }
//        }
//
//
//        filePath = new File(targetPdf.getPath() + "/" + title + ".pdf");
//        filePath = new File(filePath.getPath());
//        try {
//            filePath.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                document.writeTo(new FileOutputStream(filePath));
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        // close the document
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            document.close();
//        }
//        Toast.makeText(activity, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();
//
////            openGeneratedPDF(activity);
//
//        shareImage(activity, fileUri);
//
//
//    }
//
//    private static void openGeneratedPDF(Activity activity) {
//        File file = new File(filePath.getPath());
//        if (file.exists()) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
//            intent.setDataAndType(uri, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            try {
//                activity.startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(activity, "No Application available to view pdf", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//
//    private static Bitmap loadBitmapFromView(View v, int width, int height) {
//        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.draw(c);
//
//        return b;
//    }
//
//    public static Bitmap getScreenShot(View ll) {
//        ll.setDrawingCacheEnabled(true);
//        ll.buildDrawingCache(true);
//        return Bitmap.createBitmap(ll.getDrawingCache());
//    }
//
//    public static void store(Bitmap bm, Activity activity) {
//        try {
//            FileOutputStream fOut = new FileOutputStream(mediaFile);
//            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//            fOut.flush();
//            fOut.close();
//
//            shareImage(activity, fileUri);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static void shareImage(Activity activity, Uri fileUri) {
//        try {
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_SEND);
//            intent.setType("image/*");
//
//            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
//            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
//            try {
//                activity.startActivity(Intent.createChooser(intent, "Project Data"));
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(activity, "No App Available", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Uri getOutputMediaFileUri(Activity activity, int type) {
//        File imageFile = getOutputMediaFile(type);
//        imageUri = FileProvider.getUriForFile(
//                activity,
//                "com.cgg.twdinspection.provider", //(use your app signature + ".provider" )
//                imageFile);
//        return imageUri;
//    }
//
//    private static File getOutputMediaFile(int type) {
//        File mediaStorageDir = new File(dirPath);
//        if (!mediaStorageDir.exists()) {
//            mediaStorageDir.mkdirs();
//        }
//
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                    + getTimeStamp() + ".png");
//        } else {
//            return null;
//        }
//        return mediaFile;
//    }
//
//    private static String getTimeStamp() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
//        return simpleDateFormat.format(new Date());
//    }

    public static boolean ValidateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = false;
        try {
            isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
            if (isValidAadhar) {
                isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValidAadhar;
    }
}
