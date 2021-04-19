package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "WaterProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterProducerDefinition value;
	public WaterProducerDefinitionHolder()
	{
	}
	public WaterProducerDefinitionHolder (final WaterProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterProducerDefinitionHelper.write (_out,value);
	}
}
