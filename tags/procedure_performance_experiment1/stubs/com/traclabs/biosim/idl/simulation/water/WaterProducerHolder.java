package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "WaterProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterProducer value;
	public WaterProducerHolder()
	{
	}
	public WaterProducerHolder (final WaterProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterProducerHelper.write (_out,value);
	}
}
