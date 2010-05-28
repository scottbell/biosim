package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "Activity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
