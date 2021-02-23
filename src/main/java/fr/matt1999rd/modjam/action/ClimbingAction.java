package fr.matt1999rd.modjam.action;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;


public class ClimbingAction {
    PlayerEntity player;
    World world;

    public ClimbingAction(PlayerEntity player,World world){
        this.player = player;
        this.world = world;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public boolean onMovePlayer(int altitude){
        ItemStack stack = player.getHeldItem(Hand.OFF_HAND);
        int numberOfRope = stack.getCount();
        if (numberOfRope<altitude){
            return false;
        }
        stack.shrink(altitude);
        player.setItemStackToSlot(EquipmentSlotType.OFFHAND,stack);
        return true;
    }

    public void raisePlayer(){
        Direction facing = player.getHorizontalFacing();
        BlockPos overWallPos = new BlockPos(MathHelper.floor(player.posX)+facing.getXOffset(),MathHelper.floor(player.posY)+1,MathHelper.floor(player.posZ)+facing.getZOffset());
        BlockState state = world.getBlockState(overWallPos);
        Vec2f rot =player.getPitchYaw();
        int y=0;
        boolean hasOverHang = false;
        while (!isLikeAir(state) && y<64 && !hasOverHang){
            y++;
            state = world.getBlockState(overWallPos.up(y));
            hasOverHang = testOverHang(overWallPos.up(y));
        }
        if (y>=64 || hasOverHang)return;
        if (this.onMovePlayer(y)) {
            player.moveToBlockPosAndAngles(overWallPos.up(y + 1), rot.y, rot.x);
        }
    }

    private boolean testOverHang(BlockPos pos) {
        BlockPos overHangPos = new BlockPos(player.posX,pos.getY(),player.posZ);
        return (!isLikeAir(world.getBlockState(overHangPos)));
    }


    public static boolean isLikeAir(BlockState state){
        if (state == Blocks.AIR.getDefaultState())return true;
        return !state.getMaterial().blocksMovement();
    }
}
