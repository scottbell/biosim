package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BiomassConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassConsumer value;
	public BiomassConsumerHolder()
	{
	}
	public BiomassConsumerHolder (final BiomassConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassConsumerHelper.write (_out,value);
	}
}
