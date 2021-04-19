package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL interface "DirtyWaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DirtyWaterConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterConsumerDefinition value;
	public DirtyWaterConsumerDefinitionHolder()
	{
	}
	public DirtyWaterConsumerDefinitionHolder (final DirtyWaterConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterConsumerDefinitionHelper.write (_out,value);
	}
}
