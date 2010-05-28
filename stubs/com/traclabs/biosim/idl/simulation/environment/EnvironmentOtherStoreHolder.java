package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentOtherStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentOtherStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentOtherStore value;
	public EnvironmentOtherStoreHolder()
	{
	}
	public EnvironmentOtherStoreHolder (final EnvironmentOtherStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentOtherStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentOtherStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentOtherStoreHelper.write (_out,value);
	}
}
