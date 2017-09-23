package vn.com.daisy.general;

import java.util.Date;
import java.util.Random;

public class General {
	public static int createKeyId() {
		int key;
		int range = 100000;
		Random rand = new Random();
		key = range + rand.nextInt(999999);
		return key;
	}

	public static Date getCurrentDate() {
		Date date = new Date();
		return date;
	}
}
