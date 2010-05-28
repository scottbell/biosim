package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "Sex"
 *	@author JacORB IDL compiler 
 */

public final class Sex
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _male = 0;
	public static final Sex male = new Sex(_male);
	public static final int _female = 1;
	public static final Sex female = new Sex(_female);
	public int value()
	{
		return value;
	}
	public static Sex from_int(int value)
	{
		switch (value) {
			case _male: return male;
			case _female: return female;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected Sex(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
