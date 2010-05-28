package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BiomassConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassConsumerDefinition value;
	public BiomassConsumerDefinitionHolder()
	{
	}
	public BiomassConsumerDefinitionHolder (final BiomassConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassConsumerDefinitionHelper.write (_out,value);
	}
}
