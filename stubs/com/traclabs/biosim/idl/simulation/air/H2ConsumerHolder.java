package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "H2Consumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class H2ConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2Consumer value;
	public H2ConsumerHolder()
	{
	}
	public H2ConsumerHolder (final H2Consumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2ConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2ConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2ConsumerHelper.write (_out,value);
	}
}
