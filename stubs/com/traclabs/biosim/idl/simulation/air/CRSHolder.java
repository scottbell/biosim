package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "CRS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
