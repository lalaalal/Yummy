package com.lalaalal.yummy.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ARMOR_SKILL = "key.category.yummy.armor_skill";
    public static final String KEY_STEEL_ARMOR_SKILL = "key.yummy.armor_skill.steel";

    public static final KeyMapping STEEL_ARMOR_KEY = new KeyMapping(KEY_STEEL_ARMOR_SKILL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_ARMOR_SKILL);
}
