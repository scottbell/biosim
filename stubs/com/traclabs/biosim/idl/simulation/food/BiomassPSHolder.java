package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassPS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class BiomassPSHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassPS value;
	public BiomassPSHolder()
	{
	}
	public BiomassPSHolder (final BiomassPS initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassPSHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassPSHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassPSHelper.write (_out,value);
	}
}
