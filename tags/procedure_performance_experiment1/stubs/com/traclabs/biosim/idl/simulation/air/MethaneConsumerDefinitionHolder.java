package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneConsumerDefinition value;
	public MethaneConsumerDefinitionHolder()
	{
	}
	public MethaneConsumerDefinitionHolder (final MethaneConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneConsumerDefinitionHelper.write (_out,value);
	}
}
