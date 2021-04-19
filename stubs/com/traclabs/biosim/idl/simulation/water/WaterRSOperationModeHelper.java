package com.traclabs.biosim.idl.simulation.water;
/**
 * Generated from IDL enum "WaterRSOperationMode".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public abstract class WaterRSOperationModeHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(WaterRSOperationModeHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_enum_tc(com.traclabs.biosim.idl.simulation.water.WaterRSOperationModeHelper.id(),"WaterRSOperationMode",new String[]{"FULL","OFF","GREY_WATER_ONLY","PARTIAL"});
				}
			}
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
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
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
