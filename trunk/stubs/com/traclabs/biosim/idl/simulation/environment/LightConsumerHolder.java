package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "LightConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class LightConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public LightConsumer value;
	public LightConsumerHolder()
	{
	}
	public LightConsumerHolder (final LightConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return LightConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = LightConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		LightConsumerHelper.write (_out,value);
	}
}
