package com.traclabs.biosim.idl.actuator.environment;

/**
 *	Generated from IDL interface "EnvironmentActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentActuator value;
	public EnvironmentActuatorHolder()
	{
	}
	public EnvironmentActuatorHolder (final EnvironmentActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentActuatorHelper.write (_out,value);
	}
}
