package net.minecraftforge.fml.internal.wrapper.generator;

/**
 * Created by covers1624 on 31/12/2017.
 */
public interface IVargsFunction<R> {

    R apply(Object... args);

}