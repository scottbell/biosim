package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL alias "SimEnvironmentList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class SimEnvironmentListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] value;

	public SimEnvironmentListHolder ()
	{
	}
	public SimEnvironmentListHolder (final com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return SimEnvironmentListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SimEnvironmentListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		SimEnvironmentListHelper.write (out,value);
	}
}
