package com.traclabs.biosim.idl.framework;
/**
 * Generated from IDL enum "LogLevel".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class LogLevelHolder
	implements org.omg.CORBA.portable.Streamable
{
	public LogLevel value;

	public LogLevelHolder ()
	{
	}
	public LogLevelHolder (final LogLevel initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return LogLevelHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = LogLevelHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		LogLevelHelper.write (out,value);
	}
}
