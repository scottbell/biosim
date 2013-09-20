package com.traclabs.biosim.idl.simulation.water;
/**
 *	Generated from IDL definition of enum "WaterRSOperationMode"
 *	@author JacORB IDL compiler 
 */

public final class WaterRSOperationModeHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.water.WaterRSOperationModeHelper.id(),"WaterRSOperationMode",new String[]{"FULL","OFF","GREY_WATER_ONLY","PARTIAL"});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final com.traclabs.biosim.idl.simulation.water.WaterRSOperationMode s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static com.traclabs.biosim.idl.simulation.water.WaterRSOperationMode extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:com/traclabs/biosim/idl/simulation/water/WaterRSOperationMode:1.0";
	}
	public static WaterRSOperationMode read (final org.omg.CORBA.portable.InputStream in)
	{
		return WaterRSOperationMode.from_int(in.read_long());
	}

	public static void write (final org.omg.CORBA.portable.OutputStream out, final WaterRSOperationMode s)
	{
		out.write_long(s.value());
	}
}
