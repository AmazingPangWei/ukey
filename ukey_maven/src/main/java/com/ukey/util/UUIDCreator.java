package com.ukey.util;

import java.util.UUID;

public class UUIDCreator
{
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}
}
