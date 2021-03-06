package com.traclabs.biosim.idl.framework;

/**
 * Generated from IDL interface "BioModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BioModuleHolder	implements org.omg.CORBA.portable.Streamable{
	 public BioModule value;
	public BioModuleHolder()
	{
	}
	public BioModuleHolder (final BioModule initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BioModuleHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BioModuleHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BioModuleHelper.write (_out,value);
	}
}
