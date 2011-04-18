package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "PotableWaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PotableWaterConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterConsumerDefinition value;
	public PotableWaterConsumerDefinitionHolder()
	{
	}
	public PotableWaterConsumerDefinitionHolder (final PotableWaterConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterConsumerDefinitionHelper.write (_out,value);
	}
}
