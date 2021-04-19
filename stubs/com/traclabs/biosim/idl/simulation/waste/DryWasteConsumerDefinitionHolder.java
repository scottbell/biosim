package com.traclabs.biosim.idl.simulation.waste;

/**
 * Generated from IDL interface "DryWasteConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DryWasteConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteConsumerDefinition value;
	public DryWasteConsumerDefinitionHolder()
	{
	}
	public DryWasteConsumerDefinitionHolder (final DryWasteConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteConsumerDefinitionHelper.write (_out,value);
	}
}
