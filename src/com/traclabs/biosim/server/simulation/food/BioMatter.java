package com.traclabs.biosim.server.simulation.food;

public final class BioMatter {
    public float mass;
    public float inedibleFraction;
    public float edibleWaterContent;
    public float inedibleWaterContent;
    public PlantType type;

    public BioMatter() {
    }

    public BioMatter(float mass, float inedibleFraction, float edibleWaterContent, float inedibleWaterContent, PlantType type) {
        this.mass = mass;
        this.inedibleFraction = inedibleFraction;
        this.edibleWaterContent = edibleWaterContent;
        this.inedibleWaterContent = inedibleWaterContent;
        this.type = type;
    }
}
