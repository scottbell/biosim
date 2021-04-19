package com.traclabs.biosim.idl.simulation.environment;

/**
 * Generated from IDL interface "Dehumidifier".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
