package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "BiomassOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassOutFlowRateActuator value;
	public BiomassOutFlowRateActuatorHolder()
	{
	}
	public BiomassOutFlowRateActuatorHolder (final BiomassOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassOutFlowRateActuatorHelper.write (_out,value);
	}
}
