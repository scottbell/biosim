package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "StoreEnvironmentProducer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreEnvironmentProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreEnvironmentProducer value;
	public StoreEnvironmentProducerHolder()
	{
	}
	public StoreEnvironmentProducerHolder (final StoreEnvironmentProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreEnvironmentProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreEnvironmentProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreEnvironmentProducerHelper.write (_out,value);
	}
}
