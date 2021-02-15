package fr.matt1999rd.modjam.item;

import fr.matt1999rd.modjam.ModJam;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;


public class IceAxeItem extends Item {
    public IceAxeItem() {
        super(new Item.Properties().maxDamage(64).group(ItemGroup.TOOLS));
        this.setRegistryName("ice_axe");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer() == null)return ActionResultType.FAIL;
        ItemStack stack = context.getPlayer().getItemStackFromSlot(EquipmentSlotType.OFFHAND);
        if (stack.getItem() == ModItems.ROPE.asItem()){
            ModJam.LOGGER.info("use ice axe item !");
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
