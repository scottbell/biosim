package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL interface "CrewPerson".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewPersonHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewPerson value;
	public CrewPersonHolder()
	{
	}
	public CrewPersonHolder (final CrewPerson initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewPersonHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewPersonHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewPersonHelper.write (_out,value);
	}
}
