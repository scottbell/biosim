package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL struct "FoodMatter".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodMatter
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
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
