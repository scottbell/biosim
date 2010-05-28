package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of struct "BioMatter"
 *	@author JacORB IDL compiler 
 */

public final class BioMatter
	implements org.omg.CORBA.portable.IDLEntity
{
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
