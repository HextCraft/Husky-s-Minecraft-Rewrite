package net.minecraftforge.fml.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class DescriptionScrollingList extends GuiScrollingListBase {

    private GuiModList parent;
    private List<String> lines;

    public DescriptionScrollingList(GuiModList parent, List<String> description, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight) {
        super(parent.mc, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
        this.parent = parent;
        lines = resizeContent(description);
        setHeaderInfo(true, 64);
    }

    @Override
    protected int getSize() {
        return 0;
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {}

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    protected void drawSlot(int index, int right, int top, int height, Tessellator tess) {}

    @Override
    protected void drawHeader(int x, int y, Tessellator tess) {
        if(!lines.isEmpty()) {
            for(int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                GlStateManager.pushMatrix();
                parent.mc.fontRenderer.drawStringWithShadow(line, left + 4, y + (i * 10), 0xFFFFFF);
                GlStateManager.popMatrix();
            }
        }
    }

}