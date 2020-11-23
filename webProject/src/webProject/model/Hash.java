package webProject.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hash {

	public Hash() {
		// TODO Auto-generated constructor stub
	}

	// https://www.baeldung.com/sha-256-hashing-java
	public static String makeHash(String password) {
		byte[] encodedhash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			System.out.println("Hashing Failed: " + e);
		}
		return bytesToHex(encodedhash);
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
}
