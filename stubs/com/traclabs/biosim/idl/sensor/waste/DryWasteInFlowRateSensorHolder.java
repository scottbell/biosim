package com.traclabs.biosim.idl.sensor.waste;

/**
 * Generated from IDL interface "DryWasteInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DryWasteInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteInFlowRateSensor value;
	public DryWasteInFlowRateSensorHolder()
	{
	}
	public DryWasteInFlowRateSensorHolder (final DryWasteInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteInFlowRateSensorHelper.write (_out,value);
	}
}
