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

	@Before
	public void setup() {

		this.outputStream = new GameOutputStream(System.out);
		this.inputStream = new GameInputStream(System.in);

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
