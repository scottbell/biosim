package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSPowerState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSPowerStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSPowerState value;

	public CDRSPowerStateHolder ()
	{
	}
	public CDRSPowerStateHolder (final CDRSPowerState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSPowerStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSPowerStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSPowerStateHelper.write (out,value);
	}
}
