package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "EffluentValve".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EffluentValveHolder	implements org.omg.CORBA.portable.Streamable{
	 public EffluentValve value;
	public EffluentValveHolder()
	{
	}
	public EffluentValveHolder (final EffluentValve initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EffluentValveHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EffluentValveHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EffluentValveHelper.write (_out,value);
	}
}
