package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "LightConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class LightConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public LightConsumerDefinition value;
	public LightConsumerDefinitionHolder()
	{
	}
	public LightConsumerDefinitionHolder (final LightConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return LightConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = LightConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		LightConsumerDefinitionHelper.write (_out,value);
	}
}
