package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "MalfunctionLength"
 *	@author JacORB IDL compiler 
 */

public final class MalfunctionLengthHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.framework.MalfunctionLengthHelper.id(),"MalfunctionLength",new String[]{"TEMPORARY_MALF","PERMANENT_MALF"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.framework.MalfunctionLength s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.framework.MalfunctionLength extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/framework/MalfunctionLength:1.0";
	}
	public static MalfunctionLength read (final org.omg.CORBA.portable.InputStream in)
	{
		return MalfunctionLength.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final MalfunctionLength s)
	{
		out.write_long(s.value());
	}
}
