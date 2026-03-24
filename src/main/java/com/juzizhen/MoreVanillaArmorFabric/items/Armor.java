package com.juzizhen.MoreVanillaArmorFabric.items;

import com.juzizhen.MoreVanillaArmorFabric.morevanillaarmorfabric;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Armor extends ArmorItem {
    private final ArmorTiers armorType;
    private final EquipmentSlot slotType;

    public Armor(ArmorTiers armorType, Type type) {
        super(armorType, type, new Item.Settings());
        this.armorType = armorType;
        this.slotType = getSlotFromType(type);
    }

    public static boolean entityIsWearingArmorSetOfType(LivingEntity entity, ArmorTiers type) {
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot slot : slots) {
            ItemStack stack = entity.getEquippedStack(slot);
            if (stack.isEmpty()
                    || !(stack.getItem() instanceof Armor armor)
                    || armor.getArmorType() != type) {
                return false;
            }
        }
        return true;
    }

    public static ArmorTiers getArmorSetType(LivingEntity entity) {
        ItemStack helmetStack = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (helmetStack.getItem() instanceof Armor armorHelmet) {
            ArmorTiers type = armorHelmet.getArmorType();

            EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
            for (EquipmentSlot slot : slots) {
                ItemStack stack = entity.getEquippedStack(slot);
                if (!(stack.getItem() instanceof Armor armorPiece) || armorPiece.getArmorType() != type) {
                    return null;
                }
            }

            return type;
        }

        return null;
    }


    public ArmorTiers getArmorType() {
        return this.armorType;
    }

    public EquipmentSlot getSlotType() {
        return this.slotType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, java.util.List<Text> tooltip, TooltipContext context) {
        if (world != null && world.isClient && MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            ArmorTiers fullSetType;
            fullSetType = getArmorSetType(player);
            Formatting color = (fullSetType == this.armorType) ? Formatting.GREEN : Formatting.DARK_GRAY;

            if (this.armorType.getBonus() != null) {
                Text bonusText = Text.translatable("tooltip." + morevanillaarmorfabric.MODID + ".setbonus")
                        .append(this.armorType.getBonus().getDisplayName())
                        .formatted(color);
                tooltip.add(bonusText);
            }
        }
    }

    public static EquipmentSlot getSlotFromType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> EquipmentSlot.HEAD;
            case CHESTPLATE -> EquipmentSlot.CHEST;
            case LEGGINGS -> EquipmentSlot.LEGS;
            case BOOTS -> EquipmentSlot.FEET;
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity player && isFullSet(player, this.armorType)) {
            applySetBonus(player);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void applySetBonus(PlayerEntity player) {
        ArmorTiers type = this.armorType;
        if (type == ArmorTiers.FIERY) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 219, 0, true, false));
        } else if (type == ArmorTiers.SLIME) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 219, 0, true, false));
        } else if (type == ArmorTiers.OBSIDIAN) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 219, 0, true, false));
        }
    }

    private boolean isFullSet(PlayerEntity player, ArmorTiers type) {
        return entityIsWearingArmorSetOfType(player, type);
    }
}
