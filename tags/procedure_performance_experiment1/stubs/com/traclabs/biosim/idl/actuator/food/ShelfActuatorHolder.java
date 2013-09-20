package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "ShelfActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ShelfActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public ShelfActuator value;
	public ShelfActuatorHolder()
	{
	}
	public ShelfActuatorHolder (final ShelfActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ShelfActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ShelfActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ShelfActuatorHelper.write (_out,value);
	}
}
