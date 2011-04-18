package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL interface "BioDriver"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BioDriverHolder	implements org.omg.CORBA.portable.Streamable{
	 public BioDriver value;
	public BioDriverHolder()
	{
	}
	public BioDriverHolder (final BioDriver initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BioDriverHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BioDriverHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BioDriverHelper.write (_out,value);
	}
}
