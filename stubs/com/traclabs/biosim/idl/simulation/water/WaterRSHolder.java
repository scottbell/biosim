package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "WaterRS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterRSHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterRS value;
	public WaterRSHolder()
	{
	}
	public WaterRSHolder (final WaterRS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterRSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterRSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterRSHelper.write (_out,value);
	}
}
