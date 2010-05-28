package com.traclabs.biosim.idl.simulation.air;
/**
 *	Generated from IDL definition of enum "AirRSOperationMode"
 *	@author JacORB IDL compiler 
 */

public final class AirRSOperationMode
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _FULL = 0;
	public static final AirRSOperationMode FULL = new AirRSOperationMode(_FULL);
	public static final int _OFF = 1;
	public static final AirRSOperationMode OFF = new AirRSOperationMode(_OFF);
	public static final int _LESS = 2;
	public static final AirRSOperationMode LESS = new AirRSOperationMode(_LESS);
	public static final int _MOST = 3;
	public static final AirRSOperationMode MOST = new AirRSOperationMode(_MOST);
	public int value()
	{
		return value;
	}
	public static AirRSOperationMode from_int(int value)
	{
		switch (value) {
			case _FULL: return FULL;
			case _OFF: return OFF;
			case _LESS: return LESS;
			case _MOST: return MOST;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AirRSOperationMode(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
