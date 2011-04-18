package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "WaterStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterStore value;
	public WaterStoreHolder()
	{
	}
	public WaterStoreHolder (final WaterStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterStoreHelper.write (_out,value);
	}
}
