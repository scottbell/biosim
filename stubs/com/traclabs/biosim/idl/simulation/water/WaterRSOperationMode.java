package com.traclabs.biosim.idl.simulation.water;
/**
 * Generated from IDL enum "WaterRSOperationMode".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterRSOperationMode
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _FULL = 0;
	public static final WaterRSOperationMode FULL = new WaterRSOperationMode(_FULL);
	public static final int _OFF = 1;
	public static final WaterRSOperationMode OFF = new WaterRSOperationMode(_OFF);
	public static final int _GREY_WATER_ONLY = 2;
	public static final WaterRSOperationMode GREY_WATER_ONLY = new WaterRSOperationMode(_GREY_WATER_ONLY);
	public static final int _PARTIAL = 3;
	public static final WaterRSOperationMode PARTIAL = new WaterRSOperationMode(_PARTIAL);
	public int value()
	{
		return value;
	}
	public static WaterRSOperationMode from_int(int value)
	{
		switch (value) {
			case _FULL: return FULL;
			case _OFF: return OFF;
			case _GREY_WATER_ONLY: return GREY_WATER_ONLY;
			case _PARTIAL: return PARTIAL;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _FULL: return "FULL";
			case _OFF: return "OFF";
			case _GREY_WATER_ONLY: return "GREY_WATER_ONLY";
			case _PARTIAL: return "PARTIAL";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected WaterRSOperationMode(int i)
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
