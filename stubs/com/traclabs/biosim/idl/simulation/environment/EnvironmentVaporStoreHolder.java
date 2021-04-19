package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "EnvironmentVaporStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EnvironmentVaporStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentVaporStore value;
	public EnvironmentVaporStoreHolder()
	{
	}
	public EnvironmentVaporStoreHolder (final EnvironmentVaporStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentVaporStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentVaporStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentVaporStoreHelper.write (_out,value);
	}
}
