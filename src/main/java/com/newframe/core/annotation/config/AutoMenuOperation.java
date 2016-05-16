package com.newframe.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoMenuOperation {

	/**
	 * 操作名称
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 操作码
	 * 
	 * @return
	 */
	public String code();

	/**
	 * 操作码类型
	 * 
	 * @return
	 */
	public MenuCodeType codeType() default MenuCodeType.TAG;

	/**
	 * 图标
	 * 
	 * @return
	 */
	public String icon() default "";

	/**
	 * 状态
	 * 
	 * @return
	 */
	public int status() default 0;
}
