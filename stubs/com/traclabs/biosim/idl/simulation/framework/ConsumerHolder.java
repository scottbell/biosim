package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "Consumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public Consumer value;
	public ConsumerHolder()
	{
	}
	public ConsumerHolder (final Consumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ConsumerHelper.write (_out,value);
	}
}
