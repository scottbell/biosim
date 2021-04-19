package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "PPAPumpSpeedStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PPAPumpSpeedStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _pumpArmed = 0;
	public static final PPAPumpSpeedStatus pumpArmed = new PPAPumpSpeedStatus(_pumpArmed);
	public static final int _notArmed = 1;
	public static final PPAPumpSpeedStatus notArmed = new PPAPumpSpeedStatus(_notArmed);
	public int value()
	{
		return value;
	}
	public static PPAPumpSpeedStatus from_int(int value)
	{
		switch (value) {
			case _pumpArmed: return pumpArmed;
			case _notArmed: return notArmed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	public String toString()
	{
		switch (value) {
			case _pumpArmed: return "pumpArmed";
			case _notArmed: return "notArmed";
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected PPAPumpSpeedStatus(int i)
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
