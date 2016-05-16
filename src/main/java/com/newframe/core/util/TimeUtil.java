package com.newframe.core.util;

import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {
	private static final TimeZone defaultTimeZone = TimeZone.getDefault();
	
	public static Date fromDatabaseToUTC(Date date){
		int offset = defaultTimeZone.getOffset(date.getTime());
		long epoch = date.getTime()/* + offset*/;
		return new Date(epoch);
	}
	
	public static Date fromUTCToDatabase(Date date){
		int offset = defaultTimeZone.getOffset(date.getTime());
		long epoch = date.getTime()/* - offset*/;
		return new Date(epoch);
	}
}
