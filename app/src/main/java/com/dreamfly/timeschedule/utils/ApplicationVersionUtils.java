package com.dreamfly.timeschedule.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ApplicationVersionUtils {

    private static final String TAG = ApplicationVersionUtils.class.getSimpleName();
    private static AtomicReference<ClientVersionInfo> clientVersionInfoReference=new AtomicReference<ClientVersionInfo>();


    public static ClientVersionInfo getApplicationVersionInfo(Context context) {
        ClientVersionInfo versionInfo=clientVersionInfoReference.get();
        if (versionInfo != null) {
            return versionInfo;
        }

        synchronized(clientVersionInfoReference){
            versionInfo=clientVersionInfoReference.get();
            if (versionInfo!=null){
                return versionInfo;
            }
            versionInfo=initApplicationVersionInfo(context);
            clientVersionInfoReference.set(versionInfo);
            return versionInfo;
        }
    }

    private static ClientVersionInfo initApplicationVersionInfo(Context context) {
        ClientVersionInfo versionInfo = new ClientVersionInfo();
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            versionInfo = new ClientVersionInfo();
            versionInfo.packageName = info.packageName;
            versionInfo.versionCode = info.versionCode;
            versionInfo.versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if (width < height) {
            versionInfo.displayWidth = width;
            versionInfo.displayHeight = height;
        } else {
            versionInfo.displayWidth = height;
            versionInfo.displayHeight = width;
        }
        return versionInfo;
    }

    public static class ClientVersionInfo {
        private String packageName;
        private int versionCode;
        private String versionName;
        private int displayWidth;
        private int displayHeight;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getDisplayWidth() {
            return displayWidth;
        }

        public void setDisplayWidth(int displayWidth) {
            this.displayWidth = displayWidth;
        }

        public int getDisplayHeight() {
            return displayHeight;
        }

        public void setDisplayHeight(int displayHeight) {
            this.displayHeight = displayHeight;
        }
    }

    public static byte[] getSign(Context context) {

        PackageManager pm = context.getPackageManager();

        List<PackageInfo> apps = pm
                .getInstalledPackages(PackageManager.GET_SIGNATURES);

        Iterator<PackageInfo> iter = apps.iterator();
        String packetName = context.getPackageName();
        while (iter.hasNext()) {

            PackageInfo info = iter.next();

            String name = info.packageName;
            //Log.v(TAG, "name=" + name);
            // 按包名 取签名
            return info.signatures[0].toByteArray();
        }
        return null;
    }

    public static String getPublicKey(byte[] signature) {
        try {

            CertificateFactory certFactory = CertificateFactory

                    .getInstance("X.509");

            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));

            String publickey = cert.getPublicKey().toString();

            publickey = publickey.substring(publickey.indexOf("modulus: ") + 9,

                    publickey.indexOf("\n", publickey.indexOf("modulus:")));

            //Log.d("TRACK", publickey);

            return publickey;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getSignature(Context context) {
        android.content.pm.Signature[] sigs;
        try {
            sigs = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
            if (sigs != null && sigs.length > 0) {
                //Log.e(TAG, "sigs.len=" + sigs.length);
                //Log.e(TAG, sigs[0].toCharsString());
                String s = parseSignature(context.getPackageName(),
                        sigs[0].toByteArray());
                return s;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseSignature(String name, byte[] signature) {

        try {

            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");

            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));

            String pubKey = cert.getPublicKey().toString();
            if (!TextUtils.isEmpty(pubKey)) {
                int index = pubKey.indexOf("modulus: ");
                if (index >= 0) {
                    pubKey = pubKey.substring(index + 9,
                            pubKey.indexOf("\n", index));
                    //Log.e(TAG, "pubKey=" + pubKey);
                    return pubKey.substring(10, 256);
					/*
					 * String signNumber = cert.getSerialNumber().toString();
					 * Log.e(TAG,"signNumber=" + signNumber); if
					 * (signNumber!=null){ String ll=pubKey + signNumber; String
					 * key=StringUtils.makeLength(name + ll , 24); byte[] b=
					 * DESUtil.encrypt(key, ll.getBytes()); if (b!=null){ return
					 * new String(b); } }
					 */
                }
            }
            return null;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppName(Context context) {
        String applicationName = "";
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);

            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return applicationName;
    }

}
