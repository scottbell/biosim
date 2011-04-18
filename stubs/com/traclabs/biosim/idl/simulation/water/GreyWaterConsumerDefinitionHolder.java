package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "GreyWaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GreyWaterConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterConsumerDefinition value;
	public GreyWaterConsumerDefinitionHolder()
	{
	}
	public GreyWaterConsumerDefinitionHolder (final GreyWaterConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterConsumerDefinitionHelper.write (_out,value);
	}
}
