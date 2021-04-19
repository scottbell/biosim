package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "DirtyWaterStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DirtyWaterStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterStore value;
	public DirtyWaterStoreHolder()
	{
	}
	public DirtyWaterStoreHolder (final DirtyWaterStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterStoreHelper.write (_out,value);
	}
}
