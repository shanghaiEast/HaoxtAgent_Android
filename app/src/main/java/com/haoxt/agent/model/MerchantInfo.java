package com.haoxt.agent.model;

import java.util.HashMap;

public class MerchantInfo {

	private static volatile MerchantInfo INSTANCE;

	private HashMap<String, Object> infoMap = new HashMap<String, Object>();

	private MerchantInfo() {
	}

	public static MerchantInfo getInstance() {
		if (INSTANCE == null) {
			synchronized (MerchantInfo.class) {
				if (INSTANCE == null) {
					INSTANCE = new MerchantInfo ();
				}
			}
		}
		return INSTANCE;
	}

	public HashMap<String, Object> getInfoMap() {
		return infoMap;
	}

}
