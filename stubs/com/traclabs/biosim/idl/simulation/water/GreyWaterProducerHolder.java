package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "GreyWaterProducer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GreyWaterProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterProducer value;
	public GreyWaterProducerHolder()
	{
	}
	public GreyWaterProducerHolder (final GreyWaterProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterProducerHelper.write (_out,value);
	}
}
