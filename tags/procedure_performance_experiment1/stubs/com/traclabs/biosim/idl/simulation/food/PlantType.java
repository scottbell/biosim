package com.traclabs.biosim.idl.simulation.food;
/**
 *	Generated from IDL definition of enum "PlantType"
 *	@author JacORB IDL compiler 
 */

public final class PlantType
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _WHEAT = 0;
	public static final PlantType WHEAT = new PlantType(_WHEAT);
	public static final int _DRY_BEAN = 1;
	public static final PlantType DRY_BEAN = new PlantType(_DRY_BEAN);
	public static final int _LETTUCE = 2;
	public static final PlantType LETTUCE = new PlantType(_LETTUCE);
	public static final int _PEANUT = 3;
	public static final PlantType PEANUT = new PlantType(_PEANUT);
	public static final int _RICE = 4;
	public static final PlantType RICE = new PlantType(_RICE);
	public static final int _SOYBEAN = 5;
	public static final PlantType SOYBEAN = new PlantType(_SOYBEAN);
	public static final int _SWEET_POTATO = 6;
	public static final PlantType SWEET_POTATO = new PlantType(_SWEET_POTATO);
	public static final int _TOMATO = 7;
	public static final PlantType TOMATO = new PlantType(_TOMATO);
	public static final int _WHITE_POTATO = 8;
	public static final PlantType WHITE_POTATO = new PlantType(_WHITE_POTATO);
	public static final int _UNKNOWN_PLANT = 9;
	public static final PlantType UNKNOWN_PLANT = new PlantType(_UNKNOWN_PLANT);
	public int value()
	{
		return value;
	}
	public static PlantType from_int(int value)
	{
		switch (value) {
			case _WHEAT: return WHEAT;
			case _DRY_BEAN: return DRY_BEAN;
			case _LETTUCE: return LETTUCE;
			case _PEANUT: return PEANUT;
			case _RICE: return RICE;
			case _SOYBEAN: return SOYBEAN;
			case _SWEET_POTATO: return SWEET_POTATO;
			case _TOMATO: return TOMATO;
			case _WHITE_POTATO: return WHITE_POTATO;
			case _UNKNOWN_PLANT: return UNKNOWN_PLANT;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected PlantType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
