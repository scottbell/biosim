package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "AirRS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AirRSHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirRS value;
	public AirRSHolder()
	{
	}
	public AirRSHolder (final AirRS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirRSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirRSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirRSHelper.write (_out,value);
	}
}
