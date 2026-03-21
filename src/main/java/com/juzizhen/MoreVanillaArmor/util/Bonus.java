package com.juzizhen.MoreVanillaArmor.util;


import com.juzizhen.MoreVanillaArmor.MoreVanillaArmor;
import net.minecraft.text.Text;

public class Bonus {

    private final Text component;

    public Bonus(String key) {
        this.component = Text.translatable(MoreVanillaArmor.MODID + ".bonus." + key);
    }

    public Text getDisplayName() {
        return this.component;
    }
}
