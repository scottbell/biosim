package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "WaterRSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterRSActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterRSActuator value;
	public WaterRSActuatorHolder()
	{
	}
	public WaterRSActuatorHolder (final WaterRSActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterRSActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterRSActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterRSActuatorHelper.write (_out,value);
	}
}
