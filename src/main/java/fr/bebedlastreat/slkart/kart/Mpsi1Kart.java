package fr.bebedlastreat.slkart.kart;

public class Mpsi1Kart extends Kart{
    @Override
    public String getName() {
        return "Etienne";
    }

    @Override
    public String getIconPath() {
        return "/animals/sloth.png";
    }

    @Override
    public int getSpeed() {
        return 75;
    }

    @Override
    public int getAcceleration() {
        return 100;
    }

    @Override
    public int getRotationSpeed() {
        return 25;
    }
}
