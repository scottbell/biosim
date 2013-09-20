package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "GreyWaterStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GreyWaterStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterStore value;
	public GreyWaterStoreHolder()
	{
	}
	public GreyWaterStoreHolder (final GreyWaterStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterStoreHelper.write (_out,value);
	}
}
