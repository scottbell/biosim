package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class NitrogenProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenProducer value;
	public NitrogenProducerHolder()
	{
	}
	public NitrogenProducerHolder (final NitrogenProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenProducerHelper.write (_out,value);
	}
}
