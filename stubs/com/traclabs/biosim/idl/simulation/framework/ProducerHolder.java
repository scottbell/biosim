package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "Producer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public Producer value;
	public ProducerHolder()
	{
	}
	public ProducerHolder (final Producer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ProducerHelper.write (_out,value);
	}
}
