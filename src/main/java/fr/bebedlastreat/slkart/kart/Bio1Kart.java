package fr.bebedlastreat.slkart.kart;

public class Bio1Kart extends Kart{
    @Override
    public String getName() {
        return "Singe";
    }

    @Override
    public String getIconPath() {
        return "/animals/monkey.png";
    }

    @Override
    public int getSpeed() {
        return 75;
    }

    @Override
    public int getAcceleration() {
        return 75;
    }

    @Override
    public int getRotationSpeed() {
        return 50;
    }
}
