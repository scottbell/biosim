package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
