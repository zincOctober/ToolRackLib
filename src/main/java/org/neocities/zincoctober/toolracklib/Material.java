package org.neocities.zincoctober.toolracklib;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public record Material(
    HolderSet<Item> component,
    int baseDurability,
    TagKey<Block> incorrectFor,
    float baseBreakSpeed,
    float swingSpeed,
    float swingDamage,
    boolean isWeapon
) {
    public static final Codec<Material> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("component").forGetter(Material::component),
            Codec.INT.fieldOf("baseDurability").forGetter(Material::baseDurability),
            TagKey.codec(Registries.BLOCK).fieldOf("incorrectFor").forGetter(Material::incorrectFor),
            Codec.FLOAT.fieldOf("baseBreakSpeed").forGetter(Material::baseBreakSpeed),
            Codec.FLOAT.fieldOf("swingSpeed").forGetter(Material::swingSpeed),
            Codec.FLOAT.fieldOf("swingDamage").forGetter(Material::swingDamage),
            Codec.BOOL.fieldOf("isWeapon").forGetter(Material::isWeapon)
        ).apply(instance, Material::new)
    );
}
