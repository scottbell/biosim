package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "Pyrolizer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PyrolizerHolder	implements org.omg.CORBA.portable.Streamable{
	 public Pyrolizer value;
	public PyrolizerHolder()
	{
	}
	public PyrolizerHolder (final Pyrolizer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PyrolizerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PyrolizerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PyrolizerHelper.write (_out,value);
	}
}
