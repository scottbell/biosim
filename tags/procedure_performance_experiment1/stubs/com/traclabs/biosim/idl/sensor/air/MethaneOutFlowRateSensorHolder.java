package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "MethaneOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneOutFlowRateSensor value;
	public MethaneOutFlowRateSensorHolder()
	{
	}
	public MethaneOutFlowRateSensorHolder (final MethaneOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneOutFlowRateSensorHelper.write (_out,value);
	}
}
