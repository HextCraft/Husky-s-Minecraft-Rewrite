package net.minecraftforge.fml.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class InfoIcon {

    private GuiScreen parent;
    private TextureAtlasSprite textureUnselected;
    private TextureAtlasSprite textureSelected;
    private IIconClickAction action;

    public InfoIcon(GuiScreen parent, TextureAtlasSprite textureUnselected, TextureAtlasSprite textureSelected) {
        this.parent = parent;
        this.textureUnselected = textureUnselected;
        this.textureSelected = textureSelected;
    }

    public void setClickAction(IIconClickAction action) {
        this.action = action;
    }

    public void drawIcon(int x, int y, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if(mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {
            parent.drawTexturedModalRect(x, y, textureSelected, 16, 16);
        }
        else {
            parent.drawTexturedModalRect(x, y, textureUnselected, 16, 16);
        }

        GlStateManager.popMatrix();
    }

    public void mouseClicked(int x, int y, int mouseX, int mouseY) {
        if(action != null) {
            if(mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {
                parent.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
                action.performAction();
            }
        }
    }

    public TextureAtlasSprite getTextureUnselected() {
        return textureUnselected;
    }

    public TextureAtlasSprite getTextureSelected() {
        return textureSelected;
    }

    public interface IIconClickAction {

        void performAction();

    }

}