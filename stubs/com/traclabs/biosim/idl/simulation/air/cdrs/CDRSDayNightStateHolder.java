package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSDayNightState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSDayNightStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSDayNightState value;

	public CDRSDayNightStateHolder ()
	{
	}
	public CDRSDayNightStateHolder (final CDRSDayNightState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSDayNightStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSDayNightStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSDayNightStateHelper.write (out,value);
	}
}
