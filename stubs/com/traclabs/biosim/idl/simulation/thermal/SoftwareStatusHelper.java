package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "SoftwareStatus"
 *	@author JacORB IDL compiler 
 */

public final class SoftwareStatusHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.thermal.SoftwareStatusHelper.id(),"SoftwareStatus",new String[]{"shutdown","softwareArmed","running"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.thermal.SoftwareStatus s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.thermal.SoftwareStatus extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/thermal/SoftwareStatus:1.0";
	}
	public static SoftwareStatus read (final org.omg.CORBA.portable.InputStream in)
	{
		return SoftwareStatus.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final SoftwareStatus s)
	{
		out.write_long(s.value());
	}
}
