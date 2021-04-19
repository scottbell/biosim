package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "CO2Consumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2ConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2Consumer value;
	public CO2ConsumerHolder()
	{
	}
	public CO2ConsumerHolder (final CO2Consumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2ConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2ConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2ConsumerHelper.write (_out,value);
	}
}
