package net.minecraftforge.fml.client.discovery;

import com.google.common.collect.*;
import net.minecraftforge.fml.client.mod.ModCandidate;
import net.minecraftforge.fml.client.mod.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.internal.utils.ReflectionUtils;
import net.minecraftforge.fml.internal.utils.TupleUtils;
import net.minecraftforge.fml.internal.wrapper.UnwrapFunction;
import net.minecraftforge.fml.internal.wrapper.WrapFunction;
import net.minecraftforge.fml.internal.wrapper.Wrapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.internal.wrapper.Wrappers.ASM_DATA;
import static net.minecraftforge.fml.internal.wrapper.Wrappers.MOD_CONTAINER;


@Wrapper
public class ASMEntryTable {

    private SetMultimap<String, ASMEntry> globalEntries = HashMultimap.create();
    private Map<ModContainer, SetMultimap<String, ASMEntry>> containerEntries;
    private List<ModContainer> containers = Lists.newArrayList();
    private SetMultimap<String, ModCandidate> packageMap = HashMultimap.create();

    public SetMultimap<String, ASMEntry> getAnnotations(ModContainer container) {
        if(containerEntries == null) {
            ImmutableMap.Builder<ModContainer, SetMultimap<String, ASMEntry>> builder = ImmutableMap.builder();

            containers.forEach(cont -> {
                Multimap<String, ASMEntry> entries = Multimaps.filterValues(globalEntries, e -> cont.getSource().equals(e.getCandidate().getModContainer()));
                builder.put(cont, ImmutableSetMultimap.copyOf(entries));
            });

            containerEntries = builder.build();
        }

        return containerEntries.get(container);
    }

    public Set<ASMEntry> getAll(String annotation) {
        return globalEntries.get(annotation);
    }

    public void addASMEntry(ModCandidate candidate, String annotation, String className, @Nullable String objectName, @Nullable Map<String, Object> annotationValues) {
        globalEntries.put(annotation, new ASMEntry(candidate, annotation, className, objectName, annotationValues));
    }

    public void addContainer(ModContainer container) {
        containers.add(container);
    }

    public void registerPackage(ModCandidate candidate, String pkg) {
        packageMap.put(pkg, candidate);
    }

    public Set<ModCandidate> getCandidates(String pkg) {
        return packageMap.get(pkg);
    }

    public static Optional<String> getOwnerModID(Set<ASMEntry> entries, ASMEntry target) {
        if(entries.size() == 1) {
            return entries.iterator().next().getAnnotationValue("value");
        }
        else {
            Optional<ASMEntry> entry = entries.stream()
                    .filter(e -> target.getClassName().startsWith(e.getClassName()))
                    .findFirst();

            if(entry.isPresent()) {
                return entry.get().getAnnotationValue("value");
            }
        }

        return Optional.empty();
    }

    @WrapFunction
    private static ASMEntryTable wrap(ASMDataTable table) {
        ASMEntryTable entryTable = new ASMEntryTable();

        SetMultimap<String, ASMDataTable.ASMData> globalAnnotationData =
                ReflectionUtils.getField("globalAnnotationData", ASMDataTable.class, table, true);
        Map<net.minecraftforge.fml.common.ModContainer, SetMultimap<String, ASMDataTable.ASMData>> containerAnnotationData =
                ReflectionUtils.getField("containerAnnotationData", ASMDataTable.class, table, true);
        List<net.minecraftforge.fml.common.ModContainer> containers =
                ReflectionUtils.getField("containers", ASMDataTable.class, table, true);
        SetMultimap<String, net.minecraftforge.fml.common.discovery.ModCandidate> packageMap =
                ReflectionUtils.getField("packageMap", ASMDataTable.class, table, true);

        entryTable.globalEntries = Multimaps.forMap(globalAnnotationData.entries().stream()
                .map(TupleUtils::toPair)
                .map(p -> TupleUtils.mapR(p, ASM_DATA::wrap))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight)));

        entryTable.containerEntries = containerAnnotationData.entrySet().stream()
                .map(TupleUtils::toPair)
                .map(p -> TupleUtils.mapL(p, MOD_CONTAINER::wrap))
                .map(p -> TupleUtils.mapR(p, m -> Multimaps.forMap(m.entries().stream()
                        .map(TupleUtils::toPair)
                        .map(p2 -> TupleUtils.mapR(p2, ASM_DATA::wrap))
                        .collect(Collectors.toMap(Pair::getLeft, Pair::getRight)))))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        //TODO: blep

        return entryTable;
    }

    @UnwrapFunction
    private static ASMDataTable unwrap(ASMEntryTable table) {
        //TODO: blep
        return null;
    }

}