package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "DirtyWaterProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DirtyWaterProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterProducer value;
	public DirtyWaterProducerHolder()
	{
	}
	public DirtyWaterProducerHolder (final DirtyWaterProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterProducerHelper.write (_out,value);
	}
}
