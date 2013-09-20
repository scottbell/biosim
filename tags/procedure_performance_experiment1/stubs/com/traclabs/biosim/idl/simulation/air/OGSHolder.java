package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "OGS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class OGSHolder	implements org.omg.CORBA.portable.Streamable{
	 public OGS value;
	public OGSHolder()
	{
	}
	public OGSHolder (final OGS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return OGSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = OGSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		OGSHelper.write (_out,value);
	}
}
