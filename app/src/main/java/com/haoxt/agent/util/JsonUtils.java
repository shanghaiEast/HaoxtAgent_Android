package com.haoxt.agent.util;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
	
	public static String Array2Json(ArrayList<HashMap<String, Object>> ls){
		Gson gson = new Gson();
		return gson.toJson(ls);
	}
	
	public static String MapObj2Json(Map<String, Object> map){
		Gson gson = new Gson();
		return gson.toJson(map);
	}

	public static String LinkedTreeMap2Json(ArrayList<LinkedTreeMap<String, Object>> map){
		Gson gson = new Gson();
		return gson.toJson(map);
	}


	public static String listBTojson(List<String> list) {

		StringBuffer json = new StringBuffer();
		json.append("{'BinB':[");
		if (list.size() > 0 && list != null) {
			for (int i = 0; i < list.size() - 1; i += 2) {
				json.append("{'" + list.get(i + 1) + "':'" + list.get(i) + "'}");
				json.append(",");
			}
			 json.setCharAt(json.length() - 1, ']');
		}
		json.append("}");

		return json.toString();
	}
	
	public static String listCTojson(List<String> list) {

		StringBuffer json = new StringBuffer();
		json.append("{'BinC':[");
		if (list.size() > 0 && list != null) {
			for (int i = 0; i < list.size() - 1; i += 2) {
				json.append("{'" + list.get(i + 1) + "':'" + list.get(i) + "'}");
				json.append(",");
			}
			 json.setCharAt(json.length() - 1, ']');
		}
		json.append("}");

		return json.toString();
	}
	
}
