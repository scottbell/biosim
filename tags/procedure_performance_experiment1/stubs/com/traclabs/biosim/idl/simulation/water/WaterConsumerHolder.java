package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "WaterConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterConsumer value;
	public WaterConsumerHolder()
	{
	}
	public WaterConsumerHolder (final WaterConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterConsumerHelper.write (_out,value);
	}
}
