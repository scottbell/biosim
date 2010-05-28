package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "MalfunctionIntensity"
 *	@author JacORB IDL compiler 
 */

public final class MalfunctionIntensity
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _SEVERE_MALF = 0;
	public static final MalfunctionIntensity SEVERE_MALF = new MalfunctionIntensity(_SEVERE_MALF);
	public static final int _MEDIUM_MALF = 1;
	public static final MalfunctionIntensity MEDIUM_MALF = new MalfunctionIntensity(_MEDIUM_MALF);
	public static final int _LOW_MALF = 2;
	public static final MalfunctionIntensity LOW_MALF = new MalfunctionIntensity(_LOW_MALF);
	public int value()
	{
		return value;
	}
	public static MalfunctionIntensity from_int(int value)
	{
		switch (value) {
			case _SEVERE_MALF: return SEVERE_MALF;
			case _MEDIUM_MALF: return MEDIUM_MALF;
			case _LOW_MALF: return LOW_MALF;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected MalfunctionIntensity(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
