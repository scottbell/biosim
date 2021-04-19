package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "HarvestingActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class HarvestingActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public HarvestingActuator value;
	public HarvestingActuatorHolder()
	{
	}
	public HarvestingActuatorHolder (final HarvestingActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return HarvestingActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = HarvestingActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		HarvestingActuatorHelper.write (_out,value);
	}
}
