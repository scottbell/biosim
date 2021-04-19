package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "H2Producer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class H2ProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2Producer value;
	public H2ProducerHolder()
	{
	}
	public H2ProducerHolder (final H2Producer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2ProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2ProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2ProducerHelper.write (_out,value);
	}
}
