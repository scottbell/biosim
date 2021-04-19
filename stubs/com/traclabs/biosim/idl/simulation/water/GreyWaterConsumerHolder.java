package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "GreyWaterConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GreyWaterConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterConsumer value;
	public GreyWaterConsumerHolder()
	{
	}
	public GreyWaterConsumerHolder (final GreyWaterConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterConsumerHelper.write (_out,value);
	}
}
