package com.traclabs.biosim.idl.actuator.crew;

/**
 *	Generated from IDL interface "CrewGroupActivityActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupActivityActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupActivityActuator value;
	public CrewGroupActivityActuatorHolder()
	{
	}
	public CrewGroupActivityActuatorHolder (final CrewGroupActivityActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupActivityActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupActivityActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupActivityActuatorHelper.write (_out,value);
	}
}
