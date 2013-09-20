package com.traclabs.biosim.idl.sensor.waste;

/**
 *	Generated from IDL interface "DryWasteOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DryWasteOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteOutFlowRateSensor value;
	public DryWasteOutFlowRateSensorHolder()
	{
	}
	public DryWasteOutFlowRateSensorHolder (final DryWasteOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteOutFlowRateSensorHelper.write (_out,value);
	}
}
