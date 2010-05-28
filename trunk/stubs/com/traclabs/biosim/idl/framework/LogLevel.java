package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "LogLevel"
 *	@author JacORB IDL compiler 
 */

public final class LogLevel
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _OFF = 0;
	public static final LogLevel OFF = new LogLevel(_OFF);
	public static final int _INFO = 1;
	public static final LogLevel INFO = new LogLevel(_INFO);
	public static final int _DEBUG = 2;
	public static final LogLevel DEBUG = new LogLevel(_DEBUG);
	public static final int _WARN = 3;
	public static final LogLevel WARN = new LogLevel(_WARN);
	public static final int _ERROR = 4;
	public static final LogLevel ERROR = new LogLevel(_ERROR);
	public static final int _FATAL = 5;
	public static final LogLevel FATAL = new LogLevel(_FATAL);
	public static final int _ALL = 6;
	public static final LogLevel ALL = new LogLevel(_ALL);
	public int value()
	{
		return value;
	}
	public static LogLevel from_int(int value)
	{
		switch (value) {
			case _OFF: return OFF;
			case _INFO: return INFO;
			case _DEBUG: return DEBUG;
			case _WARN: return WARN;
			case _ERROR: return ERROR;
			case _FATAL: return FATAL;
			case _ALL: return ALL;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected LogLevel(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
