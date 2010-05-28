package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerPSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerPSActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerPSActuator value;
	public PowerPSActuatorHolder()
	{
	}
	public PowerPSActuatorHolder (final PowerPSActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerPSActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerPSActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerPSActuatorHelper.write (_out,value);
	}
}
