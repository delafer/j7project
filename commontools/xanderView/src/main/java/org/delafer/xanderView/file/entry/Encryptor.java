package org.delafer.xanderView.file.entry;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.*;

import javax.crypto.Cipher;

import net.j7.commons.utils.ByteUtils;


public class Encryptor {

	static final Charset utf8;

	static Cipher encrypt;
	static Cipher decrypt;
	static Key sks;
	static {
		utf8 = Charset.forName("UTF-8");
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
//			r = Cipher.getInstance("AES_128/ECB/NoPadding", "SunJCE");
			r = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
//			SecretKeyFactory f = SecretKeyFactory.getInstance("AES", "SunJCE");
			sks = CipherKey.getPBEKey("Siloch12aaa");
//			SecretKey key = f.generateSecret(sks);
			r.init(mode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, sks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public Encryptor() {
	}

	public static ByteBuffer encrypt(ByteBuffer input, String name) {
//		try {
//			encrypt.init(Cipher.ENCRYPT_MODE, sks/*,  new IvParameterSpec(CipherKey.iv)*/);
//		} catch (InvalidKeyException /*| InvalidAlgorithmParameterException*/ e) {
//			e.printStackTrace();
//		}
		return encrypt0(encrypt, input, name);
	}

	public static ByteBuffer decrypt(ByteBuffer input) {
//		try {
//			decrypt.init(Cipher.DECRYPT_MODE, sks);
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//		}
		return decrypt0(decrypt, input);
	}



	 public static ByteBuffer encrypt0(Cipher cipher, ByteBuffer input, String name) {
    if (input == null || !input.hasRemaining()) return input;
    try {
     byte[] encName = name.getBytes(utf8);
     int inputSize = input.remaining() + 3 + encName.length;
     System.out.println(">"+inputSize);
      ByteBuffer output = ByteBuffer.allocate(cipher.getOutputSize(inputSize));

       cipher.update(ByteBuffer.wrap(new byte[] {1}), output);
       cipher.update(ByteBuffer.wrap(ByteUtils.UIntToByte2(encName.length)), output);
       cipher.update(ByteBuffer.wrap(encName), output);

//		// OpensslCipher#update will maintain crypto context.
//		int n = cipher.update(input, output);
//		if (n < input.remaining()) {
//			cipher.doFinal(input, output);
//		}
//		else {
			cipher.doFinal(input, output);
//		}

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
//			int n = cipher.update(input, output);
//			if (n < input.remaining()) {
//				cipher.doFinal(input, output);
//			}
//			else {
				cipher.doFinal(input, output);
//			}
			 //.flip();
			output.rewind();
			byte alg = output.get();
			byte[] len = new byte[2];
			output.get(len, 0, 2);
			int length = ByteUtils.Byte2ToUInt(len);
			System.out.println("alg: "+alg+" len: "+length);
			byte[] name = new byte[length];
			output.get(name, 0, length);
			System.out.println(" name: ["+new String(name, utf8)+"]");
//			output = output.slice();

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
