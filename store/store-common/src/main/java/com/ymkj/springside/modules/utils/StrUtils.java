package com.ymkj.springside.modules.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {

	public static boolean isNotBlank(String s) {
		return (s != null) && (!"".equals(s)) && (!"undefine".equals(s))
				&& (!"null".equals(s));
	}
	
	public static boolean ObjisNotBlank(Object obj) {
		Boolean flag = true;
		if (obj == null) {
			flag = false;
		}
		return flag;
	}
	public static boolean ObjisBlank(Object obj) {
		Boolean flag = false;
		if (obj == null) {
			flag = true;
		}
		return flag;
	}

	public static String ArrayToStr(String[] array) {
		String str = "";
		if ((array != null) && (array.length > 0)) {
			for (int i = 0; i < array.length; i++) {
				str = str + array[i] + ",";
			}
			return str.substring(0, str.length() - 1);
		}
		return null;
	}

	public static String[] StringToArray(String str) {
		if (isNotBlank(str)) {
			if (str.indexOf(",") != -1)
				return str.replaceAll("\\s*", "").split("[,]");
			return new String[] { str };
		}
		return null;
	}
	
	public static List<String> StrToList(String str) {
		String[] arr = StringToArray(str);
		if(null != arr){
			return java.util.Arrays.asList(arr);
		}
		return null;
	}
	
	public static boolean isEmail(String email) {
		if (!isNotBlank(email))
			return false;
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(check);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isPhone(String phone) {
		String check = "^([0-9]+[-]?)+[[0-9]]$";
		Pattern p = Pattern.compile(check);
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	public static boolean isMobile(String mobile) {
		if (!isNotBlank(mobile))
			return false;
		String regStr = "^[1][0-9]{10}$";
		Pattern p = Pattern.compile(regStr);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	 public static boolean isEmpty(String str) {
	        return str == null || str.length() == 0;
	    }
	
	 public static boolean isNotEmpty(String str) {
	        return !StrUtils.isEmpty(str);
	    }

	/**
	 * @author      fanqp 
	 * @createTime  2017年4月17日 下午2:43:33
	 * @description 计算字符出现的次数
	 */
	public static int calMatchTime(String parameter,String allChar){
		int count = 0;
		int start = 0;
		while (allChar.indexOf(parameter, start) >= 0 && start < allChar.length()) {
			count++;
			start = allChar.indexOf(parameter, start) + parameter.length();
		}
		return count;
	}
	
	public static void main(String[] args) {
		System.out.println(StrUtils.calMatchTime("\\", "\\总部"));
	}
}