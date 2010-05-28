package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "H2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class H2ProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2ProducerDefinition value;
	public H2ProducerDefinitionHolder()
	{
	}
	public H2ProducerDefinitionHolder (final H2ProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2ProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2ProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2ProducerDefinitionHelper.write (_out,value);
	}
}
