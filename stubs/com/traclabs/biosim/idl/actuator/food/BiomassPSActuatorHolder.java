package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "BiomassPSActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassPSActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassPSActuator value;
	public BiomassPSActuatorHolder()
	{
	}
	public BiomassPSActuatorHolder (final BiomassPSActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassPSActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassPSActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassPSActuatorHelper.write (_out,value);
	}
}
