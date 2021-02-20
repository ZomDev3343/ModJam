package fr.matt1999rd.modjam;

import fr.matt1999rd.modjam.action.ClimbingAction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {

    private PlayerEventHandler(){}
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerFalling(LivingFallEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity){
            PlayerEntity playerFalling = (PlayerEntity)living;
            boolean hasFoundPlayer = false;
            for (ClimbingAction action : ClimbingAction.actionInProcess){
                if (action.getPlayer().isEntityEqual(playerFalling)){
                    hasFoundPlayer = true;
                    action.fallTick();
                }
            }
            if (!hasFoundPlayer && !ClimbingAction.actionInProcess.isEmpty()){
                ModJam.LOGGER.warn("Climbing action has not found player for action !");
            }
            System.out.println("player is falling: "+playerFalling);
        }

    }
}
