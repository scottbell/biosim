package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "PotableWaterProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
