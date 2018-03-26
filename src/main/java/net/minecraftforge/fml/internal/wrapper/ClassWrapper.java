package net.minecraftforge.fml.internal.wrapper;

import net.minecraftforge.fml.internal.wrapper.generator.IVargsFunction;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by covers1624 on 4/01/2018.
 * Modified by KitsuneAlex on 24/02/2018
 */
public class ClassWrapper {

    private static final Type WRAPPER_TYPE = Type.getType(Wrapper.class);
    private static final Type WRAP_TYPE = Type.getType(WrapFunction.class);
    private static final Type UNWRAP_TYPE = Type.getType(UnwrapFunction.class);
    private static int COUNT = 0;

    public static <W, P> WrapKey<W, P> createWrapper(Class<W> wrapperClass, Class<P> parentClass) {
        return createWrapper(wrapperClass, parentClass, wrapperClass);
    }

    public static <W, P, I> WrapKey<I, P> createWrapper(Class<W> wrapperClass, Class<P> parentClass, Class<I> retClass) {
        if (Arrays.stream(wrapperClass.getDeclaredAnnotations()).map(ann -> Type.getType(ann.annotationType())).noneMatch(t -> t.equals(WRAPPER_TYPE))) {
            throw new IllegalArgumentException(String.format("Invalid class(%s) supplied as a wrapper. Does not have @Wrapper annotation.", wrapperClass.getName()));
        }

        WrapKey<I, P> key = WrapKey.createWrapper(retClass, parentClass);
        Method wrapMethod = null;
        Method unwrapMethod = null;

        for (Method method : wrapperClass.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                Type annType = Type.getType(annotation.annotationType());
                if (WRAP_TYPE.equals(annType) && wrapMethod == null) {
                    wrapMethod = method;
                    break;
                }
                else if (UNWRAP_TYPE.equals(annType) && unwrapMethod == null) {
                    unwrapMethod = method;
                    break;
                }
            }
        }
        if (wrapMethod == null || unwrapMethod == null) {
            throw new IllegalArgumentException(String.format("Invalid class '%s' supplied as a wrapper. Does not contain both a Wrap and Unwrap annotated function.", wrapperClass.getName()));
        }

        COUNT++;
        return key;
    }

    public static int getCount() {
        return COUNT;
    }

    public final static class WrapKey<W, P> {

        private Class<W> wrapperClass;
        private Class<P> parentClass;

        private IVargsFunction<W> wrapFunction;
        private IVargsFunction<P> unwrapFunction;

        private WrapKey(Class<W> wrapperClass, Class<P> parentClass) {

            this.wrapperClass = wrapperClass;
            this.parentClass = parentClass;
        }

        private static <W, P> WrapKey<W, P> createWrapper(Class<W> wrapperClass, Class<P> parentClass) {
            return new WrapKey<>(wrapperClass, parentClass);
        }

        public Class<W> getWrapperClass() {
            return wrapperClass;
        }

        public Class<P> getParentClass() {
            return parentClass;
        }

        public W wrap(P parent) {
            return wrapFunction.apply(parent);
        }

        public P unwrap(W wrapper) {
            return unwrapFunction.apply(wrapper);
        }

        @Override
        public int hashCode() {
            return Objects.hash(wrapperClass, parentClass);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof WrapKey)) {
                return false;
            }
            WrapKey other = (WrapKey) obj;
            return other.wrapperClass == wrapperClass && other.parentClass == parentClass;
        }

    }

}