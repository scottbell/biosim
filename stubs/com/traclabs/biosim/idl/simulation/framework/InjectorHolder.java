package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "Injector".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class InjectorHolder	implements org.omg.CORBA.portable.Streamable{
	 public Injector value;
	public InjectorHolder()
	{
	}
	public InjectorHolder (final Injector initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return InjectorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = InjectorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		InjectorHelper.write (_out,value);
	}
}
