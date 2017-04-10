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

public class GameSocket{

    private GameOutputStream outputStream;

    private GameInputStream inputStream;

    @Getter
    private SecretKey secretKey;
    
    private Socket clientSocket;

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
    
    public GameSocket(){
    	generateKey();
    }

    public void start(Socket socket) throws Exception {
    	if(secretKey == null) throw new FrameworkException("Key must be generated first!");
    	
    	this.clientSocket = socket;
    	
        Cipher cipherEncode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherEncode.init(Cipher.ENCRYPT_MODE, secretKey);

        Cipher cipherDecode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipherDecode.init(Cipher.DECRYPT_MODE, secretKey);

        if (clientSocket.isBound()) {
            this.outputStream = new GameOutputStream(clientSocket.getOutputStream(), cipherEncode);
            this.inputStream = new GameInputStream(clientSocket.getInputStream(), cipherDecode);
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
    	try {
			message.writeMessage(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    		clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
