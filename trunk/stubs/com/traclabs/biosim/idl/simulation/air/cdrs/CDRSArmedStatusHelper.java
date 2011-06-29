package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSArmedStatus"
 *	@author JacORB IDL compiler 
 */

public final class CDRSArmedStatusHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatusHelper.id(),"CDRSArmedStatus",new String[]{"armed","in_progress","not_armed"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/air/cdrs/CDRSArmedStatus:1.0";
	}
	public static CDRSArmedStatus read (final org.omg.CORBA.portable.InputStream in)
	{
		return CDRSArmedStatus.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final CDRSArmedStatus s)
	{
		out.write_long(s.value());
	}
}
