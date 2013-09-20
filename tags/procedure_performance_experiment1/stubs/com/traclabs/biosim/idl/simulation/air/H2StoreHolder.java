package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "H2Store"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class H2StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2Store value;
	public H2StoreHolder()
	{
	}
	public H2StoreHolder (final H2Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2StoreHelper.write (_out,value);
	}
}
