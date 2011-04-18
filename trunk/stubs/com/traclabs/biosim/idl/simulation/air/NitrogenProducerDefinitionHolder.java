package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class NitrogenProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenProducerDefinition value;
	public NitrogenProducerDefinitionHolder()
	{
	}
	public NitrogenProducerDefinitionHolder (final NitrogenProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenProducerDefinitionHelper.write (_out,value);
	}
}
