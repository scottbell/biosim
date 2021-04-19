package com.traclabs.biosim.idl.framework;
/**
 * Generated from IDL enum "MalfunctionIntensity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MalfunctionIntensity
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
	public String toString()
	{
		switch (value) {
			case _SEVERE_MALF: return "SEVERE_MALF";
			case _MEDIUM_MALF: return "MEDIUM_MALF";
			case _LOW_MALF: return "LOW_MALF";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected MalfunctionIntensity(int i)
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
