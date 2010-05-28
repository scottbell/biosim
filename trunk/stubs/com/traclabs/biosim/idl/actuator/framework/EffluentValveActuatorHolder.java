package com.traclabs.biosim.idl.actuator.framework;

/**
 *	Generated from IDL interface "EffluentValveActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EffluentValveActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public EffluentValveActuator value;
	public EffluentValveActuatorHolder()
	{
	}
	public EffluentValveActuatorHolder (final EffluentValveActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EffluentValveActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EffluentValveActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EffluentValveActuatorHelper.write (_out,value);
	}
}
