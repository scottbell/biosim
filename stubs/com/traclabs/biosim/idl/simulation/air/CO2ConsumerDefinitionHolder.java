package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CO2ConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CO2ConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2ConsumerDefinition value;
	public CO2ConsumerDefinitionHolder()
	{
	}
	public CO2ConsumerDefinitionHolder (final CO2ConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2ConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2ConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2ConsumerDefinitionHelper.write (_out,value);
	}
}
