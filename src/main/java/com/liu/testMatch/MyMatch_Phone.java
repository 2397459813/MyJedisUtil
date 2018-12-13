package com.liu.testMatch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMatch_Phone {
	public static void main(String[] args) {
		String result = getTelnum("ºº×Ó13101714376fggggggggggfjjf455ºº×Ó663431357689054318976ºº×Ó543256786785656751113234443243");
		System.out.println(result);
	}
	
	public static String getTelnum(String sParam){

		if(sParam.length()<=0)
		return "";
		Pattern pattern = Pattern.compile("(1|861)(3|5|8)\\d{9}$*");
		Matcher matcher = pattern.matcher(sParam);
		StringBuffer bf = new StringBuffer();
		while (matcher.find()) {
		bf.append(matcher.group()).append(",");
		}
		int len = bf.length();
		if (len > 0) {
		bf.deleteCharAt(len - 1);
		}
		return bf.toString();
	}

}
