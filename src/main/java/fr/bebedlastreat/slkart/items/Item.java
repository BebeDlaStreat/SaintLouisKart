package fr.bebedlastreat.slkart.items;

import fr.bebedlastreat.slkart.content.KartImage;
import lombok.Getter;

@Getter
public enum Item {
    SHOES(KartImage.ITEM_SHOES),
    BOMB(KartImage.ITEM_BOMB),
    SHIELD(KartImage.ITEM_SHIELD),
    FLAME(KartImage.ITEM_FLAME);

    private final int image;

    Item(int image) {
        this.image = image;
    }
}
