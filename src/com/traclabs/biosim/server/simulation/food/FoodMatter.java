package com.traclabs.biosim.server.simulation.food;

public final class FoodMatter
{
    public FoodMatter(){}
    public float mass;
    public float waterContent;
    public PlantType type;
    public FoodMatter(float mass, float waterContent, PlantType type)
    {
        this.mass = mass;
        this.waterContent = waterContent;
        this.type = type;
    }
}
