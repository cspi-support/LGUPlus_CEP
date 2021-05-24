package com.lgu.seamless.util;


import com.streambase.net.minidev.json.JSONObject;
import com.streambase.net.minidev.json.parser.JSONParser;
import com.streambase.net.minidev.json.parser.ParseException;

public class JsonUtils {
	
	/* Description
	 *  function : addElementToJsonString
	 *  jsonString JsonString 필드 중, 인자로 받은  Element로 변경.
	 *  jString : jsonString 문자열 
	 *  el : String으로 이뤄진 json element (eg : '{"CUCT_DTTM":ISODate("'+"2021-03-03T14:22:12Z"+'")}')
	 *  fName : ISODate 타입으로 변경 해야할 필드명
	 *  return : retJsonString
	 * */
	public static String addElementToJsonString(String jString, String el, String fName) throws ParseException {
		
		JSONParser parser = new JSONParser();
		
		// Remove the fName field if fName is in JSONObject. 
		JSONObject jObj =  (JSONObject)parser.parse(jString);
		
		System.out.println("jObjtoJSONString   : " + jObj.toJSONString());
		
		if(jObj.containsKey(fName)) {
			jObj.remove(fName);
			jString = jObj.toJSONString();
		}
		
		System.out.println("before JsonString :: "+jString);
		
		// Adding element to JsonString.
		int lastIdx = jString.split("}").length;
		String header = jString.split("}")[lastIdx-1];
		String retJsonString = header + "," + el + "}";
		
		System.out.println("after JsonString :: "+retJsonString);
		
		return retJsonString;
	}

//	public static void main(String args[]) {
//		try {
//			new JsonUtils().addElementToJsonString("", "2012", "CUCT_DTTM");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
