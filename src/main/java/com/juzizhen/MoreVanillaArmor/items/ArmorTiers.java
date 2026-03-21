package com.juzizhen.MoreVanillaArmor.items;

import com.juzizhen.MoreVanillaArmor.Config;
import com.juzizhen.MoreVanillaArmor.util.Bonus;
import com.juzizhen.MoreVanillaArmor.util.Bonuses;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum ArmorTiers implements ArmorMaterial {

    BONE(Config.DefaultMaterial.BONE, SoundEvents.ENTITY_SKELETON_AMBIENT, null, () -> Ingredient.ofItems(Items.BONE)),
    COAL(Config.DefaultMaterial.COAL, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, null, () -> Ingredient.ofItems(Items.COAL),  () -> Ingredient.fromTag(ItemTags.COALS)),
    COPPER(Config.DefaultMaterial.COPPER, SoundEvents.ITEM_ARMOR_EQUIP_IRON, Bonuses.LIGHTNING_MAGNET, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    EMERALD(Config.DefaultMaterial.EMERALD, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, null, () -> Ingredient.ofItems(Items.EMERALD)),
    ENDER(Config.DefaultMaterial.ENDER, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, null, () -> Ingredient.ofItems(Items.ENDER_PEARL), () -> Ingredient.ofItems(Items.END_STONE)),
    FIERY(Config.DefaultMaterial.FIERY, SoundEvents.ENTITY_BLAZE_SHOOT, Bonuses.FIRE_IMMUNITY, () -> Ingredient.ofItems(Items.MAGMA_BLOCK)),
    GLOWSTONE(Config.DefaultMaterial.GLOWSTONE, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, null, () -> Ingredient.ofItems(Items.GLOWSTONE_DUST), () -> Ingredient.ofItems(Items.GLOWSTONE)),
    LAPIS(Config.DefaultMaterial.LAPIS, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, null, () -> Ingredient.ofItems(Items.LAPIS_LAZULI), () -> Ingredient.ofItems(Items.LAPIS_BLOCK)),
    NETHER(Config.DefaultMaterial.NETHER, SoundEvents.BLOCK_LAVA_EXTINGUISH, null, () -> Ingredient.ofItems(Items.NETHER_BRICK)),
    OBSIDIAN(Config.DefaultMaterial.OBSIDIAN, SoundEvents.ENTITY_ENDER_EYE_DEATH, Bonuses.HEAVY, () -> Ingredient.ofItems(Items.OBSIDIAN)),
    PAPER(Config.DefaultMaterial.PAPER, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, null, () -> Ingredient.ofItems(Items.PAPER)),
    PRISMARINE(Config.DefaultMaterial.PRISMARINE, SoundEvents.AMBIENT_UNDERWATER_LOOP, null, () -> Ingredient.ofItems(Items.PRISMARINE_CRYSTALS), () -> Ingredient.ofItems(Items.PRISMARINE)),
    QUARTZ(Config.DefaultMaterial.QUARTZ, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, null, () -> Ingredient.ofItems(Items.QUARTZ), () -> Ingredient.ofItems(Items.QUARTZ_BLOCK)),
    REDSTONE(Config.DefaultMaterial.REDSTONE, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, Bonuses.POWER_SOURCE, () -> Ingredient.ofItems(Items.REDSTONE), () -> Ingredient.ofItems(Items.REDSTONE_BLOCK)),
    SLIME(Config.DefaultMaterial.SLIME, SoundEvents.BLOCK_SLIME_BLOCK_STEP, Bonuses.DAMAGE_REDUCTION, () -> Ingredient.ofItems(Items.SLIME_BALL), () -> Ingredient.ofItems(Items.SLIME_BLOCK)),
    STONE(Config.DefaultMaterial.STONE, SoundEvents.BLOCK_STONE_BREAK, null, () -> Ingredient.ofItems(Items.STONE)),
    WOOD(Config.DefaultMaterial.WOOD, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, null, () -> Ingredient.fromTag(ItemTags.LOGS));

    private static final int[] DURABILITY_ARRAY = new int[]{13, 15, 16, 11};
    private final Config.DefaultMaterial material;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;
    private final Supplier<Ingredient> ingredient;
    private final Bonus bonus;

    ArmorTiers(Config.DefaultMaterial material, SoundEvent sound, Bonus bonus, Supplier<Ingredient> repairMaterial, Supplier<Ingredient> ingredient) {
        this.material = material;
        this.soundEvent = sound;
        this.bonus = bonus;
        this.repairMaterial = repairMaterial;
        this.ingredient = ingredient;
    }

    ArmorTiers(Config.DefaultMaterial material, SoundEvent sound, Bonus bonus, Supplier<Ingredient> repairMaterial) {
        this.material = material;
        this.soundEvent = sound;
        this.bonus = bonus;
        this.repairMaterial = repairMaterial;
        this.ingredient = repairMaterial;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return DURABILITY_ARRAY[type.ordinal()] * this.material.getDurabilityFactor();
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.material.getDamageReduction()[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantmentValue();
    }

    @Override public SoundEvent getEquipSound() { return this.soundEvent; }
    @Override public Ingredient getRepairIngredient() { return this.repairMaterial.get(); }
    @Override public String getName() { return this.material.getName(); }
    @Override public float getToughness() { return this.material.getToughness(); }
    @Override public float getKnockbackResistance() { return this.material.getKnockbackResistance(); }

    public Bonus getBonus() { return this.bonus; }
}
