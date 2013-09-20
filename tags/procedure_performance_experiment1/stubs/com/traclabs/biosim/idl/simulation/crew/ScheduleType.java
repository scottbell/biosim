package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "ScheduleType"
 *	@author JacORB IDL compiler 
 */

public final class ScheduleType
	implements org.omg.CORBA.portable.IDLEntity
{
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
	protected ScheduleType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
