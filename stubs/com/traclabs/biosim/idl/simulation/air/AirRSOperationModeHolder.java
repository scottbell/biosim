package com.traclabs.biosim.idl.simulation.air;
/**
 * Generated from IDL enum "AirRSOperationMode".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirRSOperationModeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public AirRSOperationMode value;

	public AirRSOperationModeHolder ()
	{
	}
	public AirRSOperationModeHolder (final AirRSOperationMode initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return AirRSOperationModeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirRSOperationModeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		AirRSOperationModeHelper.write (out,value);
	}
}
