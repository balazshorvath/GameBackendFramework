package hu.sovaroq.core.network;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;

import hu.sovaroq.core.network.messages.NetworkMessage;
import hu.sovaroq.core.network.stream.GameInputStream;
import hu.sovaroq.core.network.stream.GameOutputStream;

public class GameSocket{

    private GameOutputStream outputStream;

    private GameInputStream inputStream;

    private SSLSocket clientSocket;

    
    public GameSocket(){
    }

    public void start(Socket socket) throws Exception {

        if (clientSocket.isBound()) {
            this.outputStream = new GameOutputStream(clientSocket.getOutputStream());
            this.inputStream = new GameInputStream(clientSocket.getInputStream());
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
