package com.traclabs.biosim.idl.framework;

/**
 * Generated from IDL interface "TechSpecificInfo".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class TechSpecificInfoHolder	implements org.omg.CORBA.portable.Streamable{
	 public TechSpecificInfo value;
	public TechSpecificInfoHolder()
	{
	}
	public TechSpecificInfoHolder (final TechSpecificInfo initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TechSpecificInfoHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TechSpecificInfoHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TechSpecificInfoHelper.write (_out,value);
	}
}
