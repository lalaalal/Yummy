package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEntities {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YummyMod.MOD_ID);
    

    public static final RegistryObject<EntityType<Herobrine>> HEROBRINE = ENTITY_TYPES.register("herobrine",
            () -> EntityType.Builder.of((EntityType.EntityFactory<Herobrine>) Herobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build("herobrine"));

    public static final RegistryObject<EntityType<ShadowHerobrine>> SHADOW_HEROBRINE = ENTITY_TYPES.register("shadow_herobrine",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ShadowHerobrine>) ShadowHerobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build("shadow_herobrine"));

    public static final RegistryObject<EntityType<BunnyChest>> BUNNY_CHEST = ENTITY_TYPES.register("bunny_chest",
            () -> EntityType.Builder.of((EntityType.EntityFactory<BunnyChest>) BunnyChest::new, MobCategory.CREATURE)
                    .sized(1f, 1f)
                    .fireImmune()
                    .build("bunny_chest"));

    public static final RegistryObject<EntityType<ThrownSpear>> SPEAR = ENTITY_TYPES.register("spear",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpear>) ThrownSpear::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build("spear"));
    public static final RegistryObject<EntityType<? extends ThrownSpear>> MIGHTY_HOLY_SPEAR = ENTITY_TYPES.register("mighty_holy_spear",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpear>) (type, level) -> new ThrownSpear(YummyEntities.MIGHTY_HOLY_SPEAR.get(), level), MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build("mighty_holy_spear"));
    public static final RegistryObject<EntityType<ThrownSpearOfLonginus>> SPEAR_OF_LONGINUS = ENTITY_TYPES.register("spear_of_longinus",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpearOfLonginus>) ThrownSpearOfLonginus::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .fireImmune()
                    .build("spear_of_longinus"));

    public static final RegistryObject<EntityType<MarkFireball>> MARK_FIREBALL = ENTITY_TYPES.register("mark_fireball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<MarkFireball>) MarkFireball::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build("mark_fireball"));

    public static final RegistryObject<EntityType<Meteor>> METEOR = ENTITY_TYPES.register("meteor",
            () -> EntityType.Builder.of((EntityType.EntityFactory<Meteor>) Meteor::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build("meteor"));

    public static final RegistryObject<EntityType<FloatingBlockEntity>> FLOATING_BLOCK_ENTITY = ENTITY_TYPES.register("floating_block_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FloatingBlockEntity>) FloatingBlockEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build("floating_block_entity"));

    public static final RegistryObject<EntityType<TransformingBlockEntity>> TRANSFORMING_BLOCK_ENTITY = ENTITY_TYPES.register("transforming_block_entity",
            () -> EntityType.Builder.of((EntityType.EntityFactory<TransformingBlockEntity>) TransformingBlockEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .build("transforming_block_entity"));

    public static final RegistryObject<EntityType<NarakaMagicCircle>> NARAKA_MAGIC_CIRCLE = ENTITY_TYPES.register("naraka_magic_circle",
            () -> EntityType.Builder.of((EntityType.EntityFactory<NarakaMagicCircle>) NarakaMagicCircle::new, MobCategory.MISC)
                    .sized(1f, 0.1f)
                    .build("naraka_magic_circle"));
    public static final RegistryObject<EntityType<NarakaStormEntity>> NARAKA_STORM = ENTITY_TYPES.register("naraka_storm",
            () -> EntityType.Builder.of((EntityType.EntityFactory<NarakaStormEntity>) NarakaStormEntity::new, MobCategory.MISC)
                    .sized(1f, 0.1f)
                    .build("naraka_storm"));

    public static final RegistryObject<EntityType<FractureEntity>> FRACTURE_ENTITY = ENTITY_TYPES.register("fracture",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FractureEntity>) FractureEntity::new, MobCategory.MISC)
                    .sized(5, 2)
                    .build("fracture"));
    public static final RegistryObject<EntityType<FractureExplosion>> FRACTURE_EXPLOSION = ENTITY_TYPES.register("fracture_explosion",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FractureExplosion>) FractureExplosion::new, MobCategory.MISC)
                    .sized(1, 1)
                    .build("fracture_explosion"));

    public static final RegistryObject<EntityType<EbonyBoat>> EBONY_BOAT = ENTITY_TYPES.register("ebony_boat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<EbonyBoat>) EbonyBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build("ebony_boat"));
    public static final RegistryObject<EntityType<EbonyChestBoat>> EBONY_CHEST_BOAT = ENTITY_TYPES.register("ebony_chest_boat",
            () -> EntityType.Builder.of((EntityType.EntityFactory<EbonyChestBoat>) EbonyChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build("ebony_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
