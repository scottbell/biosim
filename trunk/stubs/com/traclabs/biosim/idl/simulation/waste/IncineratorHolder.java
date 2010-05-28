package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL interface "Incinerator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class IncineratorHolder	implements org.omg.CORBA.portable.Streamable{
	 public Incinerator value;
	public IncineratorHolder()
	{
	}
	public IncineratorHolder (final Incinerator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return IncineratorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IncineratorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		IncineratorHelper.write (_out,value);
	}
}
