package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "MaitenanceActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MaitenanceActivityHolder	implements org.omg.CORBA.portable.Streamable{
	 public MaitenanceActivity value;
	public MaitenanceActivityHolder()
	{
	}
	public MaitenanceActivityHolder (final MaitenanceActivity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MaitenanceActivityHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MaitenanceActivityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MaitenanceActivityHelper.write (_out,value);
	}
}
