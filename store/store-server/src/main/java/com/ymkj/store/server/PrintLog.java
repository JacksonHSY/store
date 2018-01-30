package com.ymkj.store.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志打印相关
 * 
 * @author longjw@yuminsoft.com
 *
 */
@Slf4j
public class PrintLog {

	/**
	 * 打印操作失败日志
	 * 
	 * @param model 操作模块
	 * @param inputStream 操作返回输入流
	 */
	public static void printErrMsg(String model, InputStream inputStream){
		if (inputStream != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				String s = "";
				while ((s = reader.readLine()) != null) {
					log.error("[" + model + "]：" + s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
