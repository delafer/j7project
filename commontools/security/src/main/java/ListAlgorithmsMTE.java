
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import iaik.security.provider.IAIK;
import net.j7.commons.io.TextFileUtils;
import net.j7.commons.io.TextFileUtils.TextWriter;


public class ListAlgorithmsMTE {

	static SecureRandom rnd = new SecureRandom();

	static IvParameterSpec iv_16 = new IvParameterSpec(rnd.generateSeed(16));
	static IvParameterSpec iv_8 = new IvParameterSpec(rnd.generateSeed(8));
	static IvParameterSpec iv_12 = new IvParameterSpec(rnd.generateSeed(12));
	static IvParameterSpec iv_24 = new IvParameterSpec(rnd.generateSeed(24));


	static byte[] salt = rnd.generateSeed(8);
	static byte[] salt16 = rnd.generateSeed(16);
	static PBEParameterSpec iv_pbe = new PBEParameterSpec(salt, 100,  new IvParameterSpec(salt));
	static PBEParameterSpec iv_pbe_16 = new PBEParameterSpec(salt16, 100,  new IvParameterSpec(salt16));
	static GCMParameterSpec iv_gcm = new GCMParameterSpec(16 * 8, salt16);
	static {
		Security.addProvider(new BouncyCastleProvider());
		Security.addProvider(new IAIK());
		Security.setProperty("crypto.policy", "unlimited");

		//		Security.removeProvider("SunJSSE");
		//		java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		//		
		Provider p = Security.getProvider("SunPCSC");
		Security.removeProvider("SunPCSC");
		Security.insertProviderAt(p, 0); 
	}
	public static void printSet(String setName, Set algorithms) {
		System.out.println(setName + ":");
		if (algorithms.isEmpty()) {
			System.out.println("            None available.");
		} else {
			Iterator it = algorithms.iterator();
			while (it.hasNext()) {
				String name = (String) it.next();

				System.out.println("            " + name);
			}
		}
	}
	
	private static List<BenchEntry> benched = new ArrayList<BenchEntry>();

	public static void main(String[] args) {

		//
		//    printSet("Ciphers", ciphers);
		int x = 0;
		byte[] test = randomKey(1024 * 1024 * 5);
		int keySize = -1;
		Akey key = null;
		TextWriter w = null;
		try {
			w = TextFileUtils.createTextWriter("e:\\encbench.csv", "UTF-8", false);
			w.writeLn("Name;Time(ms)");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (int i = 1; i <= 2; i++) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.gc();System.gc();System.gc();



			for (Provider provider : Security.getProviders()) {
				System.out.println();
				System.out.println(" === Provider: " + provider.getName() + " === ");
				System.out.println();

				for (Provider.Service service : provider.getServices()) {
					if (!"Cipher".equals(service.getType())) continue;
					//            System.out.println("  Algorithm: " + service.getAlgorithm()+ " | "+service.getType());

					if (service.getAlgorithm().toUpperCase().startsWith("RSA") || service.getAlgorithm().toUpperCase().contains("WRAP")
							|| service.getAlgorithm().toUpperCase().contains("ELGAMAL")) continue;
					if (service.getAlgorithm().split("\\.").length>2) {
						System.out.println("SKIPPINGM "+service.getAlgorithm());
						continue;
					}
					try {

						String alg = service.getAlgorithm();

						key = doTest(test, w, i, provider, alg);
						x++;


					} catch (Throwable e) {
						System.out.println("ERROR: "+service.getAlgorithm()+ " ("+key+")");
						printStackTrace(e);
					} 
				}
			}

		}
		System.out.println("total: "+x);

		try {
			w.flush();
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collections.sort(benched);
		TextWriter wn, wv;
		try {
			wn = TextFileUtils.createTextWriter("e:\\encbench_names.csv", "UTF-8", false);
			wv = TextFileUtils.createTextWriter("e:\\encbench_values.csv", "UTF-8", false);
			
			
			for (BenchEntry next : benched) {
				System.out.println(next);
				wn.writeLn("'"+next.name+"',");
				wv.writeLn(next.mbs()+",");
			}
			
			wn.flush();wv.flush();
			wn.close();wv.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		

		//    printSet("KeyAgreeents", keyAgreements);
		//    printSet("Macs", macs);
		//    printSet("MessageDigests", messageDigests);
		//    printSet("Signatures", signatures);
	}

	private static Akey doTest(byte[] test, TextWriter w, int pass, Provider provider, String alg)
			throws Exception {
		int keySize;
		Akey key;
//		Cipher c = getDecr(provider, alg);
		
		int poolSize = 8;
	    ExecutorService service = Executors.newFixedThreadPool(poolSize);
	    Cipher[] c = new Cipher[poolSize];
		Cipher[] d = new Cipher[poolSize];
		for (int i = 0; i < poolSize; i++) {
			c[i] = getDecr(provider, alg);
			d[i] = getDecr(provider, alg);
		}
		
		keySize = Helper.getSize(alg);

		key = getKey(alg, provider.getName(), 0, randomKey(keySize));
		if (key == null) {
			//            		System.out.println("Can't generate key for: "+service.getAlgorithm());
			//            		continue;
		}
		if (key.pubKey != null) {
			for (int i = 0; i < poolSize; i++) {
				initCipher(key, c[i], d[i], alg);
			}
		}
		else
			if (key.cert != null) {
				
				for (int i = 0; i < poolSize; i++) {
					c[i].init(Cipher.ENCRYPT_MODE, key.cert);
					d[i].init(Cipher.DECRYPT_MODE, key.cert);
				}
			}

//		byte[] res = c.doFinal(test);


		long time = 0;

		for (int j = 0; j < 2; j++) {

			waitAlittle();
			long t1 = System.currentTimeMillis();
			
			List<Future<Runnable>> futures = new ArrayList<Future<Runnable>>();
			for (int n = 0; n < poolSize; n++)
		      {
				final int x = n;
				Helper.miniDelay();
		         Future f = service.submit(new Runnable() {
					
					@Override
					public void run() {
						try {
							byte[] decr = c[x].doFinal(test);
						} catch (IllegalBlockSizeException | BadPaddingException e) {
							e.printStackTrace();
						}
						
					}
				});
		         futures.add(f);
		         
		    }
			
			for (Future<Runnable> f : futures)
		      {
		         f.get();
		      }
			
			long t2 = System.currentTimeMillis();
			
			time += ((t2 - t1) - (11 * poolSize));

		}

		//shut down the executor service so that this thread can exit
	      service.shutdownNow();
	      
		double speed = (1000d / (double)time) * 80D;
		String algId = provider.getName()+":"+alg;
		System.out.println(String.format("SPE(%s): ",(pass))+floatFormat.format(speed)+" Mbs ("+algId+")");
			if ( pass == 2) {
				BenchEntry e = new BenchEntry(speed, algId);
				benched.add(e);
				w.writeLn(algId+";"+e.mbs());
				
			}
		return key;
	}

	private static Cipher getDecr(Provider provider, String alg)
			throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		return alg.contains("PKCS5Padding") ? Cipher.getInstance(alg) : Cipher.getInstance(alg, provider.getName());
	}

	private static final transient DecimalFormat floatFormat		= new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US));

	private static void waitAlittle() {
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();Runtime.getRuntime().gc();
		System.gc();System.gc();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
	}

	private static void initCipher(Akey key, Cipher c, Cipher d, String alg)
			throws InvalidKeyException, InvalidAlgorithmParameterException {

		String au = alg.toUpperCase();

		if (alg.startsWith("AES")) {

			if (alg.equals("AES") || alg.contains("/ECB/") /*|| alg.contains("/GCM/")*/) {
				c.init(Cipher.ENCRYPT_MODE, key.pubKey);
				d.init(Cipher.DECRYPT_MODE, key.privKey);
			}  else
				if (alg.contains("/GCM/")) {
					c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_gcm);
					d.init(Cipher.DECRYPT_MODE, key.privKey, iv_gcm);	
				}
				else {
					c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_16);
					d.init(Cipher.DECRYPT_MODE, key.privKey, iv_16);	
				}

		} else 
			if (alg.startsWith("DES") || alg.startsWith("Blowfish")) {
				c.init(Cipher.ENCRYPT_MODE, key.pubKey);
				d.init(Cipher.DECRYPT_MODE, key.privKey);	
			} else
				if (alg.startsWith("PBE")) {
					if (alg.contains("SHA") && alg.contains("AES")) {
						c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_pbe_16);
						d.init(Cipher.DECRYPT_MODE, key.privKey, iv_pbe_16);
					} else {
						c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_pbe);
						d.init(Cipher.DECRYPT_MODE, key.privKey, iv_pbe);	
					}


				}else
					if (au.equals("CHACHA") || au.equals("CHACHA20") || au.equals("GRAINV1") || au.equals("SALSA20")) {
						c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_8);
						d.init(Cipher.DECRYPT_MODE, key.privKey, iv_8);
					}else
						if (au.equals("CHACHA7539") || au.equals("GRAIN128") || au.equals("HC128")) {
							c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_12);
							d.init(Cipher.DECRYPT_MODE, key.privKey, iv_12);
						}
						else
							if (au.equals("XSALSA20")) {
								c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_24);
								d.init(Cipher.DECRYPT_MODE, key.privKey, iv_24);
							}
							else if (alg.toUpperCase().contains("SALSA") || alg.toUpperCase().startsWith("CHACHA")|| alg.toUpperCase().startsWith("GRAIN") || alg.toUpperCase().startsWith("HC256")
									|| alg.toUpperCase().startsWith("RIJNDAEL") || alg.toUpperCase().startsWith("VMPC") || alg.toUpperCase().equals("GCM")
									) {
								c.init(Cipher.ENCRYPT_MODE, key.pubKey, iv_16);
								d.init(Cipher.DECRYPT_MODE, key.privKey, iv_16);
							} else
							{
								c.init(Cipher.ENCRYPT_MODE, key.pubKey);
								d.init(Cipher.DECRYPT_MODE, key.privKey);
							}


	}


	private static Akey getKey(String alg, String name, int iter, byte[] keys) {

		if (iter == 4) {
			//special key
			SecretKeySpec sks = new SecretKeySpec(keys, alg);
			return new  Akey(sks, -1);
		};

		if (iter == 0) {
			int idx = alg.indexOf("/");
			if (idx != -1) alg =  alg.substring(0, idx);	
		}

		if (iter == 2) {
			int idx = alg.indexOf("_");
			if (idx == -1) return getKey(alg, name, ++iter, keys); 
			alg =  alg.substring(0, idx);

		}
		
		if (alg.equalsIgnoreCase("IES")) {
			//return new Akey(new BCDHPublicKey(Helper.bi(), new DHParameterSpec(Helper.bi(), Helper.bi())), new DHPrivateKey(new DHPrivateKeySpec(Helper.bi(), Helper.bi(), Helper.bi())), -1);
		}

		try {
			SecretKeyFactory f = iter % 2 == 1 ? SecretKeyFactory.getInstance(alg) : SecretKeyFactory.getInstance(alg, name);
			if (null != f) {
				KeySpec ks = null;

				if (alg.toUpperCase().startsWith("PBE")) {
					ks = new PBEKeySpec(asChars(keys));
				} else {
					ks = new SecretKeySpec(keys, alg);
				}

				SecretKey sc = f.generateSecret(ks);
				return new Akey(sc, 1);	
			}
		} catch (Throwable e) {
			//		System.out.println("::"+alg);
			printStackTrace(e);
		}

		try {
			KeyPairGenerator f = iter % 2 == 1 ? KeyPairGenerator.getInstance(alg) : KeyPairGenerator.getInstance(alg, name);
			if (null != f) {
				KeyPair kp  = f.generateKeyPair();
				return new Akey(kp.getPublic(), kp.getPrivate(), 2);	
			}
		} catch (Throwable e) {
			printStackTrace(e);
		}

		try {
			KeyFactory f = iter % 2 == 1 ? KeyFactory.getInstance(alg) : KeyFactory.getInstance(alg, name);
			if (null != f) {
				return new Akey(f.generatePublic(new SecretKeySpec(keys, "AES")), f.generatePrivate(new SecretKeySpec(keys, "AES")), 3);
			}
		} catch (Throwable e) {
			printStackTrace(e);
		}



		try {
			KeyGenerator f = iter % 2 == 1 ? KeyGenerator.getInstance(alg) : KeyGenerator.getInstance(alg, name);
			if (null != f) {
				f.init(keys.length * 8);
				return new Akey(f.generateKey(), 4);
			}
		} catch (Throwable e) {
			printStackTrace(e);
		}

		try {
			CertificateFactory f = iter % 2 == 1 ? CertificateFactory.getInstance(alg) : CertificateFactory.getInstance(alg, name);
			if (null != f) {
				Certificate c = f.generateCertificate(new ByteArrayInputStream(keys));
				return new Akey(c, 5);
			}
		} catch (Throwable e) {}

		return getKey(alg, name, ++iter, keys); 
	}

	private static char[] asChars(byte[] keys) {
		char[] ch = new char[keys.length];
		for (int i = 0; i < keys.length; i++) {
			ch[i] = (char)((byte)'a' + (keys[i] % 28));
		}
		return ch;
	}

	private static void printStackTrace(Throwable e) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			e.printStackTrace(ps);

			String output = os.toString("UTF8");
			if (output.contains(" not available") || output.contains("no such algorithm")) return ;
			e.printStackTrace();
		} catch (Exception e1) {}

	}

	public static byte[] randomKey(int size) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}

	private static void process(Cipher cipher, ByteBuffer inBuffer, ByteBuffer outBuffer)
			throws IOException {
		try {
			int inputSize = inBuffer.remaining();
			// OpensslCipher#update will maintain crypto context.
			int n = cipher.update(inBuffer, outBuffer);
			if (n < inputSize) {
				/**
				 * Typically code will not get here. OpensslCipher#update will 
				 * consume all input data and put result in outBuffer. 
				 * OpensslCipher#doFinal will reset the crypto context.
				 */
				cipher.doFinal(inBuffer, outBuffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}

	public static class Akey {
		@Override
		public String toString() {
			return String.format("Akey %s [%s]", pubKey.getEncoded().length, n);
		}

		java.security.Key pubKey; //encode
		java.security.Key privKey; //decode
		java.security.cert.Certificate cert;
		int n;
		public Akey(Key pubKey, int n) {
			this.pubKey = pubKey;
			this.privKey = pubKey;
			this.n = n;
		}

		public Akey(Certificate cert, int n) {
			this.cert = cert;
			this.n = n;
		}

		public Akey(Key pubKey, Key privKey, int n) {
			this.pubKey = pubKey;
			this.privKey = privKey;
			this.n = n;
		}
	}

	public static class BenchEntry implements Comparable<BenchEntry> {
		double mbs;
		String name;

		public BenchEntry(double mbs, String name) {
			this.mbs = mbs;
			this.name = name;
		}
		
		public String mbs() {
			return floatFormat.format(mbs);
		}

		@Override
		public String toString() {
			return String.format("BenchEntry [%s : %s mbs]", name, mbs());
		}

		public int compareTo(BenchEntry arg0) {

			return Double.compare(arg0.mbs, mbs);
		}
	}
}