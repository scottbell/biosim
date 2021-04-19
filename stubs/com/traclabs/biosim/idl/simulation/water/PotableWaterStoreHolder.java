package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "PotableWaterStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PotableWaterStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterStore value;
	public PotableWaterStoreHolder()
	{
	}
	public PotableWaterStoreHolder (final PotableWaterStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterStoreHelper.write (_out,value);
	}
}
