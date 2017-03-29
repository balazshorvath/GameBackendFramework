package hu.sovaroq.core.network;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import hu.sovaroq.core.network.messages.NetworkMessage;
import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;
import hu.sovaroq.framework.exception.FrameworkException;
import lombok.Getter;

public class GameSocket extends Socket{
	
    private GameOutputStream outputStream;

    private GameInputStream inputStream;

    @Getter
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

    public void start() throws Exception {
    	if(secretKey == null) throw new FrameworkException("Key must be generated first!");
    	
        Cipher cipherEncode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherEncode.init(Cipher.ENCRYPT_MODE, secretKey);

        Cipher cipherDecode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherDecode.init(Cipher.DECRYPT_MODE, secretKey);

        if (this.isBound()) {
            this.outputStream = new GameOutputStream(this.getOutputStream(), cipherEncode);
            this.inputStream = new GameInputStream(this.getInputStream(), cipherDecode);
        }
    }
    
    /**
     * Reads a <code>NetworkMessage</code> from the socket.
     * 
     * @return <code>NetworkMessage</code> or null, if no message is available.
     */
    public NetworkMessage read(){
    	return null;
    }
    
    public void send(NetworkMessage message){
    	message.writeMessage(outputStream);
    }
    
    public void disconnect(){
    	try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	try {
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
