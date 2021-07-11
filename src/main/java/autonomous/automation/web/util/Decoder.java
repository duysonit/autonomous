package autonomous.automation.web.util;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class Decoder {

    public static String usr = "";
    public static String pass = "";

    public Decoder(String path) throws IOException {
        Key privateKey = readKeyFromFile(path + File.separator + "private.key");
        String[] results = decryptFile(path + File.separator + "rsa_encrypt.txt", privateKey);
        if (results.length == 2) {
            usr = results[0];
            pass = results[1];
        }
    }

    public String getUserName() {
        return Decoder.usr;
    }

    public String getPassword() {
        return Decoder.pass;
    }

    static Key readKeyFromFile(String keyFileName) throws IOException {
        InputStream in = new FileInputStream(keyFileName);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(
                in));
        try {
            BigInteger m = (BigInteger) oin.readObject();
            BigInteger e = (BigInteger) oin.readObject();
            KeyFactory fact = KeyFactory.getInstance("RSA");
            if (keyFileName.startsWith("public")) {
                return fact.generatePublic(new RSAPublicKeySpec(m, e));
            } else {
                return fact.generatePrivate(new RSAPrivateKeySpec(m, e));
            }
        } catch (Exception e) {
            throw new RuntimeException("Spurious serialisation error", e);
        } finally {
            oin.close();
        }
    }

    private static String[] decryptFile(String srcFileName, Key key) {
        InputStream inputReader = null;
        String s = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //String textLine = null;
            //RSA encryption data size limitations are slightly less than the key modulus size,
            //depending on the actual padding scheme used (e.g. with 1024 bit (128 byte) RSA key,
            //the size limit is 117 bytes for PKCS#1 v 1.5 padding. (http://www.jensign.com/JavaScience/dotnet/RSAEncrypt/)
            byte[] buf = new byte[128];
            int bufl;
            // init the Cipher object for Encryption...
            cipher.init(Cipher.DECRYPT_MODE, key);

            // start FileIO
            inputReader = new FileInputStream(srcFileName);
            while ((bufl = inputReader.read(buf)) != -1) {
                byte[] encText = null;
                encText = decrypt(copyBytes(buf, bufl), (PrivateKey) key);
                s = s + (new String(encText));
            }
        } catch (Exception e) {

        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                // do nothing...
            } // end of inner try, catch (Exception)...
        }
        String[] results = s.split(":");
        return results;
    }

    public static byte[] copyBytes(byte[] arr, int length) {
        byte[] newArr = null;
        if (arr.length == length) {
            newArr = arr;
        } else {
            newArr = new byte[length];
            for (int i = 0; i < length; i++) {
                newArr[i] = (byte) arr[i];
            }
        }
        return newArr;
    }

    /**
     * Decrypt text using private key
     *
     * @param text The encrypted text
     * @param key The private key
     * @return The unencrypted text
     * @throws java.lang.Exception
     */
    public static byte[] decrypt(byte[] text, PrivateKey key) throws Exception {
        byte[] dectyptedText = null;
        try {
            // decrypt the text using the private key
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.DECRYPT_MODE, key);
            dectyptedText = cipher.doFinal(text);
        } catch (Exception e) {

            throw e;
        }
        return dectyptedText;

    }
}
