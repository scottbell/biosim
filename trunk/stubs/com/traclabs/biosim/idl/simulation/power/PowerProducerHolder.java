package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerProducer value;
	public PowerProducerHolder()
	{
	}
	public PowerProducerHolder (final PowerProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerProducerHelper.write (_out,value);
	}
}
