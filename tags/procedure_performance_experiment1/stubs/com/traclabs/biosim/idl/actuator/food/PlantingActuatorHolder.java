package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "PlantingActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PlantingActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PlantingActuator value;
	public PlantingActuatorHolder()
	{
	}
	public PlantingActuatorHolder (final PlantingActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PlantingActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PlantingActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PlantingActuatorHelper.write (_out,value);
	}
}
