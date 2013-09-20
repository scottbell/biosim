package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL interface "Malfunction"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
