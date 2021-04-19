package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL interface "EVAActivity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EVAActivityHolder	implements org.omg.CORBA.portable.Streamable{
	 public EVAActivity value;
	public EVAActivityHolder()
	{
	}
	public EVAActivityHolder (final EVAActivity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EVAActivityHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EVAActivityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EVAActivityHelper.write (_out,value);
	}
}
