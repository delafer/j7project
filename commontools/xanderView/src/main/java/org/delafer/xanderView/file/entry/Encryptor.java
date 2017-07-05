package org.delafer.xanderView.file.entry;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	
	static byte[] arrKey = randomKey(16); 
	static Cipher encrypt;
	static Cipher decrypt;
	static SecretKeySpec sks;
	static {
		Security.setProperty("crypto.policy", "unlimited");
		encrypt = getCipher(true);
		decrypt = getCipher(false);
	}
	
	public static byte[] randomKey(int size) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	};
	
	private static Cipher getCipher(boolean mode) {
		Cipher r = null;
		try {
			r = Cipher.getInstance("AES_128/ECB/NoPadding", "SunJCE");
//			SecretKeyFactory f = SecretKeyFactory.getInstance("AES", "SunJCE");
			sks = new SecretKeySpec(arrKey, "AES");
//			SecretKey key = f.generateSecret(sks);
//			r.init(mode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, sks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public Encryptor() {
	}

	public static ByteBuffer encrypt(ByteBuffer input) {
		try {
			encrypt.init(Cipher.ENCRYPT_MODE, sks);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return encrypt0(encrypt, input);
	}
	
	public static ByteBuffer decrypt(ByteBuffer input) {
		try {
			decrypt.init(Cipher.DECRYPT_MODE, sks);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return decrypt0(decrypt, input);
	}


	
	 public static ByteBuffer encrypt0(Cipher cipher, ByteBuffer input) {
    if (input == null || !input.hasRemaining()) return input;
    try {
     int inputSize = input.remaining();
      ByteBuffer output = ByteBuffer.allocate(cipher.getOutputSize(inputSize));
		// OpensslCipher#update will maintain crypto context.
		int n = cipher.update(input, output);
		if (n < input.remaining()) {
			cipher.doFinal(input, output);
		}
      
      output.rewind(); //.flip();
      return output;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
	 
	  public static ByteBuffer decrypt0(Cipher cipher, ByteBuffer input) {
	    if (input == null || !input.hasRemaining()) return input;
	    try {
	      int inputSize = input.remaining();
	      ByteBuffer output = ByteBuffer.allocate(cipher.getOutputSize(inputSize));
			// OpensslCipher#update will maintain crypto context.
			int n = cipher.update(input, output);
			if (n < input.remaining()) {
				cipher.doFinal(input, output);
			} 
			output.rewind(); //.flip();
	      return output;
	    } catch (Exception e) {
	      throw new IllegalStateException(e);
	    }
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
