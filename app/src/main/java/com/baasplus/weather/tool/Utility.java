package com.baasplus.weather.tool;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utility {

	/**
	 * 查询databases路径下是否存在citycode.db数据库文件
	 * @return 是否存在
	 */
	public static boolean citycodeExist() {
		String PACKAGE_NAME = "com.example.HZQweather";
		String dbPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME + "/databases/citycode.db";
		return new File(dbPath).exists();
	}
	
	/**
	 * 从assets文件夹下读取数据库文件，然后copy到databases文件夹下
	 * @param context
	 */
	public static void importDB(Context context){
		try {
			
			String PACKAGE_NAME = "com.baasplus.weather";
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
	
	/**
	 * 规范日期
	 * @param timeL
	 * @return
	 */
	public static String DateFormater(long timeL){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);
		Date d = new Date(timeL);
		String date = sdf.format(d);
		return date;
	}
}
