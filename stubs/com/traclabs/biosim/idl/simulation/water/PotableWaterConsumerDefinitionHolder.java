package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "PotableWaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
