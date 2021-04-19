package com.traclabs.biosim.idl.sensor.air;

/**
 * Generated from IDL interface "MethaneOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
