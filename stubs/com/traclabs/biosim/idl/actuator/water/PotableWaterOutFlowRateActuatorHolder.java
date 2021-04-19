package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "PotableWaterOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PotableWaterOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterOutFlowRateActuator value;
	public PotableWaterOutFlowRateActuatorHolder()
	{
	}
	public PotableWaterOutFlowRateActuatorHolder (final PotableWaterOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterOutFlowRateActuatorHelper.write (_out,value);
	}
}
