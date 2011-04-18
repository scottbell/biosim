package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentConsumer value;
	public EnvironmentConsumerHolder()
	{
	}
	public EnvironmentConsumerHolder (final EnvironmentConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentConsumerHelper.write (_out,value);
	}
}
