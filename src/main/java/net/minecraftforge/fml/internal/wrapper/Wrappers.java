package net.minecraftforge.fml.internal.wrapper;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.border.EnumBorderStatus;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.fml.client.discovery.ASMEntry;
import net.minecraftforge.fml.client.discovery.ASMEntryTable;
import net.minecraftforge.fml.client.mod.ModCandidate;
import net.minecraftforge.fml.client.mod.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLStateEvent;

public class Wrappers {

    public static ClassWrapper.WrapKey<ASMEntry, ASMDataTable.ASMData> ASM_DATA;
    public static ClassWrapper.WrapKey<ASMEntryTable, ASMDataTable> ASM_DATA_TABLE;
    public static ClassWrapper.WrapKey<ModCandidate, net.minecraftforge.fml.common.discovery.ModCandidate> MOD_CANDIDATE;
    public static ClassWrapper.WrapKey<ModContainer, net.minecraftforge.fml.common.ModContainer> MOD_CONTAINER;
    public static ClassWrapper.WrapKey<EnumEnchantmentType, EnumEnchantmentType> ENUM_ENCHANTMENT_TYPE;
    public static ClassWrapper.WrapKey<EnumDyeColor, EnumDyeColor> ENUM_DYE_COLOR;
    public static ClassWrapper.WrapKey<EnumRarity, EnumRarity> ENUM_RARITY;
    //TODO: add IItemCompound
    //TODO: add IItem
    //TODO: add IBlock
    //TODO: add IBlockEntity
    //TODO: add IEntity
    //TODO: add IEntityLiving
    //TODO: add IEntityPlayer

    public static void registerWrappers() {
        ASM_DATA =                  ClassWrapper.createWrapper(ASMEntry.class, ASMDataTable.ASMData.class);
        ASM_DATA_TABLE =            ClassWrapper.createWrapper(ASMEntryTable.class, ASMDataTable.class);
        MOD_CANDIDATE =             ClassWrapper.createWrapper(ModCandidate.class, net.minecraftforge.fml.common.discovery.ModCandidate.class);
        MOD_CONTAINER =             ClassWrapper.createWrapper(ModContainer.class, net.minecraftforge.fml.common.ModContainer.class);
    }

}