package fr.matt1999rd.modjam.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;


public class RopeItem extends Item {
    public RopeItem() {
        super(new Item.Properties().group(ItemGroup.TOOLS));
        this.setRegistryName("rope");
    }
}
