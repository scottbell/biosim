package com.traclabs.biosim.idl.actuator.framework;

/**
 *	Generated from IDL interface "InfluentValveActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class InfluentValveActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public InfluentValveActuator value;
	public InfluentValveActuatorHolder()
	{
	}
	public InfluentValveActuatorHolder (final InfluentValveActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return InfluentValveActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = InfluentValveActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		InfluentValveActuatorHelper.write (_out,value);
	}
}
