package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneStore value;
	public MethaneStoreHolder()
	{
	}
	public MethaneStoreHolder (final MethaneStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneStoreHelper.write (_out,value);
	}
}
