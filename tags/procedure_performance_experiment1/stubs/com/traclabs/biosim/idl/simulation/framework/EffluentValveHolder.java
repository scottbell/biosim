package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "EffluentValve"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
