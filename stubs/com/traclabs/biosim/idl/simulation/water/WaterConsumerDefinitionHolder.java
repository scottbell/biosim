package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "WaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterConsumerDefinition value;
	public WaterConsumerDefinitionHolder()
	{
	}
	public WaterConsumerDefinitionHolder (final WaterConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterConsumerDefinitionHelper.write (_out,value);
	}
}
