package fr.matt1999rd.modjam;

import com.google.common.collect.Lists;
import fr.matt1999rd.modjam.action.ClimbingAction;
import fr.matt1999rd.modjam.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerFallHandler {
    public static List<ClimbingAction> actions = Lists.newArrayList();

    public PlayerFallHandler(){  }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent evt){
        AtomicReference<ClimbingAction> actionInCase = new AtomicReference<>(null);
        if (evt.getEntityLiving() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)evt.getEntityLiving();
            actions.forEach(climbingAction -> {
                if (climbingAction.getPlayer().isEntityEqual(player)){
                    actionInCase.set(climbingAction);
                }
            });
            ClimbingAction cAction = actionInCase.get();
            if (cAction == null){
                ClimbingAction action = new ClimbingAction(player,player.world);
                actions.add(action);
                return;
            }
            double numberOfRope = getAltitude(player)+evt.getDistance();
            if (numberOfRope<3){
                return;
            }
            ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);
            if (stack.getItem() == ModItems.ROPE.asItem()){
                if (numberOfRope<stack.getCount()) {
                    evt.setDamageMultiplier(0.0F);
                }
            }
        }
    }

    private int getAltitude(PlayerEntity playerEntity) {
        int x=0;
        World world = Minecraft.getInstance().world;
        BlockPos pos = playerEntity.getPosition();
        BlockState state = world.getBlockState(pos);
        while (ClimbingAction.isLikeAir(state)){
            x++;
            state = world.getBlockState(pos.down(x));
        }
        return x;
    }
}
