package com.lgu.seamless;

import org.bson.Document;

public class BsonDateTest {

	
	public static void main(String args[]) {
		String dt = "{	\"$min\":{\"CUCT_DTTM_MIN\": new ISODate(\"2021-03-25\")}";
//		String dt = "{	\"$min\":{\"CUCT_DTTM_MIN\": new ISODate(\"2021-03-25 18:54:04\", \"YYYY-mm-DDTHH:MM:ss\")}";
//		String dt = "{	\"$min\":{\"CUCT_DTTM_MIN\": new JRaw(@\"new ISODate(\"\"2021-03-25 18:54:04\"\"))}";
		System.out.println("dt :: "+dt);
		final Document selector = Document.parse(dt);
	}
}
