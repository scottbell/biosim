package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "TotalMolesSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class TotalMolesSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public TotalMolesSensor value;
	public TotalMolesSensorHolder()
	{
	}
	public TotalMolesSensorHolder (final TotalMolesSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TotalMolesSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TotalMolesSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TotalMolesSensorHelper.write (_out,value);
	}
}
