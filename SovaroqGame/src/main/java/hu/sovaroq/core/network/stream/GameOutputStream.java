package hu.sovaroq.core.network.stream;

import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

public class GameOutputStream extends CipherOutputStream{

	public GameOutputStream(OutputStream os, Cipher c) {
		super(os, c);
	}

}
