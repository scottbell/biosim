package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerConsumer value;
	public PowerConsumerHolder()
	{
	}
	public PowerConsumerHolder (final PowerConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerConsumerHelper.write (_out,value);
	}
}
