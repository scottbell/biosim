package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "CO2Producer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2ProducerHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2Producer value;
	public CO2ProducerHolder()
	{
	}
	public CO2ProducerHolder (final CO2Producer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2ProducerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2ProducerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2ProducerHelper.write (_out,value);
	}
}
