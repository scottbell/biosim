package com.traclabs.biosim.idl.framework;


/**
 * Generated from IDL interface "Malfunction".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface MalfunctionOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.framework.MalfunctionIntensity getIntensity();
	long getID();
	java.lang.String getName();
	boolean hasPerformed();
	void setPerformed(boolean pPerformed);
	com.traclabs.biosim.idl.framework.MalfunctionLength getLength();
	void doSomeRepairWork();
	boolean doneEnoughRepairWork();
}
