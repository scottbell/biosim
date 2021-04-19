package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "EnvironmentFlowRateControllable".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EnvironmentFlowRateControllableHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentFlowRateControllable value;
	public EnvironmentFlowRateControllableHolder()
	{
	}
	public EnvironmentFlowRateControllableHolder (final EnvironmentFlowRateControllable initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentFlowRateControllableHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentFlowRateControllableHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentFlowRateControllableHelper.write (_out,value);
	}
}
