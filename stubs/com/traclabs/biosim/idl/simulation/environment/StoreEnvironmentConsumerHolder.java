package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "StoreEnvironmentConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreEnvironmentConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreEnvironmentConsumer value;
	public StoreEnvironmentConsumerHolder()
	{
	}
	public StoreEnvironmentConsumerHolder (final StoreEnvironmentConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreEnvironmentConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreEnvironmentConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreEnvironmentConsumerHelper.write (_out,value);
	}
}
