package com.traclabs.biosim.idl.actuator.framework;

/**
 * Generated from IDL interface "GenericActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GenericActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GenericActuator value;
	public GenericActuatorHolder()
	{
	}
	public GenericActuatorHolder (final GenericActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GenericActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GenericActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GenericActuatorHelper.write (_out,value);
	}
}
