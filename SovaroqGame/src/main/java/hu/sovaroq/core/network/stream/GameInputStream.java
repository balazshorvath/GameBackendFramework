package hu.sovaroq.core.network.stream;

import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

import hu.sovaroq.core.network.exception.DataReadException;
import hu.sovaroq.core.network.messages.NetworkMessage;

public class GameInputStream extends CipherInputStream {

	public GameInputStream(InputStream is, Cipher c) {
		super(is, c);
	}

	public String readString(int lenght) throws IOException {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < lenght; i++) {
			int read = read();
			if (read == -1 || read == 0) {
				throw new DataReadException("Unexpected end of stream");
			} else if (Character.isAlphabetic(read)) {
				throw new DataReadException("Unexpected character");
			} else {
				ret.append(Character.toString((char) read));
			}
		}
		return ret.toString();
	}

	/**
	 * Searches for the next message starting string defined in
	 * <code>NetworkMessage</code>.
	 * 
	 * @return Returns the message type, or 0x00 is no bytes are available to
	 *         read.
	 * @throws IOException
	 */
	public byte startNextMessageRead() throws IOException {
		StringBuffer buffer = new StringBuffer();
		if (this.available() != 0) {
			do {
				int read = read();
				if (read == -1 || read == 0) {
					break;
				} else {
					buffer.append(Character.toString((char) read));
				}
				if (buffer.length() > NetworkMessage.MESSAGE_START.length()) {
					buffer.deleteCharAt(0);
				}
				if (buffer.toString().equals(NetworkMessage.MESSAGE_START)) {
					break;
				}
			} while (true);
			
			return (byte) read();
		}else{
			return 0x00;
		}

		
	}

	public Double readDouble() {
		return 0d;
	}

	public Integer readInt() {
		return 0;
	}
}