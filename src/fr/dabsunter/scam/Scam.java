package fr.dabsunter.scam;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by David on 23/07/2016.
 */
public class Scam {

	private static final char[] DIGITS =
			{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static void check(String productId) {
		final Gson gson = new Gson();
		final String id = digestMD5(productId);
		Product[] products = new Product[0];
		try {
			InputStream in = new URL("http://dabsunter.github.io/products.json").openStream();
			products = gson.fromJson(new InputStreamReader(in), Product[].class);
			in.close();
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			System.err.println(ex.toString());
		}

		for (Product p : products)
			if (p.id.equals(id) && p.active)
				return;

		System.err.println("Le produit n'a pas été validé.");
		System.exit(0);
	}

	public static String digestMD5(String message) {
		try {
			final MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(message.getBytes("UTF-8"));
			final byte[] data = md5.digest();
			final int l = data.length;
			final char[] out = new char[l << 1];
			// two characters form the hex value.
			for (int i = 0, j = 0; i < l; i++) {
				out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
				out[j++] = DIGITS[0x0F & data[i]];
			}
			return new String(out);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private class Product {

		public String id;
		public boolean active;

	}

}
