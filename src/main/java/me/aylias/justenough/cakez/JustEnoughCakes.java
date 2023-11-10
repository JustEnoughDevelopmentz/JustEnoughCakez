package me.aylias.justenough;

import com.mojang.logging.LogUtils;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JustEnoughCakes.MODID)
public class JustEnoughCakes {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "justenoughcakez";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ItemColor itemColor = (itemStack, p_92673_) -> {
        if (itemStack.getItem().getDescriptionId().contains("white_basic"))
            return 0xffffff;
        else if (itemStack.getItem().getDescriptionId().contains("orange_basic"))
            return 0xff7700;
        else if (itemStack.getItem().getDescriptionId().contains("magenta_basic"))
            return 0xF700F7;
        else if (itemStack.getItem().getDescriptionId().contains("light_blue_basic"))
            return 0x00a2ff;
        else if (itemStack.getItem().getDescriptionId().contains("yellow_basic"))
            return 0xeeff00;
        else if (itemStack.getItem().getDescriptionId().contains("lime_basic"))
            return 0xeeff00;
        else if (itemStack.getItem().getDescriptionId().contains("pink_basic"))
            return 0xff0099;
        else if (itemStack.getItem().getDescriptionId().contains("light_gray_basic"))
            return 0xadadad;
        else if (itemStack.getItem().getDescriptionId().contains("gray_basic"))
            return 0x707070;
        else if (itemStack.getItem().getDescriptionId().contains("cyan_basic"))
            return 0x00ffee;
        else if (itemStack.getItem().getDescriptionId().contains("purple_basic"))
            return 0xae00ff;
        else if (itemStack.getItem().getDescriptionId().contains("blue_basic"))
            return 0x002fff;
        else if (itemStack.getItem().getDescriptionId().contains("brown_basic"))
            return 0x75502b;
        else if (itemStack.getItem().getDescriptionId().contains("green_basic"))
            return 0x2b702d;
        else if (itemStack.getItem().getDescriptionId().contains("red_basic"))
            return 0xe84b3c;
        else if (itemStack.getItem().getDescriptionId().contains("black_basic"))
            return 0x000;
        return 0xffffff;
    };

    public static BlockColor blockColor = (block, p_92568_, p_92569_, p_92570_) -> {
        if (block.getBlock().getDescriptionId().contains("white_basic"))
            return 0xffffff;
        else if (block.getBlock().getDescriptionId().contains("orange_basic"))
            return 0xff7700;
        else if (block.getBlock().getDescriptionId().contains("magenta_basic"))
            return 0xF700F7;
        else if (block.getBlock().getDescriptionId().contains("light_blue_basic"))
            return 0x00a2ff;
        else if (block.getBlock().getDescriptionId().contains("yellow_basic"))
            return 0xeeff00;
        else if (block.getBlock().getDescriptionId().contains("lime_basic"))
            return 0xeeff00;
        else if (block.getBlock().getDescriptionId().contains("pink_basic"))
            return 0xff0099;
        else if (block.getBlock().getDescriptionId().contains("light_gray_basic"))
            return 0xadadad;
        else if (block.getBlock().getDescriptionId().contains("gray_basic"))
            return 0x707070;
        else if (block.getBlock().getDescriptionId().contains("cyan_basic"))
            return 0x00ffee;
        else if (block.getBlock().getDescriptionId().contains("purple_basic"))
            return 0xae00ff;
        else if (block.getBlock().getDescriptionId().contains("blue_basic"))
            return 0x002fff;
        else if (block.getBlock().getDescriptionId().contains("brown_basic"))
            return 0x75502b;
        else if (block.getBlock().getDescriptionId().contains("green_basic"))
            return 0x2b702d;
        else if (block.getBlock().getDescriptionId().contains("red_basic"))
            return 0xe84b3c;
        else if (block.getBlock().getDescriptionId().contains("black_basic"))
            return 0x000;
        return 0xffffff;
    };

    public JustEnoughCakes() {
        JECRegistry.init();

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            
        }

        @SubscribeEvent
        public static void colorHandler(RegisterColorHandlersEvent.Item event) {
            List<ItemLike> itemLikes = new ArrayList<>();
            JECRegistry.registeredCakes.forEach(item -> itemLikes.add(item.get()));

            event.register(itemColor, itemLikes.toArray(ItemLike[]::new));
            LOGGER.info("Testing if the colors get registered");
        }
    }



}
