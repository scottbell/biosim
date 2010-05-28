package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentStore value;
	public EnvironmentStoreHolder()
	{
	}
	public EnvironmentStoreHolder (final EnvironmentStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentStoreHelper.write (_out,value);
	}
}
