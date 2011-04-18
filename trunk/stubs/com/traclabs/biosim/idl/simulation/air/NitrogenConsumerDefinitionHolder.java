package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class NitrogenConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenConsumerDefinition value;
	public NitrogenConsumerDefinitionHolder()
	{
	}
	public NitrogenConsumerDefinitionHolder (final NitrogenConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenConsumerDefinitionHelper.write (_out,value);
	}
}
