package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CO2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CO2ProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2ProducerDefinition value;
	public CO2ProducerDefinitionHolder()
	{
	}
	public CO2ProducerDefinitionHolder (final CO2ProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2ProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2ProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2ProducerDefinitionHelper.write (_out,value);
	}
}
