package me.aylias.justenough.cakez;

import com.mojang.logging.LogUtils;
import me.aylias.justenough.cakez.blocks.CustomCakeBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static me.aylias.justenough.cakez.JustEnoughCakes.MODID;


public class JECRegistry {

    private static int cakes = 0;

    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "justenoughcakes" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "justenoughcakes" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final MobEffect[] mobEffects = new MobEffect[]{MobEffects.ABSORPTION, MobEffects.BAD_OMEN, MobEffects.BLINDNESS, MobEffects.CONFUSION, MobEffects.CONDUIT_POWER, MobEffects.DAMAGE_BOOST, MobEffects.DAMAGE_RESISTANCE, MobEffects.DARKNESS, MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED, MobEffects.DOLPHINS_GRACE, MobEffects.FIRE_RESISTANCE, MobEffects.GLOWING, MobEffects.HARM, MobEffects.HEAL, MobEffects.HEALTH_BOOST, MobEffects.HERO_OF_THE_VILLAGE, MobEffects.HUNGER, MobEffects.INVISIBILITY, MobEffects.JUMP, MobEffects.LEVITATION, MobEffects.LUCK, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED, MobEffects.NIGHT_VISION, MobEffects.POISON, MobEffects.REGENERATION, MobEffects.SATURATION, MobEffects.SLOW_FALLING, MobEffects.UNLUCK, MobEffects.WATER_BREATHING, MobEffects.WEAKNESS, MobEffects.WITHER};

    public static final String[] colors = new String[]{
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black"
    };

    public static List<RegistryObject<? extends Item>> registeredCakes = new ArrayList<>();

    private static void registerCake(String name) {
        cakes++;
        String id = name + "_cake";
        var block = BLOCKS.register(id, () -> new CustomCakeBlock(BlockBehaviour.Properties.of()));
        var item = ITEMS.register(id, () -> new BlockItem((block.get()), new Item.Properties()));
        registeredCakes.add(item);
        registerPotionCakes(id);
        registerCandleCakes(id);

        for (var color :
                colors) {
            var colorId = color + "_" + id;
            var block1 = BLOCKS.register(colorId, () -> new CustomCakeBlock(BlockBehaviour.Properties.of()));
            var item1 = ITEMS.register(colorId, () -> new BlockItem((block1.get()), new Item.Properties()));
            registerPotionCakes(colorId);
            registerCandleCakes(colorId);
            registeredCakes.add(item1);
            cakes++;
        }
    }

    private static void registerPotionCakes(String id) {
        for (var effect :
                mobEffects) {
            var effectId = effect.getDescriptionId() + "_" + id;
            var registered = BLOCKS.register(effectId, () -> new CustomCakeBlock(BlockBehaviour.Properties.of()));
            var item = ITEMS.register(effectId, () -> new BlockItem((registered.get()), new Item.Properties()));
            registerCandleCakes(effectId);
            cakes++;
            registeredCakes.add(item);
        }
    }

    private static void registerCandleCakes(String id) {
        for (var color :
                colors) {
            var colorId = color + "_candle_" + id;
            var block = BLOCKS.register(colorId, () -> new CustomCakeBlock(BlockBehaviour.Properties.of()));
            var item = ITEMS.register(colorId, () -> new BlockItem((block.get()), new Item.Properties()));
            registeredCakes.add(item);
            cakes++;
        }
    }

    // Creates a new Block with the id "justenoughcakes:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("custom_cake", () -> new CustomCakeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "justenoughcakes:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("custom_cake", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                registeredCakes.forEach(item -> output.accept(item.get())); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            })
            .withSearchBar()
            .build());

    static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(JECRegistry::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(JECRegistry::addCreative);
        registerCake("basic");
        LOGGER.warn("registered this many cakes: " + cakes);

//        var path = "E:\\IntelliJProjects\\JustEnoughCakes\\cakes.txt";
//        try {
//            FileWriter myWriter = new FileWriter(path);
//            StringBuilder builder = new StringBuilder();
//            for (var item :
//                    registeredCakes) {
//                builder.append(item.getId().toString().replace("justenoughcakes:", "")).append(";");
//            }
//            myWriter.write(builder.toString());
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
    }


    private static void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code

    }

    // Add the example block item to the building blocks tab
    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }
}
