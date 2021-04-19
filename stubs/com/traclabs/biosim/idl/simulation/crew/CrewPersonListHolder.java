package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL alias "CrewPersonList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewPersonListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.crew.CrewPerson[] value;

	public CrewPersonListHolder ()
	{
	}
	public CrewPersonListHolder (final com.traclabs.biosim.idl.simulation.crew.CrewPerson[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CrewPersonListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewPersonListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CrewPersonListHelper.write (out,value);
	}
}
