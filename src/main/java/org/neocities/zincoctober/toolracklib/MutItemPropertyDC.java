package org.neocities.zincoctober.toolracklib;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.StreamCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;

public record MutItemPropertyDC(
    int durability, int durabilityMax, float breakspeed, TagKey<Block> effectiveBlocks, TagKey<Block> tierTag, float swingDamage, float swingSpeed, boolean isWeapon
) {
    public MutItemPropertyDC() {
        this(100, 100, 3.0F, BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.INCORRECT_FOR_WOODEN_TOOL, 0.0F, 1.0F, false);
    }

    // public MutItemPropertyDC(int durability, int durabilityMax, int breakspeed, String effectiveBlocks, String tierTag, int swingDamage, int swingSpeed, boolean isWeapon) {
    //     this.durability = durability,
    //     this.durabilityMax = durabilityMax,
    //     this.breakspeed = breakspeed,
    //     this.effectiveBlocks = effectiveBlocks,
    //     this.tierTag =
    // }

    public static final Codec<MutItemPropertyDC> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("durability").forGetter(MutItemPropertyDC::durability),
            Codec.INT.fieldOf("durabilityMax").forGetter(MutItemPropertyDC::durabilityMax),
            Codec.FLOAT.fieldOf("breakspeed").forGetter(MutItemPropertyDC::breakspeed),
            TagKey.codec(Registries.BLOCK).fieldOf("effectiveBlocks").forGetter(MutItemPropertyDC::effectiveBlocks),
            TagKey.codec(Registries.BLOCK).fieldOf("tierTag").forGetter(MutItemPropertyDC::tierTag),
            Codec.FLOAT.fieldOf("swingDamage").forGetter(MutItemPropertyDC::swingDamage),
            Codec.FLOAT.fieldOf("swingSpeed").forGetter(MutItemPropertyDC::swingSpeed),
            Codec.BOOL.fieldOf("isWeapon").forGetter(MutItemPropertyDC::isWeapon)
        ).apply(instance, MutItemPropertyDC::new)
    );

    public static final StreamCodec<ByteBuf, MutItemPropertyDC> STREAM_CODEC = StreamCodec.of(
        MutItemPropertyDC::encode,
        MutItemPropertyDC::decode
    );

    private static void encode(ByteBuf buf, MutItemPropertyDC data) {
        ByteBufCodecs.INT.encode(buf, data.durability);
        ByteBufCodecs.INT.encode(buf, data.durabilityMax);
        ByteBufCodecs.FLOAT.encode(buf, data.breakspeed);
        tagStreamCodec.encode(buf, data.effectiveBlocks);
        tagStreamCodec.encode(buf, data.tierTag);
        ByteBufCodecs.FLOAT.encode(buf, data.swingDamage);
        ByteBufCodecs.FLOAT.encode(buf, data.swingSpeed);
        ByteBufCodecs.BOOL.encode(buf, data.isWeapon);
    }

    private static MutItemPropertyDC decode(ByteBuf buf) {
        MutItemPropertyDC data = new MutItemPropertyDC(
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            tagStreamCodec.decode(buf),
            tagStreamCodec.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            ByteBufCodecs.BOOL.decode(buf)
        );
        return data;
    }

    private static StreamCodec<ByteBuf, TagKey<Block>> tagStreamCodec =
        ResourceLocation.STREAM_CODEC.map(
        tag -> TagKey.create(Registries.BLOCK, tag),
        TagKey::location
        );

}
