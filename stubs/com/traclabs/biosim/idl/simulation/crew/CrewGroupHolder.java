package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL interface "CrewGroup".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
