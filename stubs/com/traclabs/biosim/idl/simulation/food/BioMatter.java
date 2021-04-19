package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL struct "BioMatter".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BioMatter
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public BioMatter(){}
	public float mass;
	public float inedibleFraction;
	public float edibleWaterContent;
	public float inedibleWaterContent;
	public com.traclabs.biosim.idl.simulation.food.PlantType type;
	public BioMatter(float mass, float inedibleFraction, float edibleWaterContent, float inedibleWaterContent, com.traclabs.biosim.idl.simulation.food.PlantType type)
	{
		this.mass = mass;
		this.inedibleFraction = inedibleFraction;
		this.edibleWaterContent = edibleWaterContent;
		this.inedibleWaterContent = inedibleWaterContent;
		this.type = type;
	}
}
