package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IATCSActivation"
 *	@author JacORB IDL compiler 
 */

public final class IATCSActivationHolder
	implements org.omg.CORBA.portable.Streamable
{
	public IATCSActivation value;

	public IATCSActivationHolder ()
	{
	}
	public IATCSActivationHolder (final IATCSActivation initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return IATCSActivationHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IATCSActivationHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		IATCSActivationHelper.write (out,value);
	}
}
