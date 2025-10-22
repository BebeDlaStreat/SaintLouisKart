package fr.bebedlastreat.slkart.kart;

public class Mp2iKart extends Kart{
    @Override
    public String getName() {
        return "Stacky";
    }

    @Override
    public String getIconPath() {
        return "/animals/mammoth.png";
    }

    @Override
    public int getSpeed() {
        return 100;
    }

    @Override
    public int getAcceleration() {
        return 50;
    }

    @Override
    public int getRotationSpeed() {
        return 50;
    }
}
