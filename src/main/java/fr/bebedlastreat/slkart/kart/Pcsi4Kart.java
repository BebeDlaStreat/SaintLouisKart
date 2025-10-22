package fr.bebedlastreat.slkart.kart;

public class Pcsi4Kart extends Kart{
    @Override
    public String getName() {
        return "11/2";
    }

    @Override
    public String getIconPath() {
        return "/animals/black_sheep.png";
    }

    @Override
    public int getSpeed() {
        return 75;
    }

    @Override
    public int getAcceleration() {
        return 25;
    }

    @Override
    public int getRotationSpeed() {
        return 100;
    }
}
