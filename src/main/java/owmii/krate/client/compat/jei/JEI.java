package owmii.krate.client.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import owmii.krate.Krate;
import owmii.krate.item.Itms;

@JeiPlugin
public class JEI implements IModPlugin {
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(Itms.FILTER), VanillaTypes.ITEM, I18n.format("info.krate.filter"));
        registration.addIngredientInfo(new ItemStack(Itms.HOPPER_UPGRADE), VanillaTypes.ITEM, I18n.format("info.krate.hopper_upgrade"));
        registration.addIngredientInfo(new ItemStack(Itms.COLLECT_UPGRADE), VanillaTypes.ITEM, I18n.format("info.krate.collect_upgrade"));
        registration.addIngredientInfo(new ItemStack(Itms.COMPACT_UPGRADE), VanillaTypes.ITEM, I18n.format("info.krate.compact_upgrade"));
        registration.addIngredientInfo(new ItemStack(Itms.SHULKER_UPGRADE), VanillaTypes.ITEM, I18n.format("info.krate.shulker_upgrade"));
        registration.addIngredientInfo(new ItemStack(Itms.SMALL_TO_BASIC), VanillaTypes.ITEM, I18n.format("info.krate.small_to_basic"));
        registration.addIngredientInfo(new ItemStack(Itms.BASIC_TO_BIG), VanillaTypes.ITEM, I18n.format("info.krate.basic_to_big"));
        registration.addIngredientInfo(new ItemStack(Itms.BIG_TO_LARGE), VanillaTypes.ITEM, I18n.format("info.krate.big_to_large"));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Krate.MOD_ID, "main");
    }
}