package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentNitrogenStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentNitrogenStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentNitrogenStore value;
	public EnvironmentNitrogenStoreHolder()
	{
	}
	public EnvironmentNitrogenStoreHolder (final EnvironmentNitrogenStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentNitrogenStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentNitrogenStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentNitrogenStoreHelper.write (_out,value);
	}
}
