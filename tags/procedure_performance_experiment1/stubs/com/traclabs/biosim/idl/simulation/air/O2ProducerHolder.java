package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2Producer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2ProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2Producer value;
	public O2ProducerHolder()
	{
	}
	public O2ProducerHolder (final O2Producer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2ProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2ProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2ProducerHelper.write (_out,value);
	}
}
