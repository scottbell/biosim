package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "PotableWaterInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PotableWaterInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterInFlowRateActuator value;
	public PotableWaterInFlowRateActuatorHolder()
	{
	}
	public PotableWaterInFlowRateActuatorHolder (final PotableWaterInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterInFlowRateActuatorHelper.write (_out,value);
	}
}
