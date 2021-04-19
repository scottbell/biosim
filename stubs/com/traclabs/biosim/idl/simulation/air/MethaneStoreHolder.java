package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "MethaneStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
