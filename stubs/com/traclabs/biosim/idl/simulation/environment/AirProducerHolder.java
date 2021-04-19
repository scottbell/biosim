package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "AirProducer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirProducer value;
	public AirProducerHolder()
	{
	}
	public AirProducerHolder (final AirProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirProducerHelper.write (_out,value);
	}
}
