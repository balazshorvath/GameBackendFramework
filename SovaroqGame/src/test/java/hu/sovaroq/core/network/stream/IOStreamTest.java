package hu.sovaroq.core.network.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Before;
import org.junit.Test;

public class IOStreamTest {

	private GameOutputStream outputStream;

	private GameInputStream inputStream;

	private SecretKey secretKey;

	public void generateKey() {
		try {
			KeyGenerator keyGen;
			keyGen = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom(); // cryptograph. secure
														// random
			keyGen.init(random);
			secretKey = keyGen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setup() {
		
		generateKey();

		try {
			Cipher cipherEncode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipherEncode.init(Cipher.ENCRYPT_MODE, secretKey);

			Cipher cipherDecode = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipherDecode.init(Cipher.DECRYPT_MODE, secretKey);
			
			InputStream is;
			OutputStream os;

			this.outputStream = new GameOutputStream(System.out, cipherEncode);
			this.inputStream = new GameInputStream(System.in, cipherDecode);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Test
	public void testStreams() throws IOException {
		
		Thread writer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					outputStream.writeString("KAKILAKI");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		Thread reader = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String input = inputStream.readString();
					System.out.println(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		
		writer.start();
		reader.start();

	}
	
}
