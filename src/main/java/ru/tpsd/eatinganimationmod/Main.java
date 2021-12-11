package ru.tpsd.eatinganimationmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import ru.tpsd.eatinganimationmod.proxy.CommonProxy;

import java.util.ArrayList;
import java.util.Arrays;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main 
{
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) { }
	
	@EventHandler
	public static void init(FMLInitializationEvent event) { }
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) { }
	
	public static float a = 0;
    public static final ArrayList<Item> VANILLA_FOOD = new ArrayList<>(Arrays.asList(
            Items.APPLE, Items.BAKED_POTATO, Items.BEEF, Items.BEETROOT, Items.CARROT,
            Items.CHICKEN, Items.BREAD, Items.CHORUS_FRUIT, Items.COOKED_BEEF,
            Items.COOKED_CHICKEN, Items.COOKED_FISH, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT,
            Items.COOKIE, Items.GOLDEN_APPLE, Items.GOLDEN_CARROT,
            Items.MELON, Items.MILK_BUCKET, Items.MUSHROOM_STEW, Items.MUTTON, Items.POISONOUS_POTATO,
            Items.PORKCHOP, Items.POTATO, Items.PUMPKIN_PIE, Items.RABBIT, Items.RABBIT_STEW, Items.BEETROOT_SOUP,
            Items.ROTTEN_FLESH, Items.SPIDER_EYE
    ));

    public void onInitializeClient() {

        for (Item item : VANILLA_FOOD) {
            FabricModelPredicateProviderRegistry.register(item, new Identifier("eat"), (itemStack, clientWorld, livingEntity, i) -> {
                if (livingEntity == null) 
                {
                    return 0.0F;
                }

                if(livingEntity instanceof OtherClientPlayerEntity && livingEntity.getItemUseTime() > 31)
                {
                	return a / 30;
                }
                return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 30.0F;
            });

            FabricModelPredicateProviderRegistry.register(item, new Identifier("eating"), (itemStack, clientWorld, livingEntity, i) -> {
                if (livingEntity == null) 
                {
                    return 0.0F;
                }

                return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
            });
        }
    }
}
