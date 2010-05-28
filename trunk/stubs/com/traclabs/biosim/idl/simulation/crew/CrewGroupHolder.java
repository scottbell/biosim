package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "CrewGroup"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroup value;
	public CrewGroupHolder()
	{
	}
	public CrewGroupHolder (final CrewGroup initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupHelper.write (_out,value);
	}
}
