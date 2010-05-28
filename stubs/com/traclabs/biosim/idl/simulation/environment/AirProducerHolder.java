package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "AirProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
