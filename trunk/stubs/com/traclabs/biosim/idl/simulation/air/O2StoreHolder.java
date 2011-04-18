package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2Store"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2Store value;
	public O2StoreHolder()
	{
	}
	public O2StoreHolder (final O2Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2StoreHelper.write (_out,value);
	}
}
