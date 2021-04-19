package com.traclabs.biosim.idl.framework;
/**
 * Generated from IDL enum "MalfunctionLength".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MalfunctionLength
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
	public String toString()
	{
		switch (value) {
			case _TEMPORARY_MALF: return "TEMPORARY_MALF";
			case _PERMANENT_MALF: return "PERMANENT_MALF";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected MalfunctionLength(int i)
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
