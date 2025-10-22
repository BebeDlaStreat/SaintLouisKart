package fr.bebedlastreat.slkart.kart;

public class Mpsi4Kart extends Kart{
    @Override
    public String getName() {
        return "Wombat";
    }

    @Override
    public String getIconPath() {
        return "/animals/wombat.png";
    }

    @Override
    public int getSpeed() {
        return 75;
    }

    @Override
    public int getAcceleration() {
        return 50;
    }

    @Override
    public int getRotationSpeed() {
        return 75;
    }
}
