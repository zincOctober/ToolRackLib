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
import net.minecraft.core.registries.Registries ;

public record Material(
    String name,
    HolderSet<Item> component,
    int baseDurability,
    TagKey<Block> incorrectFor,
    float baseBreakSpeed,
    float baseSwingSpeed,
    float baseSwingDamage
) {
    public static final Codec<Material> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.STRING.fieldOf("name").forGetter(Material::name),
            RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("component").forGetter(Material::component),
            Codec.INT.fieldOf("baseDurability").forGetter(Material::baseDurability),
            TagKey.codec(Registries.BLOCK).fieldOf("incorrectFor").forGetter(Material::incorrectFor),
            Codec.FLOAT.fieldOf("baseBreakSpeed").forGetter(Material::baseBreakSpeed),
            Codec.FLOAT.fieldOf("baseSwingSpeed").forGetter(Material::baseSwingSpeed),
            Codec.FLOAT.fieldOf("baseSwingDamage").forGetter(Material::baseSwingDamage)
        ).apply(instance, Material::new)
    );
}
