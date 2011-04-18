package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL interface "DryWasteProducer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DryWasteProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteProducer value;
	public DryWasteProducerHolder()
	{
	}
	public DryWasteProducerHolder (final DryWasteProducer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteProducerHelper.write (_out,value);
	}
}
