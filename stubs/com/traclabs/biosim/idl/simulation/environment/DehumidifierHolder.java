package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "Dehumidifier"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DehumidifierHolder	implements org.omg.CORBA.portable.Streamable{
	 public Dehumidifier value;
	public DehumidifierHolder()
	{
	}
	public DehumidifierHolder (final Dehumidifier initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DehumidifierHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DehumidifierHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DehumidifierHelper.write (_out,value);
	}
}
