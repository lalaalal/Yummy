package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEntities {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<EntityType<Herobrine>> HEROBRINE = ENTITY_TYPES.register("herobrine",
            () -> EntityType.Builder.of(Herobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":herobrine"));

    public static final RegistryObject<EntityType<ShadowHerobrine>> SHADOW_HEROBRINE = ENTITY_TYPES.register("shadow_herobrine",
            () -> EntityType.Builder.of(ShadowHerobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":shadow_herobrine"));

    public static final RegistryObject<EntityType<BunnyChest>> BUNNY_CHEST = ENTITY_TYPES.register("bunny_chest",
            () -> EntityType.Builder.of(BunnyChest::new, MobCategory.CREATURE)
                    .sized(1f, 1f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":bunny_chest"));

    public static final RegistryObject<EntityType<ThrownSpear>> SPEAR = ENTITY_TYPES.register("spear",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpear>) ThrownSpear::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(YummyMod.MOD_ID + ":spear"));
    public static final RegistryObject<EntityType<ThrownSpear>> MIGHTY_HOLY_SPEAR = ENTITY_TYPES.register("mighty_holy_spear",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpear>) (type, level) -> new ThrownSpear(YummyEntities.MIGHTY_HOLY_SPEAR.get(), level), MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(YummyMod.MOD_ID + ":mighty_holy_spear"));
    public static final RegistryObject<EntityType<ThrownSpearOfLonginus>> THROWN_SPEAR_OF_LONGINUS = ENTITY_TYPES.register("spear_of_longinus",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpearOfLonginus>) ThrownSpearOfLonginus::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":spear_of_longinus"));

    public static final RegistryObject<EntityType<MarkFireball>> MARK_FIREBALL = ENTITY_TYPES.register("mark_fireball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<MarkFireball>) MarkFireball::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(YummyMod.MOD_ID + ":mark_fireball"));

    public static final RegistryObject<EntityType<Meteor>> METEOR = ENTITY_TYPES.register("meteor",
            () -> EntityType.Builder.of((EntityType.EntityFactory<Meteor>) Meteor::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(YummyMod.MOD_ID + ":meteor"));

    public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK_ENTITY = ENTITY_TYPES.register("floating_block_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FloatingBlockEntity>) FloatingBlockEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build(YummyMod.MOD_ID + ":floating_block_entity"));

    public static final RegistryObject<EntityType<TransformingBlockEntity>> TRANSFORMING_BLOCK_ENTITY = ENTITY_TYPES.register("transforming_block_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<TransformingBlockEntity>) TransformingBlockEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build(YummyMod.MOD_ID + ":transforming_block_entity"));

    public static final RegistryObject<EntityType<NarakaMagicCircle>> NARAKA_MAGIC_CIRCLE = ENTITY_TYPES.register("naraka_magic_circle",
            () -> EntityType.Builder.of((EntityType.EntityFactory<NarakaMagicCircle>) NarakaMagicCircle::new, MobCategory.MISC)
                    .sized(1f, 0.1f)
                    .build(YummyMod.MOD_ID + ":naraka_magic_circle"));

    public static final RegistryObject<EntityType<EbonyBoat>> EBONY_BOAT = ENTITY_TYPES.register("ebony_boat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<EbonyBoat>) EbonyBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(YummyMod.MOD_ID + ":ebony_boat"));
    public static final RegistryObject<EntityType<EbonyChestBoat>> EBONY_CHEST_BOAT = ENTITY_TYPES.register("ebony_chest_boat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<EbonyChestBoat>) EbonyChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(YummyMod.MOD_ID + ":ebony_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
