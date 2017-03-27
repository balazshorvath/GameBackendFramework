package hu.sovaroq.core.network;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import lombok.Getter;

public class GameSocket {

    private Socket socket;

    private GameOutputStream outputStream;

    private GameInputStream inputStream;

    @Getter
    private SecretKey secretKey;

    private void generateKey() {
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
            this.outputStream = new GameOutputStream(socket.getOutputStream(), cipherEncode);
            this.inputStream = new GameInputStream(socket.getInputStream(), cipherDecode);
        }
    }

}
