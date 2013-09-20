package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "LogLevel"
 *	@author JacORB IDL compiler 
 */

public final class LogLevelHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.framework.LogLevelHelper.id(),"LogLevel",new String[]{"OFF","INFO","DEBUG","WARN","ERROR","FATAL","ALL"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.framework.LogLevel s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.framework.LogLevel extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/framework/LogLevel:1.0";
	}
	public static LogLevel read (final org.omg.CORBA.portable.InputStream in)
	{
		return LogLevel.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final LogLevel s)
	{
		out.write_long(s.value());
	}
}
