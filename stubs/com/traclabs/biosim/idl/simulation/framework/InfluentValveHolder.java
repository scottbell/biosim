package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "InfluentValve".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class InfluentValveHolder	implements org.omg.CORBA.portable.Streamable{
	 public InfluentValve value;
	public InfluentValveHolder()
	{
	}
	public InfluentValveHolder (final InfluentValve initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return InfluentValveHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = InfluentValveHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		InfluentValveHelper.write (_out,value);
	}
}
