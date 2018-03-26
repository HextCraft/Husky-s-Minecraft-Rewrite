package net.minecraftforge.fml.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class ModScrollingList extends GuiScrollingListBase {

    private GuiModList parent;
    private Map<String, List<InfoIcon>> infoIcons;
    private final String infNoVersion = I18n.format("gui.info.no_version");
    private final String infAuthors = I18n.format("gui.info.authors");
    private ArrayList<ModContainer> mods;

    public ModScrollingList(GuiModList parent, ArrayList<ModContainer> mods, int listWidth, int slotHeight) {
        super(parent.getMinecraftInstance(), listWidth, parent.height, 32, parent.height - 88 + 4, 10, slotHeight, parent.width, parent.height);
        this.parent = parent;
        this.mods = mods;
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {
        this.parent.selectModIndex(index);
    }

    @Override
    protected int getSize() {
        return 0;
    }

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
    protected void drawSlot(int index, int right, int top, int height, Tessellator tess) {

        ModContainer mc       = mods.get(index);
        String       name     = StringUtils.stripControlCodes(mc.getName());
        String       version  = StringUtils.stripControlCodes(mc.getDisplayVersion());
        FontRenderer font     = this.parent.getFontRenderer();
        ForgeVersion.CheckResult vercheck = ForgeVersion.getResult(mc);

        ModMetadata metadata = FMLModContainer.instance().getMetadata();
        String modid = metadata.modId;

        if (!metadata.name.isEmpty()) {
            font.drawString(font.trimStringToWidth(name, listWidth - 10), left + 3, top + 2, 0xFFFFFFFF);
        } else {
            font.drawString(font.trimStringToWidth(modid, listWidth - 10), left + 3, top + 2, 0xFFFFFFFF);
        }

        if (!metadata.version.isEmpty()) {
            font.drawString(font.trimStringToWidth(version, listWidth - (5 + height)), left + 3, top + 12, 0xCCCCCC);
        } else {
            font.drawString(font.trimStringToWidth(infNoVersion, listWidth - (5 + height)), left + 3, top + 12, 0xCCCCCC);
        }

        font.drawString(font.trimStringToWidth(String.format("%s:", infAuthors), listWidth - (5 + height)), left + 3, top + 26, 0xCCCCCC);
        ModelHumanoidHead model = new ModelHumanoidHead();

        if (!metadata.authorList.isEmpty()) {
            for (int i = 0; i < metadata.getAuthorSkinList().length(); i++) {
//                    Pair<String, UUID> author = metadata.getAuthorList().length();
                int x = left + 58 + (i * 20);
                int y = top + 37;
//                    ResourceLocation texture = Carbon.PROXY.getAuthorSkin(author.getRight());
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, 0D);
//                    parent.mc.getTextureManager().bindTexture(texture);
                model.render(null, 0F, 0F, 0F, 0F, 0F, 2F);
                GlStateManager.translate(-x, -y, 0D);
                GlStateManager.popMatrix();
            }

            for (int i = 0; i < metadata.getAuthorList().length(); i++) {
                int x = left + 58 + (i * 20);
                int y = top + 37;

                if (mouseX >= x - 8 && mouseX <= x + 8 && mouseY >= y - 16 && mouseY <= y) {
                    GlStateManager.pushMatrix();
                    GuiUtils.drawHoveringText(Lists.newArrayList(metadata.authorList), mouseX, mouseY, screenWidth, screenHeight, 200, font);
                    GlStateManager.popMatrix();
                }
            }
        }

        List<InfoIcon> icons = infoIcons.get(modid);

        for (int i = 0; i < icons.size(); i++) {
            InfoIcon icon = icons.get(i);

            if (icon.getTextureUnselected().hasAnimationMetadata()) {
                icon.getTextureUnselected().updateAnimation();
            }

            if (icon.getTextureSelected().hasAnimationMetadata()) {
                icon.getTextureSelected().updateAnimation();
            }

            icon.drawIcon(right - 18 - (i * 18), top + 2, mouseX, mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float scrollDistance = GuiScrollingList.scrollDistance;
        int headerHeight = GuiScrollingList.headerHeight;
        int scrollBarRight = left + listWidth;
        int scrollBarLeft  = scrollBarRight - 6;
        int entryRight = scrollBarLeft - 1;

        for(int index = 0; index < getSize(); index++) {
            int baseY = top + 4 - (int)scrollDistance;
            int slotTop = baseY + index * slotHeight + headerHeight;

            if(FMLModContainer.instance().getMetadata().hashCode() > 0) {
                ModMetadata metadata = FMLModContainer.instance().getMetadata();
                List<InfoIcon> icons = infoIcons.get(metadata.modId);

                for(int i = 0; i < icons.size(); i++) {
                    icons.get(i).mouseClicked(entryRight - 18 - (i * 18), slotTop + 2, mouseX, mouseY);
                }
            }
        }
    }

    /*private Map<String, List<InfoIcon>> computeInfoIcons(Map<String, ModMetadata> metadata) {
        Map<String, List<InfoIcon>> icons = Maps.newHashMap();

        metadata.forEach((modid, data) -> {
            List<InfoIcon> iconList = Lists.newArrayList();

            if(data.getIssueTracker().isPresent()) {
                InfoIcon iconIssueTracker = new InfoIcon(parent, IconRegister.ICON_REPORT_ISSUE, IconRegister.ICON_REPORT_ISSUE_SELECTED);
                iconIssueTracker.setClickAction(() -> parent.openBrowser(data.getIssueTracker().get()));
                iconList.add(iconIssueTracker);
            }
            if(UpdateChecker.INSTANCE.getAvailableUpdates().containsKey(modid)) {
                UpdateChecker.Update update = UpdateChecker.INSTANCE.getAvailableUpdates().get(modid);
                InfoIcon iconUpdate = new InfoIcon(parent, IconRegister.ICON_INFO_UPDATE, IconRegister.ICON_INFO_UPDATE_SELECTED);
                iconUpdate.setClickAction(() -> FMLClientHandler.instance().displayGuiScreen(null, new GuiModUpdate(update)));
                iconList.add(iconUpdate);
            }

            icons.put(modid, iconList);
        });

        return icons;
    }*/

}