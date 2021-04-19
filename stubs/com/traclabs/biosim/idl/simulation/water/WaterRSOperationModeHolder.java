package com.traclabs.biosim.idl.simulation.water;
/**
 * Generated from IDL enum "WaterRSOperationMode".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterRSOperationModeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public WaterRSOperationMode value;

	public WaterRSOperationModeHolder ()
	{
	}
	public WaterRSOperationModeHolder (final WaterRSOperationMode initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return WaterRSOperationModeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterRSOperationModeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		WaterRSOperationModeHelper.write (out,value);
	}
}
