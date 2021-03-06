package com.traclabs.biosim.idl.simulation.air.cdrs;

/**
 * Generated from IDL interface "CDRSModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CDRSModuleHolder	implements org.omg.CORBA.portable.Streamable{
	 public CDRSModule value;
	public CDRSModuleHolder()
	{
	}
	public CDRSModuleHolder (final CDRSModule initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CDRSModuleHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSModuleHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CDRSModuleHelper.write (_out,value);
	}
}
