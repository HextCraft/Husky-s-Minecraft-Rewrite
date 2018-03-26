package net.minecraftforge.fml.client.registry;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconRegister {

    public static TextureAtlasSprite ICON_INFO_UPDATE;
    public static TextureAtlasSprite ICON_INFO_UPDATE_SELECTED;
    public static TextureAtlasSprite ICON_REPORT_ISSUE;
    public static TextureAtlasSprite ICON_REPORT_ISSUE_SELECTED;

    @SubscribeEvent
    public void handleTextureStitchEvent(TextureStitchEvent event) {
        TextureMap map = event.getMap();
        ICON_INFO_UPDATE = map.registerSprite(new ResourceLocation("forge", "gui/info_update"));
        ICON_INFO_UPDATE_SELECTED = map.registerSprite(new ResourceLocation("forge", "gui/info_update_selected"));
        ICON_REPORT_ISSUE = map.registerSprite(new ResourceLocation("forge", "gui/report_issue"));
        ICON_REPORT_ISSUE_SELECTED = map.registerSprite(new ResourceLocation("forge", "gui/report_issue_selected"));
    }

}