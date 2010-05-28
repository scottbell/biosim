package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL definition of alias "CrewGroupList"
 *	@author JacORB IDL compiler 
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
