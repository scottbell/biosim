package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneConsumer value;
	public MethaneConsumerHolder()
	{
	}
	public MethaneConsumerHolder (final MethaneConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneConsumerHelper.write (_out,value);
	}
}
