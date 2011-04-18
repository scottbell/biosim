package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "MalfunctionLength"
 *	@author JacORB IDL compiler 
 */

public final class MalfunctionLength
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _TEMPORARY_MALF = 0;
	public static final MalfunctionLength TEMPORARY_MALF = new MalfunctionLength(_TEMPORARY_MALF);
	public static final int _PERMANENT_MALF = 1;
	public static final MalfunctionLength PERMANENT_MALF = new MalfunctionLength(_PERMANENT_MALF);
	public int value()
	{
		return value;
	}
	public static MalfunctionLength from_int(int value)
	{
		switch (value) {
			case _TEMPORARY_MALF: return TEMPORARY_MALF;
			case _PERMANENT_MALF: return PERMANENT_MALF;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected MalfunctionLength(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
