package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "PassiveModule"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PassiveModuleHolder	implements org.omg.CORBA.portable.Streamable{
	 public PassiveModule value;
	public PassiveModuleHolder()
	{
	}
	public PassiveModuleHolder (final PassiveModule initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PassiveModuleHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PassiveModuleHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PassiveModuleHelper.write (_out,value);
	}
}
