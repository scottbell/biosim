package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "BiomassPSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
