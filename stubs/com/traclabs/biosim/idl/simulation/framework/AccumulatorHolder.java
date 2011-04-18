package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "Accumulator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AccumulatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public Accumulator value;
	public AccumulatorHolder()
	{
	}
	public AccumulatorHolder (final Accumulator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AccumulatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AccumulatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AccumulatorHelper.write (_out,value);
	}
}
