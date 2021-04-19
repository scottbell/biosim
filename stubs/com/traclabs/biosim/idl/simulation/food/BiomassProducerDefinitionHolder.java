package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "BiomassProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassProducerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassProducerDefinition value;
	public BiomassProducerDefinitionHolder()
	{
	}
	public BiomassProducerDefinitionHolder (final BiomassProducerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassProducerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassProducerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassProducerDefinitionHelper.write (_out,value);
	}
}
