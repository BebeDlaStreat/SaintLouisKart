package fr.bebedlastreat.slkart.kart;

public class EcgKart extends Kart{
    @Override
    public String getName() {
        return "Paon";
    }

    @Override
    public String getIconPath() {
        return "/animals/peacock.png";
    }

    @Override
    public int getSpeed() {
        return 25;
    }

    @Override
    public int getAcceleration() {
        return 100;
    }

    @Override
    public int getRotationSpeed() {
        return 75;
    }
}
