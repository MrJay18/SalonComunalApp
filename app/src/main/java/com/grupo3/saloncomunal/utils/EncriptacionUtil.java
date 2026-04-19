package com.grupo3.saloncomunal.utils;

import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class EncriptacionUtil {

    // MISMA clave que usa el backend en C# - NO LA CAMBIES
    private static final String CLAVE = "SalonComunalSecret2026";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BITS = 128; // 16 bytes
    private static final String ALGORITMO = "AES/GCM/NoPadding";

    /**
     * Encripta un texto usando AES-256 GCM
     * Compatible con AES256GCMEncryption.cs del backend
     */
    public static String encriptar(String textoPlano) {
        try {
            if (textoPlano == null || textoPlano.isEmpty()) return null;

            byte[] keyBytes = convertirClaveABytes(CLAVE);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            // Generar IV aleatorio
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new GCMParameterSpec(TAG_LENGTH_BITS, iv));

            byte[] textoBytes = textoPlano.getBytes(StandardCharsets.UTF_8);
            byte[] cipherTextConTag = cipher.doFinal(textoBytes);

            // Combinar IV + cipherText + tag
            byte[] resultado = new byte[IV_LENGTH + cipherTextConTag.length];
            System.arraycopy(iv, 0, resultado, 0, IV_LENGTH);
            System.arraycopy(cipherTextConTag, 0, resultado, IV_LENGTH, cipherTextConTag.length);

            return Base64.encodeToString(resultado, Base64.NO_WRAP);
        } catch (Exception e) {
            BitacoraUtil.error("EncriptacionUtil", "Error al encriptar", e);
            return null;
        }
    }

    /**
     * Desencripta un texto usando AES-256 GCM
     */
    public static String desencriptar(String textoEncriptadoBase64) {
        try {
            if (textoEncriptadoBase64 == null || textoEncriptadoBase64.isEmpty()) return null;

            byte[] keyBytes = convertirClaveABytes(CLAVE);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            byte[] datos = Base64.decode(textoEncriptadoBase64, Base64.NO_WRAP);

            // Separar IV del resto
            byte[] iv = new byte[IV_LENGTH];
            byte[] cipherTextConTag = new byte[datos.length - IV_LENGTH];
            System.arraycopy(datos, 0, iv, 0, IV_LENGTH);
            System.arraycopy(datos, IV_LENGTH, cipherTextConTag, 0, cipherTextConTag.length);

            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new GCMParameterSpec(TAG_LENGTH_BITS, iv));

            byte[] textoDesencriptado = cipher.doFinal(cipherTextConTag);
            return new String(textoDesencriptado, StandardCharsets.UTF_8);
        } catch (Exception e) {
            BitacoraUtil.error("EncriptacionUtil", "Error al desencriptar", e);
            return null;
        }
    }

    /**
     * Convierte la clave a 32 bytes usando SHA-256
     * (igual que hace el backend con ConvertToKeyBytes)
     */
    private static byte[] convertirClaveABytes(String clave) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        return sha.digest(clave.getBytes(StandardCharsets.UTF_8));
    }
}
