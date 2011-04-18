package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "BiomassInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
