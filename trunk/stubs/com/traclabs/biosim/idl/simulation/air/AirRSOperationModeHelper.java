package com.traclabs.biosim.idl.simulation.air;
/**
 *	Generated from IDL definition of enum "AirRSOperationMode"
 *	@author JacORB IDL compiler 
 */

public final class AirRSOperationModeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.air.AirRSOperationModeHelper.id(),"AirRSOperationMode",new String[]{"FULL","OFF","LESS","MOST"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.air.AirRSOperationMode s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.air.AirRSOperationMode extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/air/AirRSOperationMode:1.0";
	}
	public static AirRSOperationMode read (final org.omg.CORBA.portable.InputStream in)
	{
		return AirRSOperationMode.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final AirRSOperationMode s)
	{
		out.write_long(s.value());
	}
}
