package com.juzizhen.MoreVanillaArmorFabric.util;


import com.juzizhen.MoreVanillaArmorFabric.items.Armor;
import com.juzizhen.MoreVanillaArmorFabric.items.ArmorTiers;
import com.juzizhen.MoreVanillaArmorFabric.morevanillaarmorfabric;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class Bonus {

    private final Text component;

    public Bonus(String key) {
        this.component = Text.translatable(morevanillaarmorfabric.MODID + ".bonus." + key);
    }

    public Text getDisplayName() {
        return this.component;
    }

    public static boolean isFullSet(PlayerEntity player, ArmorTiers type) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);

        return head.getItem() instanceof Armor armorHead && armorHead.getArmorType() == type
                && chest.getItem() instanceof Armor armorChest && armorChest.getArmorType() == type
                && legs.getItem() instanceof Armor armorLegs && armorLegs.getArmorType() == type
                && feet.getItem() instanceof Armor armorFeet && armorFeet.getArmorType() == type;
    }
}
