package net.minecraftforge.fml.client;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SideOnly(Side.CLIENT)
public class GuiScreenBase extends GuiScreen {

    public void drawBackground(TextureAtlasSprite texture, int overlayColor) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        for(int x = 0; x < mc.displayWidth / 16; x++) {
            for(int y = 0; y < mc.displayHeight / 16; y++) {
                drawTexturedModalRect(x * 16, y * 16, texture, 16, 16);
            }
        }

        drawRect(0, 0, width, height, overlayColor);
        GlStateManager.popMatrix();
    }

    public void drawGrassBackground(int left, int top, int width2, int height2, int overlayColor) {
        GlStateManager.pushMatrix();

        for(int x = 0; x < mc.displayWidth / 16; x++) {
            for(int y = 0; y < mc.displayHeight / 16; y++) {
                mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                drawTexturedModalRect(x * 16, y * 16, mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/dirt"), 16, 16);
            }
        }

        drawRect(left, top, width2, height2, overlayColor);
        GlStateManager.popMatrix();
    }

    public void drawBackground(int x, int y, int w, int h, ResourceLocation location, int overlayColor) {
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(location.toString());

        for(int dx = 0; dx < MathHelper.roundUp(w / 16, 1); dx++) {
            for(int dy = 0; dy < MathHelper.roundUp(h / 16, 1); dy++) {
                drawTexturedModalRect(dx * 16, dy * 16, sprite, 16, 16);
            }
        }
        GlStateManager.popMatrix();
    }

    public void drawGradientBox(int x, int y, int width, int height, int startColor, int endColor, int borderColor) {
        GlStateManager.pushMatrix();
        drawGradientRect(x, y, x + width, y + height, startColor, endColor);
        drawVerticalLine(x, y - 1, y + height, borderColor);
        drawVerticalLine(x + width - 1, y - 1, y + height, borderColor);
        drawHorizontalLine(x, x + width - 1, y, borderColor);
        drawHorizontalLine(x, x + width - 1, y + height - 1, borderColor);
        GlStateManager.popMatrix();
    }

    public void drawLineBox(int x, int y, int width, int height, int borderColor) {
        GlStateManager.pushMatrix();
        drawVerticalLine(x, y - 1, y + height, borderColor);
        drawVerticalLine(x + width - 1, y - 1, y + height, borderColor);
        drawHorizontalLine(x, x + width - 1, y, borderColor);
        drawHorizontalLine(x, x + width - 1, y + height - 1, borderColor);
        GlStateManager.popMatrix();
    }

    public void openBrowser(String url) {
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            }
            catch(IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}