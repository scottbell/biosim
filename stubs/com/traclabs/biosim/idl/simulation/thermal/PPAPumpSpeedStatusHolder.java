package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "PPAPumpSpeedStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PPAPumpSpeedStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public PPAPumpSpeedStatus value;

	public PPAPumpSpeedStatusHolder ()
	{
	}
	public PPAPumpSpeedStatusHolder (final PPAPumpSpeedStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PPAPumpSpeedStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PPAPumpSpeedStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PPAPumpSpeedStatusHelper.write (out,value);
	}
}
