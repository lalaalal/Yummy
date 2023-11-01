package com.lalaalal.yummy.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public class PurifiedSoulArmor extends ArmorItem {

    public PurifiedSoulArmor(Type pType, Properties pProperties) {

        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 45;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{5, 8, 10, 5}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 20;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_netherite"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.NETHER_STAR));
            }

            @Override
            public String getName() {
                return "starArmor";
            }

            @Override
            public float getToughness() {
                return 6f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.2f;
            }
        }, pType, pProperties);
    }

    public static class Helmet extends PurifiedSoulArmor {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }
    }

    public static class Chestplate extends PurifiedSoulArmor {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }
    }

    public static class Leggings extends PurifiedSoulArmor {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_2.png";
        }
    }

    public static class Boots extends PurifiedSoulArmor {
        public Boots() {
            super(ArmorItem.Type.BOOTS, new Item.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }
    }


}
