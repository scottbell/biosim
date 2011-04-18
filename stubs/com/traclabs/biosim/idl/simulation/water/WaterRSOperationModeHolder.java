package com.traclabs.biosim.idl.simulation.water;
/**
 *	Generated from IDL definition of enum "WaterRSOperationMode"
 *	@author JacORB IDL compiler 
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
