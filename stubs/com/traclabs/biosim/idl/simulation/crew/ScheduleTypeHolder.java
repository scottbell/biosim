package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "ScheduleType"
 *	@author JacORB IDL compiler 
 */

public final class ScheduleTypeHolder
	implements org.omg.CORBA.portable.Streamable
{
	public ScheduleType value;

	public ScheduleTypeHolder ()
	{
	}
	public ScheduleTypeHolder (final ScheduleType initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ScheduleTypeHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ScheduleTypeHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ScheduleTypeHelper.write (out,value);
	}
}
