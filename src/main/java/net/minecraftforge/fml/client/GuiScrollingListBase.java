package net.minecraftforge.fml.client;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public abstract class GuiScrollingListBase extends GuiScrollingList {

    private Minecraft mc;

    public GuiScrollingListBase(Minecraft minecraft, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight) {
        super(minecraft, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
        mc = minecraft;
    }

    protected List<String> resizeContent(List<String> content) {
        List<String> resizedContent = Lists.newArrayList();
        int maxTextLength = listWidth - 8;

        if(maxTextLength >= 0) {
            List<ITextComponent> components = content.stream()
                    .map(TextComponentString::new)
                    .collect(Collectors.toList());

            for(ITextComponent component : components) {
                List<ITextComponent> resizedComponents = GuiUtilRenderComponents.splitText(component, maxTextLength, mc.fontRenderer, false, true);
                resizedContent.addAll(resizedComponents.stream()
                        .map(ITextComponent::getFormattedText)
                        .map(this::removeRedundantSpaces)
                        .collect(Collectors.toList())
                );
            }
        }

        return resizedContent;
    }

    protected String removeRedundantSpaces(String line) {
        int spaces = 0;
        boolean nonSpace = false;

        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == ' ' && !nonSpace) {
                spaces++;
            }
            else {
                nonSpace = true;
            }
        }

        return line.substring(spaces);
    }

}