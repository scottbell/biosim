package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneProducer value;
	public MethaneProducerHolder()
	{
	}
	public MethaneProducerHolder (final MethaneProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneProducerHelper.write (_out,value);
	}
}
