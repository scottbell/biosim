package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "FoodProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodProducerDefinition value;
	public FoodProducerDefinitionHolder()
	{
	}
	public FoodProducerDefinitionHolder (final FoodProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodProducerDefinitionHelper.write (_out,value);
	}
}
