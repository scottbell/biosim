package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "NitrogenConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class NitrogenConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenConsumer value;
	public NitrogenConsumerHolder()
	{
	}
	public NitrogenConsumerHolder (final NitrogenConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenConsumerHelper.write (_out,value);
	}
}
