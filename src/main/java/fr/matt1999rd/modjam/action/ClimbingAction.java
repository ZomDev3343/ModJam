package fr.matt1999rd.modjam.action;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ClimbingAction {
    PlayerEntity player;
    public static List<ClimbingAction> actionInProcess = Lists.newArrayList();
    World world;
    int numberOfRope;
    boolean isClimbing;
    public ClimbingAction(PlayerEntity player,World world){
        this.player = player;
        this.world = world;
        ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
        numberOfRope = stack.getCount();
        isClimbing = false;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void onMovePlayer(boolean raiseBody){
        if (raiseBody){
            numberOfRope--;
            ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
            stack.shrink(1);
            player.setItemStackToSlot(EquipmentSlotType.OFFHAND,stack);
        }else {
            numberOfRope++;
            ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
            stack.grow(1);
            player.setItemStackToSlot(EquipmentSlotType.OFFHAND,stack);
        }
    }

    public boolean raisePlayer(){
        Direction facing = player.getHorizontalFacing();
        BlockPos overWallPos = new BlockPos(MathHelper.floor(player.posX)+facing.getXOffset(),MathHelper.floor(player.posY)+2,MathHelper.floor(player.posZ)+facing.getZOffset());
        BlockState state = world.getBlockState(overWallPos);
        Vec2f rot =player.getPitchYaw();
        if (isLikeAir(state)){
            player.moveToBlockPosAndAngles(overWallPos.up(2),rot.y,rot.x);
            isClimbing = false;
            return true;
        } else if ( (state.getMaterial() == Material.ROCK ||state.getMaterial() == Material.EARTH) && state.isSolid()){
            player.moveToBlockPosAndAngles(player.getPosition().up(2),rot.y,rot.x);
            Vec3d vec3d = player.getMotion();
            //player.setMotion(vec3d.x,0.0D,vec3d.z);
            actionInProcess.add(this);
            isClimbing = true;
            return true;
        }
        return false;
    }


    public void fallTick() {
        if (isClimbing){
            System.out.println("fall Tick");
            Vec3d vec3d = player.getMotion();
            if (vec3d.y<0) {
                player.setMotion(vec3d.x,0.0D,vec3d.z);
                Vec2f rot =player.getPitchYaw();
                player.moveToBlockPosAndAngles(new BlockPos(player.prevPosX,player.prevPosY,player.prevPosZ),rot.y,rot.x);
            }
        }
    }

    private boolean isLikeAir(BlockState state){
        if (state == Blocks.AIR.getDefaultState())return true;
        return !state.getMaterial().blocksMovement();
    }
}
