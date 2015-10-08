package com.fcr.demo.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author wangfang 创建日期：2012-3-12 类名：DES.java 备注：
 */
public class Des {

	// private String key = "8e2bf219";
	private byte[] desKey;

	public Des(String key) {
		this.desKey = key.getBytes();
	}

	// 8e2bf219
	public final static String jmdk1 = "8";
	public final static String jmdk2 = "e";
	public final static String jmdk3 = "2";
	public final static String jmdk4 = "b";
	public final static String jmdk5 = "f";
	public final static String jmdk6 = "2";
	public final static String jmdk7 = "1";
	public final static String jmdk8 = "9";

	public static String getDefaultKey() {
		String strKey = jmdk1 + jmdk2 + jmdk3 + jmdk4 + jmdk5 + jmdk6 + jmdk7
				+ jmdk8;
		return strKey;
	}

	public byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	public byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * 加密方法
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	/**
	 * 解密方法
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 * 
	 */
	public String decrypt(String input) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result));
	}

	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		// BASE64Encoder b = new sun.misc.BASE64Encoder();
		// return b.encode(s);
		return Base64Encoder.encode(s);
	}

	public static byte[] base64Decode(String s) throws Exception {
		if (s == null)
			return null;
		byte[] b = Base64Encoder.decode(s);
		return b;
	}
}
