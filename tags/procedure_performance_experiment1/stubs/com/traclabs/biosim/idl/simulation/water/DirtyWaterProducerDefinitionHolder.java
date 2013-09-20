package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "DirtyWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DirtyWaterProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterProducerDefinition value;
	public DirtyWaterProducerDefinitionHolder()
	{
	}
	public DirtyWaterProducerDefinitionHolder (final DirtyWaterProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterProducerDefinitionHelper.write (_out,value);
	}
}
