package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "BiomassStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassStore value;
	public BiomassStoreHolder()
	{
	}
	public BiomassStoreHolder (final BiomassStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassStoreHelper.write (_out,value);
	}
}
