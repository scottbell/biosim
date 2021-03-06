package com.traclabs.biosim.idl.simulation.power;

/**
 * Generated from IDL interface "GenericPowerConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GenericPowerConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public GenericPowerConsumer value;
	public GenericPowerConsumerHolder()
	{
	}
	public GenericPowerConsumerHolder (final GenericPowerConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GenericPowerConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GenericPowerConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GenericPowerConsumerHelper.write (_out,value);
	}
}
