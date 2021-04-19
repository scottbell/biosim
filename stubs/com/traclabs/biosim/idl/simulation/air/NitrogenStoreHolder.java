package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "NitrogenStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class NitrogenStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenStore value;
	public NitrogenStoreHolder()
	{
	}
	public NitrogenStoreHolder (final NitrogenStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenStoreHelper.write (_out,value);
	}
}
