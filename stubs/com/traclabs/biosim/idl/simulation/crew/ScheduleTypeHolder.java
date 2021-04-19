package com.traclabs.biosim.idl.simulation.crew;
/**
 * Generated from IDL enum "ScheduleType".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
