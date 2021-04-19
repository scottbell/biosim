package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "AirConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirConsumer value;
	public AirConsumerHolder()
	{
	}
	public AirConsumerHolder (final AirConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirConsumerHelper.write (_out,value);
	}
}
