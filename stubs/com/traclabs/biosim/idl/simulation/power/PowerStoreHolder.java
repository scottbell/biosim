package com.traclabs.biosim.idl.simulation.power;

/**
 * Generated from IDL interface "PowerStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PowerStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerStore value;
	public PowerStoreHolder()
	{
	}
	public PowerStoreHolder (final PowerStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerStoreHelper.write (_out,value);
	}
}
