package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "MethaneConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
