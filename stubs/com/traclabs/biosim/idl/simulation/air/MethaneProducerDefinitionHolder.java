package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneProducerDefinition value;
	public MethaneProducerDefinitionHolder()
	{
	}
	public MethaneProducerDefinitionHolder (final MethaneProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneProducerDefinitionHelper.write (_out,value);
	}
}
