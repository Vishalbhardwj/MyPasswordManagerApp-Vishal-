//package com.example.mypasswordmanagerapp
//
//import android.security.keystore.KeyGenParameterSpec
//import android.security.keystore.KeyProperties
//import android.util.Base64
//import java.security.KeyStore
//import java.security.SecureRandom
//import javax.crypto.Cipher
//import javax.crypto.KeyGenerator
//import javax.crypto.SecretKey
//import javax.crypto.spec.GCMParameterSpec
//import javax.crypto.spec.SecretKeySpec
//
//object EncryptionUtil {
//    private const val TRANSFORMATION = "AES/GCM/NoPadding"
//    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
//    private const val KEY_ALIAS = "MyKeyAlias"
//
//    fun generateSecretKey(): SecretKeySpec {
//        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
//        keyStore.load(null)
//
//        // Check if the key already exists in the keystore
//        val existingKey = keyStore.getKey(KEY_ALIAS, null)
//        if (existingKey is SecretKey) {
//            return SecretKeySpec(existingKey.encoded, "AES")
//        }
//
//        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
//        keyGenerator.init(
//            KeyGenParameterSpec.Builder(KEY_ALIAS,
//                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
//                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                .build()
//        )
//        val secretKey = keyGenerator.generateKey()
//        return SecretKeySpec(secretKey.encoded, "AES")
//    }
//
//    fun encrypt(data: String, secretKey: SecretKeySpec): String {
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        val iv = ByteArray(12)
//        SecureRandom().nextBytes(iv)
//        val ivSpec = GCMParameterSpec(128, iv)
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
//        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
//        return Base64.encodeToString(iv + encryptedData, Base64.DEFAULT)
//    }
//
//    fun decrypt(data: String, secretKey: SecretKeySpec): String {
//        val decodedData = Base64.decode(data, Base64.DEFAULT)
//        val iv = decodedData.copyOfRange(0, 12)
//        val encryptedData = decodedData.copyOfRange(12, decodedData.size)
//        val cipher = Cipher.getInstance(TRANSFORMATION)
//        val ivSpec = GCMParameterSpec(128, iv)
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
//        val decryptedData = cipher.doFinal(encryptedData)
//        return String(decryptedData, Charsets.UTF_8)
//    }
//}