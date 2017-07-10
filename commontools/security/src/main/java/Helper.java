import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
	
	public static int getSize(String alg) {
		
		String au = alg.toUpperCase();
		if (au.startsWith("SM4") || au.startsWith("TEA") || au.startsWith("HC128") || au.startsWith("XTEA") || au.startsWith("SKIPJACK"))
			return 16;
		if (au.equals("CAST5")) return 8;
		if (au.equals("DES")) return 8;
		if (au.equals("DESEDE")) return 24;
		if (au.equals("THREEFISH-512")) return 64;
		if (au.equals("THREEFISH-1024")) return 128;
		if (alg.startsWith("PBEWithMD5AndTripleDES")) return 21;
		Matcher m = Pattern.compile("\\_(.*?)\\/").matcher(alg);
		while(m.find()) {
		    int x = asInt(m.group(1));
		    return x / 8;
		}
		if (alg.contains("AES")) return 16;
		return 32;
	}
	
	private static int asInt(String group) {
		try {
			return Integer.parseInt(group);	
		} catch (Exception e) {
			return 128;
		}
		
	}
	public static byte[] randomKey(int size) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}
	public static BigInteger bi() {
		return new BigInteger(randomKey(64));
	}
	
	public static void main(String[] args) {
		System.out.println(bi());

	}
	
	public final static void miniDelay() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {}
	}

}
