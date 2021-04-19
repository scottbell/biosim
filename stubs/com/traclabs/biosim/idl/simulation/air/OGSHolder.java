package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "OGS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
