package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BiomassProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassProducer value;
	public BiomassProducerHolder()
	{
	}
	public BiomassProducerHolder (final BiomassProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassProducerHelper.write (_out,value);
	}
}
