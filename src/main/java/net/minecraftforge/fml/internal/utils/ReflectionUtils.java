package net.minecraftforge.fml.internal.utils;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ReflectionUtils {

    public static final MethodHandles.Lookup METHOD_LOOKUP = MethodHandles.lookup();

    public static MethodHandle unreflect(Method method) {
        try {
            return METHOD_LOOKUP.unreflect(method);
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> getFields(Predicate<Field> predicate, Class<?> clazz, @Nullable Object instance, boolean forceAccess) {
        List<T> fieldInstances = Lists.newArrayList();
        Field[] fields = (Field[])Arrays.stream(clazz.getDeclaredFields()).filter(predicate).toArray();
        Arrays.stream(fields).forEach(f -> fieldInstances.add(getField(f.getName(), clazz, instance, forceAccess)));
        return fieldInstances;
    }

    public static Method getDeclaredMethod(String name, Class<?> clazz) {
        Optional<Method> method = Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getName().equals(name)).findFirst();

        if(method.isPresent()) {
            return method.get();
        }
        else {
            throw new ReflectionException("No such method named '%s' in class '%s'!", name, clazz.getName());
        }
    }

    public static Method getMethod(String name, Class<?> clazz) {
        Optional<Method> method = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equals(name)).findFirst();

        if(method.isPresent()) {
            return method.get();
        }
        else {
            throw new ReflectionException("No such method named '%s' in class '%s'!", name, clazz.getName());
        }
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T invokeMethod(String name, Class<?> clazz, @Nullable Object instance, boolean forceAccess, @Nullable Object... args) {
        Class<?>[] descriptorClasses = new Class<?>[args.length];
        for(int i = 0; i < args.length; i++) descriptorClasses[i] = args[i].getClass();

        try {
            if(forceAccess) {
                Optional<Method> method = Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getName().equals(name)).findFirst();

                if(method.isPresent()) {
                    Method m = method.get();
                    m.setAccessible(true);
                    return (T)m.invoke(instance, args);
                }
                else {
                    throw new ReflectionException("No such method named '%s' in class '%s'!", name, clazz.getName());
                }
            }
            else {
                Optional<Method> method = Arrays.stream(clazz.getMethods()).filter(m -> m.getName().equals(name)).findFirst();

                if(method.isPresent()) {
                    Method m = method.get();
                    return (T)m.invoke(instance, args);
                }
                else {
                    throw new ReflectionException("No such method named '%s' in class '%s'!", name, clazz.getName());
                }
            }
        }
        catch(IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setField(String name, Class<?> clazz, @Nullable Object instance, Object value, boolean forceAccess) {
        try {
            if(forceAccess) {
                Optional<Field> field = Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getName().equals(name)).findFirst();

                if(field.isPresent()) {
                    Field f = field.get();
                    f.setAccessible(true);
                    f.set(instance, value);
                }
            }
            else {
                Optional<Field> field = Arrays.stream(clazz.getFields()).filter(f -> f.getName().equals(name)).findFirst();

                if(field.isPresent()) {
                    Field f = field.get();
                    f.set(instance, value);
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getField(String name, Class<?> clazz, Object instance, boolean forceAccess) {
        try {
            if(forceAccess) {
                Optional<Field> field = Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getName().equals(name)).findFirst();

                if(field.isPresent()) {
                    Field f = field.get();
                    f.setAccessible(true);
                    return (T)f.get(instance);
                }
                else {
                    return null;
                }
            }
            else {
                Optional<Field> field = Arrays.stream(clazz.getFields()).filter(f -> f.getName().equals(name)).findFirst();

                if(field.isPresent()) {
                    Field f = field.get();
                    return (T)f.get(instance);
                }
                else {
                    return null;
                }
            }
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    public static <T> T createInstance(Class<T> clazz, boolean forceAccess, Object... args) {
        Class<?>[] constructorClasses = new Class<?>[args.length];
        for(int i = 0; i < args.length; i++) constructorClasses[i] = args[i].getClass();

        try {
            if(forceAccess) {
                Constructor<T> constructor = clazz.getConstructor(constructorClasses);
                return constructor.newInstance(args);
            }
            else {
                Constructor<T> constructor = clazz.getDeclaredConstructor(constructorClasses);
                constructor.setAccessible(true);
                return constructor.newInstance(args);
            }
        }
        catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        }
        catch(InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    public static Class<?> getClassForName(String name) {
        return getClassForName(name, false);
    }

    @Nullable
    public static Class<?> getClassForName(String name, boolean initialize) {
        try {
            ClassLoader scl = ClassLoader.getSystemClassLoader();
            return Class.forName(name, initialize, scl);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class ReflectionException extends RuntimeException {

        public ReflectionException(String message, Object... params) {
            super(String.format(message, params));
        }

    }

}