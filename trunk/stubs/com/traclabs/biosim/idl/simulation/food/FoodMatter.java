package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of struct "FoodMatter"
 *	@author JacORB IDL compiler 
 */

public final class FoodMatter
	implements org.omg.CORBA.portable.IDLEntity
{
	public FoodMatter(){}
	public float mass;
	public float waterContent;
	public com.traclabs.biosim.idl.simulation.food.PlantType type;
	public FoodMatter(float mass, float waterContent, com.traclabs.biosim.idl.simulation.food.PlantType type)
	{
		this.mass = mass;
		this.waterContent = waterContent;
		this.type = type;
	}
}
