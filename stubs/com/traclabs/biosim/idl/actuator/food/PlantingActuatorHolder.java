package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "PlantingActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
