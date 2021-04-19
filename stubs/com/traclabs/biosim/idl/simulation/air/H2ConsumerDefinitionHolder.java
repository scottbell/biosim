package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "H2ConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class H2ConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2ConsumerDefinition value;
	public H2ConsumerDefinitionHolder()
	{
	}
	public H2ConsumerDefinitionHolder (final H2ConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2ConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2ConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2ConsumerDefinitionHelper.write (_out,value);
	}
}
