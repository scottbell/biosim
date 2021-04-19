package com.traclabs.biosim.idl.simulation.power;

/**
 * Generated from IDL interface "PowerPS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PowerPSHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerPS value;
	public PowerPSHolder()
	{
	}
	public PowerPSHolder (final PowerPS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerPSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerPSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerPSHelper.write (_out,value);
	}
}
