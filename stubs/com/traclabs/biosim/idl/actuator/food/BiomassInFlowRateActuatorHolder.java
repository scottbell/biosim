package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "BiomassInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassInFlowRateActuator value;
	public BiomassInFlowRateActuatorHolder()
	{
	}
	public BiomassInFlowRateActuatorHolder (final BiomassInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassInFlowRateActuatorHelper.write (_out,value);
	}
}
