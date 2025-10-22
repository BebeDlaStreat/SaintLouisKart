package fr.bebedlastreat.slkart.kart;

public class Pcsi1Kart extends Kart{
    @Override
    public String getName() {
        return "Crocodile";
    }

    @Override
    public String getIconPath() {
        return "/animals/crocodile.png";
    }

    @Override
    public int getSpeed() {
        return 200/3;
    }

    @Override
    public int getAcceleration() {
        return 200/3;
    }

    @Override
    public int getRotationSpeed() {
        return 200/3;
    }
}
