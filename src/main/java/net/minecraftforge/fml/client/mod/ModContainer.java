package net.minecraftforge.fml.client.mod;

import net.minecraftforge.fml.internal.wrapper.UnwrapFunction;
import net.minecraftforge.fml.internal.wrapper.WrapFunction;
import net.minecraftforge.fml.internal.wrapper.Wrapper;

import java.io.File;

@Wrapper
public class ModContainer {

    private File source;

    public File getSource() {
        return source;
    }

    @WrapFunction
    private static ModContainer wrap(net.minecraftforge.fml.common.ModContainer container) {
        //TODO: blep
        return null;
    }

    @UnwrapFunction
    private static net.minecraftforge.fml.common.ModContainer uwnrap(ModContainer container) {
        //TODO: blep
        return null;
    }

}