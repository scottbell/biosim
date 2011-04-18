package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2ProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2ProducerDefinition value;
	public O2ProducerDefinitionHolder()
	{
	}
	public O2ProducerDefinitionHolder (final O2ProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2ProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2ProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2ProducerDefinitionHelper.write (_out,value);
	}
}
