package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "AirRSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AirRSActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirRSActuator value;
	public AirRSActuatorHolder()
	{
	}
	public AirRSActuatorHolder (final AirRSActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirRSActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirRSActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirRSActuatorHelper.write (_out,value);
	}
}
