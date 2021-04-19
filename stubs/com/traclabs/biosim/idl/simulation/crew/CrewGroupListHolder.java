package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL alias "CrewGroupList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewGroupListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup[] value;

	public CrewGroupListHolder ()
	{
	}
	public CrewGroupListHolder (final com.traclabs.biosim.idl.simulation.crew.CrewGroup[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CrewGroupListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CrewGroupListHelper.write (out,value);
	}
}
