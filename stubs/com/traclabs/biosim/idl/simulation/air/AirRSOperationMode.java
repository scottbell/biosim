package com.traclabs.biosim.idl.simulation.air;
/**
 * Generated from IDL enum "AirRSOperationMode".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirRSOperationMode
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
	public String toString()
	{
		switch (value) {
			case _FULL: return "FULL";
			case _OFF: return "OFF";
			case _LESS: return "LESS";
			case _MOST: return "MOST";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected AirRSOperationMode(int i)
	{
		value = i;
	}
	/**
	 * Designate replacement object when deserialized from stream. See
	 * http://www.omg.org/docs/ptc/02-01-03.htm#Issue4271
	 *
	 * @throws java.io.ObjectStreamException
	 */
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
