package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSArmedStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CDRSArmedStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _armed = 0;
	public static final CDRSArmedStatus armed = new CDRSArmedStatus(_armed);
	public static final int _in_progress = 1;
	public static final CDRSArmedStatus in_progress = new CDRSArmedStatus(_in_progress);
	public static final int _not_armed = 2;
	public static final CDRSArmedStatus not_armed = new CDRSArmedStatus(_not_armed);
	public int value()
	{
		return value;
	}
	public static CDRSArmedStatus from_int(int value)
	{
		switch (value) {
			case _armed: return armed;
			case _in_progress: return in_progress;
			case _not_armed: return not_armed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _armed: return "armed";
			case _in_progress: return "in_progress";
			case _not_armed: return "not_armed";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSArmedStatus(int i)
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
