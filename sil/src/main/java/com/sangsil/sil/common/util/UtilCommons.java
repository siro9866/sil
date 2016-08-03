package com.sangsil.sil.common.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UtilCommons {

	/**
	 * 랜덤 문자 생성(파일명 등에 사용)
	 * 
	 * @return
	 */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		if ((o instanceof String) && (((String) o).trim().length() == 0)) {
			return true;
		}
		if (o instanceof Map) {
			return ((Map<?, ?>) o).isEmpty();
		}
		if (o instanceof List) {
			return ((List<?>) o).isEmpty();
		}
		if (o instanceof Object[]) {
			return (((Object[]) o).length == 0);
		}
		return false;
	}

}
