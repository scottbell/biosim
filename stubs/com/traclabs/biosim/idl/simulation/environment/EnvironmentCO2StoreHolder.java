package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "EnvironmentCO2Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EnvironmentCO2StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentCO2Store value;
	public EnvironmentCO2StoreHolder()
	{
	}
	public EnvironmentCO2StoreHolder (final EnvironmentCO2Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentCO2StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentCO2StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentCO2StoreHelper.write (_out,value);
	}
}
