package fr.bebedlastreat.slkart.kart;

public class Bio2Kart extends Kart{
    @Override
    public String getName() {
        return "Hippopotame";
    }

    @Override
    public String getIconPath() {
        return "/animals/hippopotamus.png";
    }

    @Override
    public int getSpeed() {
        return 100;
    }

    @Override
    public int getAcceleration() {
        return 25;
    }

    @Override
    public int getRotationSpeed() {
        return 75;
    }
}
