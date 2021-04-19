package com.traclabs.biosim.idl.simulation.waste;

/**
 * Generated from IDL interface "DryWasteStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DryWasteStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteStore value;
	public DryWasteStoreHolder()
	{
	}
	public DryWasteStoreHolder (final DryWasteStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteStoreHelper.write (_out,value);
	}
}
