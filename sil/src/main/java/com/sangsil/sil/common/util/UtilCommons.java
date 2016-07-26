package com.sangsil.sil.common.util;

import java.util.UUID;

public class UtilCommons {

	public static String getRandomString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	
}
