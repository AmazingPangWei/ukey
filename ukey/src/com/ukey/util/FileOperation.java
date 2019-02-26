package com.ukey.util;

import java.io.File;

public class FileOperation
{
	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
