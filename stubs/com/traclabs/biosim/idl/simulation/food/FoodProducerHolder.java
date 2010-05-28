package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FoodProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodProducer value;
	public FoodProducerHolder()
	{
	}
	public FoodProducerHolder (final FoodProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodProducerHelper.write (_out,value);
	}
}
