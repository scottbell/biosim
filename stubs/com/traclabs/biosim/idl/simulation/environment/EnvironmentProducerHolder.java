package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentProducer value;
	public EnvironmentProducerHolder()
	{
	}
	public EnvironmentProducerHolder (final EnvironmentProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentProducerHelper.write (_out,value);
	}
}
