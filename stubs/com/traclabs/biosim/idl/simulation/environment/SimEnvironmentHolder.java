package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "SimEnvironment"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class SimEnvironmentHolder	implements org.omg.CORBA.portable.Streamable{
	 public SimEnvironment value;
	public SimEnvironmentHolder()
	{
	}
	public SimEnvironmentHolder (final SimEnvironment initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return SimEnvironmentHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SimEnvironmentHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		SimEnvironmentHelper.write (_out,value);
	}
}
