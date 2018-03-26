package net.minecraftforge.fml.client.discovery;

import net.minecraft.util.util_classes.Copyable;
import net.minecraftforge.fml.client.mod.ModCandidate;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.internal.wrapper.UnwrapFunction;
import net.minecraftforge.fml.internal.wrapper.WrapFunction;
import net.minecraftforge.fml.internal.wrapper.Wrapper;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@Wrapper
public class ASMEntry implements Copyable<ASMEntry> {

    private ModCandidate candidate;
    private String annotationName;
    private String className;
    private String objectName;
    private Map<String, Object> annotationValues;

    private ASMEntry(ASMDataTable.ASMData data) {
        //TODO: do things in here!
    }

    public ASMEntry(ModCandidate candidate, String annotationName, String className, @Nullable String objectName, @Nullable Map<String, Object> annotationValues) {
        this.candidate = candidate;
        this.annotationName = annotationName;
        this.className = className;
        this.objectName = objectName;
        this.annotationValues = annotationValues;
    }

    public ModCandidate getCandidate() {
        return candidate;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public String getClassName() {
        return className;
    }

    public Optional<String> getObjectName() {
        return Optional.ofNullable(objectName);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getAnnotationValue(String name) {
        if(annotationValues != null) {
            return Optional.ofNullable((T)annotationValues.get(name));
        }

        return Optional.empty();
    }

    @Override
    public ASMEntry copy() {
        return new ASMEntry(candidate, annotationName, className, objectName, annotationValues);
    }

    @WrapFunction
    private static ASMEntry wrap(ASMDataTable.ASMData data) {
        return new ASMEntry(data);
    }

    @UnwrapFunction
    private static ASMDataTable.ASMData unwrap(ASMEntry entry) {
        //TODO: blep
        return null;
    }

}