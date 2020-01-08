package com.FiftyOneDegree.research.FiftyOneDegreeCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DeviceInfoUtil {

    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String AES_ENCRYPTION_ALGORITHM = "AES";
    private static final String CHARACTER_ENCODING = "UTF-8";

    /**
     * Convert Device info to JSON
     *
     * @param deviceInfo
     * @return String which represents info in json format
     */
    public static String convertToJSON(DeviceInfo deviceInfo) {
        Gson gson = new Gson();
        String json = gson.toJson(deviceInfo);
        return json;
    }

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>) s).isEmpty();
        }
        if (s instanceof List) {
            return ((List<?>) s).isEmpty();
        }
        if (s instanceof Object[]) {
            return (((Object[]) s).length == 0);
        }
        return false;
    }

    public static String getTimeStamp() {
        //getTime() returns current time in milliseconds
        long time = new Date().getTime();
        //Passed the milliseconds to constructor of Timestamp class
        Timestamp ts = new Timestamp(time);
        return ts.toString();
    }

    private static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    public static String encrypt(String plainText) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextbytes = plainText.getBytes(CHARACTER_ENCODING);
        byte[] keyBytes = getKeyBytes(DeviceInfoConstants.ENCRYPTION_KEY);
        return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT);
    }

    private static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes(CHARACTER_ENCODING);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

    public static void putPreferenceValue(Context context, String key, Object value) throws Exception {
        SharedPreferences settings = context.getSharedPreferences(DeviceInfoConstants.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (value != null) {
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            }
        }
        editor.commit();
    }

    public static Boolean getPreferenceValue(Context context, String key) throws Exception {
        SharedPreferences settings = context.getSharedPreferences(DeviceInfoConstants.PREFS_NAME, Context.MODE_PRIVATE);
        Boolean value = settings.getBoolean(key, false);
        return value;
    }
}

