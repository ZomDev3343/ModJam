package fr.matt1999rd.modjam;

import fr.matt1999rd.modjam.item.IceAxeItem;
import fr.matt1999rd.modjam.item.RopeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("modjam")
public class ModJam
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ModJam() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            // register a new item here
            itemRegistryEvent.getRegistry().register(new IceAxeItem());
            itemRegistryEvent.getRegistry().register(new RopeItem());
        }
    }
}
