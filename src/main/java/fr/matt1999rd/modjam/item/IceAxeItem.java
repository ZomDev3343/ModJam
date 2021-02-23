package fr.matt1999rd.modjam.item;

import fr.matt1999rd.modjam.action.ClimbingAction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class IceAxeItem extends Item {
    ClimbingAction action;
    public IceAxeItem() {
        super(new Item.Properties().maxDamage(64).group(ItemGroup.TOOLS));
        this.setRegistryName("ice_axe");
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null)return ActionResultType.FAIL;
        World world = context.getWorld();
        ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
        if (stack.getItem() == ModItems.ROPE.asItem()){
            if (!world.isRemote){
                Vec3d playerPos = player.getPositionVec();
                double distance = context.getPos().distanceSq(playerPos.x,context.getPos().getY(),playerPos.z,true);
                if (distance>1.0D)return ActionResultType.FAIL;
                if (action == null){
                    action = new ClimbingAction(player,world);
                }
                action.raisePlayer();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
