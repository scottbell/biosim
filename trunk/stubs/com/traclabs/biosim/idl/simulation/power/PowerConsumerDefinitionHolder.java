package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerConsumerDefinition value;
	public PowerConsumerDefinitionHolder()
	{
	}
	public PowerConsumerDefinitionHolder (final PowerConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerConsumerDefinitionHelper.write (_out,value);
	}
}
