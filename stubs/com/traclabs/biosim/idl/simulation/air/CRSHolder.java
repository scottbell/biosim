package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CRS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CRSHolder	implements org.omg.CORBA.portable.Streamable{
	 public CRS value;
	public CRSHolder()
	{
	}
	public CRSHolder (final CRS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CRSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CRSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CRSHelper.write (_out,value);
	}
}
