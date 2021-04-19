package com.traclabs.biosim.idl.simulation.power;

/**
 * Generated from IDL interface "PowerProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PowerProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerProducerDefinition value;
	public PowerProducerDefinitionHolder()
	{
	}
	public PowerProducerDefinitionHolder (final PowerProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerProducerDefinitionHelper.write (_out,value);
	}
}
