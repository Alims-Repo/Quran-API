package com.nelu.license

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.net.URL
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class PurchaseCodeValidator(
    context: Context,
    key: String,
    url: String
) {

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "SecurePurchaseValidatorPrefs",
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (encryptedPrefs.getString("code", "") == key) return@launch
                if (context.checkPurchaseCodeIsValid(key)) {
                    Log.e("URL", URL(KEY_VALIDATOR + encrypt(key)).readText() + "-" + context.packageName)
                    withContext(Dispatchers.Main) {
                        val intent = Intent(context, LicenseCheckActivity::class.java)
                        intent.putExtra("URL", url)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }
                } else encryptedPrefs.edit { putString("code", key) }
            } catch (e: FileNotFoundException) {
                Log.e("FileNotFoundException", "Error validating purchase code: $e")
                withContext(Dispatchers.Main) {
                    val intent = Intent(context, LicenseCheckActivity::class.java)
                    intent.putExtra("URL", url)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e("Exception", "Error validating purchase code: $e")
            }
        }
    }

    private fun Context.checkPurchaseCodeIsValid(key: String) =
        URL(KEY_VALIDATOR + encrypt(key)).readText() != packageName

    private fun encrypt(text: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec: SecretKey = SecretKeySpec(SECRET_KEY_256.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(KEY_256.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    companion object {
        private const val KEY_256 = "P0q1R2s3T4u5V6w7"
        private const val SECRET_KEY_256 = "QwErTyUiOpAsDfGhJkLzXcVbNm123456"
        private const val KEY_VALIDATOR = "https://raw.githubusercontent.com/NeluCode/purchase-codes/refs/heads/main/"
    }
}