package com.traclabs.biosim.idl.actuator.crew;

/**
 * Generated from IDL interface "CrewGroupActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
