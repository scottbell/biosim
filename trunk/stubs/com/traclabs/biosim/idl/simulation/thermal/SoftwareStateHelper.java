package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "SoftwareState"
 *	@author JacORB IDL compiler 
 */

public final class SoftwareStateHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.thermal.SoftwareStateHelper.id(),"SoftwareState",new String[]{"shutdown","softwareArmed","running"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.thermal.SoftwareState s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.thermal.SoftwareState extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/thermal/SoftwareState:1.0";
	}
	public static SoftwareState read (final org.omg.CORBA.portable.InputStream in)
	{
		return SoftwareState.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final SoftwareState s)
	{
		out.write_long(s.value());
	}
}
