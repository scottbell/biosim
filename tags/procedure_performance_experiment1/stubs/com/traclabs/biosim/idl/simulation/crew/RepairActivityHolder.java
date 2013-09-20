package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "RepairActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class RepairActivityHolder	implements org.omg.CORBA.portable.Streamable{
	 public RepairActivity value;
	public RepairActivityHolder()
	{
	}
	public RepairActivityHolder (final RepairActivity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return RepairActivityHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = RepairActivityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		RepairActivityHelper.write (_out,value);
	}
}
