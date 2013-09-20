package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "SimBioModule"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class SimBioModuleHolder	implements org.omg.CORBA.portable.Streamable{
	 public SimBioModule value;
	public SimBioModuleHolder()
	{
	}
	public SimBioModuleHolder (final SimBioModule initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return SimBioModuleHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SimBioModuleHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		SimBioModuleHelper.write (_out,value);
	}
}
