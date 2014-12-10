package com.example.hzqweather.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class Utility {

	public static boolean citycodeExist() {
		String PACKAGE_NAME = "com.example.HZQweather";
		String dbPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/citycode.db";
		return new File(dbPath).exists();
	}
	
	public static void importDB(Context context){
		try {
			
			String PACKAGE_NAME = "com.example.hzqweather";
			String dbPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/citycode.db";
			System.out.println(dbPath);
			int BUFFER_SIZE = 200000;
			InputStream is = context.getResources().getAssets().open("citycode.db"); 
			File dbFile = new File("/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases");
			if (!dbFile.exists()) {
				dbFile.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(dbPath);
			byte[] buffer = new byte[BUFFER_SIZE];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
			    fos.write(buffer, 0, count);
			}              
			fos.close();//关闭输出流
			is.close();//关闭输入流
			
			Runtime.getRuntime().exec("chmod 777 " + dbPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
