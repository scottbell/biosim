package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "PotableWaterProducer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
