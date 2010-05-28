package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL definition of alias "BioModuleList"
 *	@author JacORB IDL compiler 
 */

public final class BioModuleListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.framework.BioModule[] value;

	public BioModuleListHolder ()
	{
	}
	public BioModuleListHolder (final com.traclabs.biosim.idl.framework.BioModule[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BioModuleListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BioModuleListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BioModuleListHelper.write (out,value);
	}
}
