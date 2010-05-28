package com.traclabs.biosim.idl.actuator.crew;

/**
 *	Generated from IDL interface "CrewGroupActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupActuator value;
	public CrewGroupActuatorHolder()
	{
	}
	public CrewGroupActuatorHolder (final CrewGroupActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupActuatorHelper.write (_out,value);
	}
}
