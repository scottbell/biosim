package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "Sex"
 *	@author JacORB IDL compiler 
 */

public final class SexHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.crew.SexHelper.id(),"Sex",new String[]{"male","female"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.crew.Sex s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.crew.Sex extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/crew/Sex:1.0";
	}
	public static Sex read (final org.omg.CORBA.portable.InputStream in)
	{
		return Sex.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final Sex s)
	{
		out.write_long(s.value());
	}
}
