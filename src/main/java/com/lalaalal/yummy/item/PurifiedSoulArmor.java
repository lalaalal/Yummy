package com.lalaalal.yummy.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

public class PurifiedSoulArmor extends ArmorItem {

    @Override
    public void setDamage(ItemStack stack, int damage){
        super.setDamage(stack, 0);
    }
    public PurifiedSoulArmor(Type pType, Properties pProperties) {

        super(new ArmorMaterial() {

            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 50;
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
                return "PurifiedSoulArmor";
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
            super(ArmorItem.Type.HELMET, new ArmorItem.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }

    }

    public static class Chestplate extends PurifiedSoulArmor {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new ArmorItem.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }
    }

    public static class Leggings extends PurifiedSoulArmor {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new ArmorItem.Properties().fireResistant());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_2.png";
        }
    }

    public static class Boots extends PurifiedSoulArmor {
        public Boots() { super(ArmorItem.Type.BOOTS, new ArmorItem.Properties().fireResistant()); }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "yummy:textures/models/armor/purified_soul_layer_1.png";
        }
    }

    public static void FullSet(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (YummyItems.hasPurifiedSoulLeaseOne(player)) {
                if (YummyItems.hasPurifiedSoulFullSet(player)) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,5, 3, false, false));
                    if (!player.getAbilities().mayfly) {
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                    }
                } else{
                    if (player.isCreative() || player.isSpectator()){
                        if (!player.getAbilities().mayfly) {
                            player.getAbilities().mayfly = true;
                            player.onUpdateAbilities();
                        }
                    }
                    else{
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                    }
                }
            }
        }
    }


}
