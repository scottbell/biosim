package com.traclabs.biosim.idl.simulation.waste;

/**
 * Generated from IDL interface "DryWasteProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DryWasteProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteProducerDefinition value;
	public DryWasteProducerDefinitionHolder()
	{
	}
	public DryWasteProducerDefinitionHolder (final DryWasteProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteProducerDefinitionHelper.write (_out,value);
	}
}
