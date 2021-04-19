package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "CO2Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2Store value;
	public CO2StoreHolder()
	{
	}
	public CO2StoreHolder (final CO2Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2StoreHelper.write (_out,value);
	}
}
