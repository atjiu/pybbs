package com.jfinalbbs.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class AgentUtil {

	public static String IPAD = "ipad";
	public static String IPHONE = "iphone";
	public static String ANDROID = "android";
	public static String WEB = "web";

	/**
	 * ��������UA
	 * 
	 * @return
	 */
	public static String getAgent(HttpServletRequest request) {
		String agent = request.getHeader("USER-AGENT");
		if (agent == null || agent.equals("")) {
			return WEB;
		} else {
            agent=agent.toLowerCase();
			if (agent.indexOf(IPHONE) > 0) {
				return IPHONE;
			} else if (agent.indexOf(IPAD) > 0) {
				return IPAD;
			} else if (agent.indexOf(ANDROID) > 0) {
				return ANDROID;
			}
		}
		return WEB;
	}
}
