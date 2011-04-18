package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "PotableWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PotableWaterProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterProducerDefinition value;
	public PotableWaterProducerDefinitionHolder()
	{
	}
	public PotableWaterProducerDefinitionHolder (final PotableWaterProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterProducerDefinitionHelper.write (_out,value);
	}
}
