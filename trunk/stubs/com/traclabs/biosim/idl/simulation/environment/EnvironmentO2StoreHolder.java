package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentO2Store"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentO2StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentO2Store value;
	public EnvironmentO2StoreHolder()
	{
	}
	public EnvironmentO2StoreHolder (final EnvironmentO2Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentO2StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentO2StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentO2StoreHelper.write (_out,value);
	}
}
