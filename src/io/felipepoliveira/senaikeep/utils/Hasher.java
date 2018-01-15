package io.felipepoliveira.senaikeep.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
	public static String hashToMd5(String msg) {
		MessageDigest md5Digester = getMessageDigester(SupportedMessageDigesters.MD5); 
		byte[] msgBytes;
		try {
			msgBytes = md5Digester.digest(msg.getBytes("UTF-8"));
			
			return new String(msgBytes, Charset.defaultCharset());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("An fatal error occurred while digesting string");
		}
		
		
	}
	
	public static MessageDigest getMessageDigester(SupportedMessageDigesters smd) {
		try {
			switch (smd) {

			case MD5:
				return MessageDigest.getInstance("MD5");
			default:
				throw new InvalidParameterException("You must specify a valid SupportedMessageDigester. " + smd + " given instead.");
			}
		} catch (NoSuchAlgorithmException e) {}
		return null;
	}
	
	public enum SupportedMessageDigesters{
		MD5
	}
}
