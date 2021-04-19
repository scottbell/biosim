package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "AirProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirProducerDefinition value;
	public AirProducerDefinitionHolder()
	{
	}
	public AirProducerDefinitionHolder (final AirProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirProducerDefinitionHelper.write (_out,value);
	}
}
