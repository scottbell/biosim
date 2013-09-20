package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL interface "DryWasteConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DryWasteConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteConsumer value;
	public DryWasteConsumerHolder()
	{
	}
	public DryWasteConsumerHolder (final DryWasteConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteConsumerHelper.write (_out,value);
	}
}
