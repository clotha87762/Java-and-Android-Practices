package netdb.courses.softwarestudio.simple_rest_server.service.md5;

import java.security.MessageDigest;

public class MD5Encoder {
	
	/**
	 * Encrypt the target string by using MD5 Hash.
	 */
	public static String encrypt(String str) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			result = toHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String toHexString(byte[] in) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < in.length; i++) {
			String hex = Integer.toHexString(0xFF & in[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
