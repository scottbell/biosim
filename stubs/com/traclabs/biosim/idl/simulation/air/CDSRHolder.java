package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CDSR"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CDSRHolder	implements org.omg.CORBA.portable.Streamable{
	 public CDSR value;
	public CDSRHolder()
	{
	}
	public CDSRHolder (final CDSR initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CDSRHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDSRHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CDSRHelper.write (_out,value);
	}
}
