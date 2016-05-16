package com.newframe.core.util;

import com.newframe.core.exception.BusinessException;

public class ExceptionUtils {

	private ExceptionUtils(){
		//no instance
	}
	
	
	/**
	 * 如果目标为空则抛出异常
	 * @param target
	 * @param errorMessage
	 */
	public static void throwIfNull(Object target,String errorMessage){
		if(target==null){
			throw new BusinessException(errorMessage);
		}
	}
	
	/**
	 * 如果目标为空则抛出异常
	 * 本方法空指针安全
	 * @param target
	 * @param errorMessage
	 */
	public static void throwIfEmpty(String target,String errorMessage)
	{
		if(target==null || target.equals("")){
			throw new BusinessException(errorMessage);
		}
	}
	
}
