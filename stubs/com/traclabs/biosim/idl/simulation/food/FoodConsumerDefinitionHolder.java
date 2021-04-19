package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "FoodConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodConsumerDefinition value;
	public FoodConsumerDefinitionHolder()
	{
	}
	public FoodConsumerDefinitionHolder (final FoodConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodConsumerDefinitionHelper.write (_out,value);
	}
}
