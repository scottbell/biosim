package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "PotableWaterConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PotableWaterConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterConsumer value;
	public PotableWaterConsumerHolder()
	{
	}
	public PotableWaterConsumerHolder (final PotableWaterConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterConsumerHelper.write (_out,value);
	}
}
