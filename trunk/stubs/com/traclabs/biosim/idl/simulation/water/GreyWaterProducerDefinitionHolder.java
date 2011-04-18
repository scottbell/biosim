package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "GreyWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GreyWaterProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterProducerDefinition value;
	public GreyWaterProducerDefinitionHolder()
	{
	}
	public GreyWaterProducerDefinitionHolder (final GreyWaterProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterProducerDefinitionHelper.write (_out,value);
	}
}
