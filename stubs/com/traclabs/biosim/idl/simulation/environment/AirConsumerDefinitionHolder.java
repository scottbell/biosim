package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "AirConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AirConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirConsumerDefinition value;
	public AirConsumerDefinitionHolder()
	{
	}
	public AirConsumerDefinitionHolder (final AirConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirConsumerDefinitionHelper.write (_out,value);
	}
}
