package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL definition of struct "Air"
 *	@author JacORB IDL compiler 
 */

public final class AirHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.environment.Air value;

	public AirHolder ()
	{
	}
	public AirHolder(final com.traclabs.biosim.idl.simulation.environment.Air initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.traclabs.biosim.idl.simulation.environment.AirHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.traclabs.biosim.idl.simulation.environment.AirHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.traclabs.biosim.idl.simulation.environment.AirHelper.write(_out, value);
	}
}
