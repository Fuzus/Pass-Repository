package br.com.fuzusnoary.passrepository.util;

import android.content.Context;
import android.os.Build;

import androidx.biometric.BiometricManager;

public class FingerPrintUtils {

    public static boolean isAvailable(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        BiometricManager biometricManager = BiometricManager.from(context);
        int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            return true;
        } else {
            return false;
        }
    }
}
