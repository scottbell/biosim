package com.traclabs.biosim.idl.simulation.crew;

/**
 * Generated from IDL interface "Activity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class ActivityHolder	implements org.omg.CORBA.portable.Streamable{
	 public Activity value;
	public ActivityHolder()
	{
	}
	public ActivityHolder (final Activity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ActivityHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ActivityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ActivityHelper.write (_out,value);
	}
}
