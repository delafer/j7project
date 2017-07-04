package org.delafer.xanderView.file.entry;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class Encryptor {

	public Encryptor() {
	}

	public ByteBuffer encrypt(ByteBuffer input) {
		return input;
	}

	public ByteBuffer decrypt(ByteBuffer input) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cipher.update(arg0, arg1, arg2)
//        cipher.init(mode, key.getKey());
		return input;
	}

//	 public static ByteBuffer encrypt(ByteBuffer clear) {
//		    if (clear == null || !clear.hasRemaining()) return clear;
//		    try {
//		      SecretKeySpec sKeySpec = new SecretKeySpec(getRawKey(encryptionSeed), "AES");
//		      Cipher cipher = Cipher.getInstance("AES");
//		      cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
//		      ByteBuffer encrypted = ByteBuffer.allocate(cipher.getOutputSize(clear.remaining()));
//		      cipher.doFinal(clear, encrypted);
//		      encrypted.rewind(); //.flip();
//		      return encrypted;
//		    } catch (Exception e) {
//		      throw new IllegalStateException(e);
//		    }
//		  }
//
//		  public static ByteBuffer decrypt(ByteBuffer encrypted) {
//		    if (encrypted == null || !encrypted.hasRemaining()) return encrypted;
//		    try {
//		      SecretKeySpec sKeySpec = new SecretKeySpec(getRawKey(encryptionSeed), "AES");
//		      Cipher cipher = Cipher.getInstance("AES");
//		      cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
//		      ByteBuffer decrypted = ByteBuffer.allocate(cipher.getOutputSize(encrypted.remaining()));
//		      cipher.doFinal(encrypted, decrypted);
//		      decrypted.rewind(); //.flip();
//		      return decrypted;
//		    } catch (Exception e) {
//		      throw new IllegalStateException(e);
//		    }
//		  }

}
