package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL interface "VCCR".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class VCCRHolder	implements org.omg.CORBA.portable.Streamable{
	 public VCCR value;
	public VCCRHolder()
	{
	}
	public VCCRHolder (final VCCR initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return VCCRHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = VCCRHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		VCCRHelper.write (_out,value);
	}
}
