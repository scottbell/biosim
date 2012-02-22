package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "BioCommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BioCommandListenerHolder	implements org.omg.CORBA.portable.Streamable{
	 public BioCommandListener value;
	public BioCommandListenerHolder()
	{
	}
	public BioCommandListenerHolder (final BioCommandListener initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BioCommandListenerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BioCommandListenerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BioCommandListenerHelper.write (_out,value);
	}
}
