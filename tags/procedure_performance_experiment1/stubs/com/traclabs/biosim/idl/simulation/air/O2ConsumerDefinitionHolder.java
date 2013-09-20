package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2ConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2ConsumerDefinitionHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2ConsumerDefinition value;
	public O2ConsumerDefinitionHolder()
	{
	}
	public O2ConsumerDefinitionHolder (final O2ConsumerDefinition initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2ConsumerDefinitionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2ConsumerDefinitionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2ConsumerDefinitionHelper.write (_out,value);
	}
}
