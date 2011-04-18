package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "PotableWaterProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PotableWaterProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterProducer value;
	public PotableWaterProducerHolder()
	{
	}
	public PotableWaterProducerHolder (final PotableWaterProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterProducerHelper.write (_out,value);
	}
}
