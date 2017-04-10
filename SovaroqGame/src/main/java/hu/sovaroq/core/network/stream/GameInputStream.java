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
			ret.append(checkread(read));
		}
		return ret.toString();
	}
	
	public String readString() throws IOException {
		StringBuffer buffer = new StringBuffer();
		if (this.available() != 0) {
			do {
				int read = read();
				String character = checkread(read);
				if ((byte) read == NetworkMessage.DATA_DELIMITER) {
					break;
				}
				buffer.append(character);
			} while (true);
		} else {
			return null;
		}
		return buffer.toString();
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
		if (this.available() != 0) {
			do {
				int read = read();
				if (read == -1) {
					throw new DataReadException("Unexpected end of stream");
				} else if (read == NetworkMessage.MESSAGE_START) {
					break;
				}
			} while (true);

			return (byte) read();
		} else {
			return 0x00;
		}
	}
	
	public void checkFinishMessage() throws IOException{
		do {
			int read = read();
			if (read == -1) {
				throw new DataReadException("Unexpected end of stream");
			} else if (read == NetworkMessage.MESSAGE_END) {
				break;
			}
		} while (true);
	}

	public Double readDouble() throws IOException {
		StringBuffer buffer = new StringBuffer();
		if (this.available() != 0) {
			do {
				int read = read();
				String character = checkread(read);
				if ((byte) read == NetworkMessage.DATA_DELIMITER) {
					break;
				}
				buffer.append(character);
			} while (true);
		} else {
			return null;
		}
		return Double.parseDouble(buffer.toString());
	}

	public Integer readInt() throws IOException {
		StringBuffer buffer = new StringBuffer();
		if (this.available() != 0) {
			do {
				int read = read();
				String character = checkread(read);
				if ((byte) read == NetworkMessage.DATA_DELIMITER) {
					break;
				}
				buffer.append(character);
			} while (true);
		} else {
			return null;
		}
		return Integer.parseInt(buffer.toString());
	}

	private String checkread(int read) throws DataReadException {
		if (read == -1) {
			throw new DataReadException("Unexpected end of stream");
		} else if (!Character.isAlphabetic(read)) {
			throw new DataReadException("Unexpected character");
		} else {
			return Character.toString((char) read);
		}
	}
}
