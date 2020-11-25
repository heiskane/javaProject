package webProject.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Hash {
	
	public Hash() {
		// TODO Auto-generated constructor stub
	}

	// https://www.baeldung.com/sha-256-hashing-java
	// https://en.wikipedia.org/wiki/Kerckhoffs%27s_principle
	// Create SHA512 Passwords with dynamic hashes
	public static String[] makeHash(String salt, String password) {
		if (salt == null)
		{
			// null is given when making a new user so generate a random salt
			salt = genSalt();
		}
		password = salt + password;
		byte[] encodedhash = null;
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		}
		catch (Exception e)
		{
			System.out.println("Hashing Failed: " + e);
		}
		String hash = bytesToHex(encodedhash);
		return new String[] {salt, hash};
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++)
	    {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1)
	        {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	// https://crackstation.net/hashing-security.htm
	// https://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html
	private static String genSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return bytesToHex(bytes);
	}
	
}
