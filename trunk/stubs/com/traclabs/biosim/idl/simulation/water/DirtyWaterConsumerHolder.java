package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "DirtyWaterConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DirtyWaterConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterConsumer value;
	public DirtyWaterConsumerHolder()
	{
	}
	public DirtyWaterConsumerHolder (final DirtyWaterConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterConsumerHelper.write (_out,value);
	}
}
