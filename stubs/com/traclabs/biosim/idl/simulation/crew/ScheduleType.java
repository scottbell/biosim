package com.traclabs.biosim.idl.simulation.crew;
/**
 * Generated from IDL enum "ScheduleType".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class ScheduleType
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _ACTIVE_SCHED = 0;
	public static final ScheduleType ACTIVE_SCHED = new ScheduleType(_ACTIVE_SCHED);
	public static final int _PASSIVE_SCHED = 1;
	public static final ScheduleType PASSIVE_SCHED = new ScheduleType(_PASSIVE_SCHED);
	public static final int _VACATION_SCHED = 2;
	public static final ScheduleType VACATION_SCHED = new ScheduleType(_VACATION_SCHED);
	public int value()
	{
		return value;
	}
	public static ScheduleType from_int(int value)
	{
		switch (value) {
			case _ACTIVE_SCHED: return ACTIVE_SCHED;
			case _PASSIVE_SCHED: return PASSIVE_SCHED;
			case _VACATION_SCHED: return VACATION_SCHED;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _ACTIVE_SCHED: return "ACTIVE_SCHED";
			case _PASSIVE_SCHED: return "PASSIVE_SCHED";
			case _VACATION_SCHED: return "VACATION_SCHED";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected ScheduleType(int i)
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
