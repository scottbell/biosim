package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "O2Consumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class O2ConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2Consumer value;
	public O2ConsumerHolder()
	{
	}
	public O2ConsumerHolder (final O2Consumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2ConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2ConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2ConsumerHelper.write (_out,value);
	}
}
