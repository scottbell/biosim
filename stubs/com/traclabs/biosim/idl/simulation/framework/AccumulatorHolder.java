package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "Accumulator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
