package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "SimBioModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
