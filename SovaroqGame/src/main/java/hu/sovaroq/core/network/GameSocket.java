package hu.sovaroq.core.network;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class GameSocket {

    private Socket socket;

    private CipherOutputStream outputStream;

    private CipherInputStream inputStream;

    private SecretKey secretKey;

    public void generateKey() {
        try {
            KeyGenerator keyGen;
            keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom(); // cryptograph. secure random
            keyGen.init(random);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void start(Socket socket) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        generateKey();

        Cipher cipherEncode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherEncode.init(Cipher.ENCRYPT_MODE, secretKey);

        Cipher cipherDecode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherDecode.init(Cipher.DECRYPT_MODE, secretKey);

        this.socket = socket;
        if (socket != null && socket.isBound()) {
            this.outputStream = new CipherOutputStream(socket.getOutputStream(), cipherEncode);
            this.inputStream = new CipherInputStream(socket.getInputStream(), cipherDecode);
        }
    }

}
