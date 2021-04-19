package com.traclabs.biosim.idl.simulation.thermal;

/**
 * Generated from IDL interface "IATCS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class IATCSHolder	implements org.omg.CORBA.portable.Streamable{
	 public IATCS value;
	public IATCSHolder()
	{
	}
	public IATCSHolder (final IATCS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return IATCSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IATCSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		IATCSHelper.write (_out,value);
	}
}
