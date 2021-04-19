package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSDayNightState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CDRSDayNightState
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _day = 0;
	public static final CDRSDayNightState day = new CDRSDayNightState(_day);
	public static final int _night = 1;
	public static final CDRSDayNightState night = new CDRSDayNightState(_night);
	public int value()
	{
		return value;
	}
	public static CDRSDayNightState from_int(int value)
	{
		switch (value) {
			case _day: return day;
			case _night: return night;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _day: return "day";
			case _night: return "night";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSDayNightState(int i)
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
