package net.minecraftforge.fml.client.mod;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraftforge.fml.client.discovery.ASMEntryTable;
import net.minecraftforge.fml.client.discovery.ModDiscoverer;
import net.minecraftforge.fml.common.discovery.ContainerType;
import net.minecraftforge.fml.internal.wrapper.UnwrapFunction;
import net.minecraftforge.fml.internal.wrapper.WrapFunction;
import net.minecraftforge.fml.internal.wrapper.Wrapper;

import java.io.File;
import java.util.List;
import java.util.Set;

@Wrapper
public class ModCandidate {

    private File modContainer;
    private Set<String> classes = Sets.newHashSet();
    private List<ModContainer> containers;
    private List<String> packages = Lists.newArrayList();

    public ModCandidate(File container) {
        modContainer = container;
    }

    public File getModContainer() {
        return modContainer;
    }

    public Set<String> getClasses() {
        return classes;
    }

    public List<ModContainer> getContainers() {
        return containers;
    }

    public List<String> getPackages() {
        return packages;
    }

    public List<ModContainer> explore(ASMEntryTable table) {
        containers = ModDiscoverer.findMods(this, table);
        return containers;
    }

    public void addClassEntry(String name, ASMEntryTable table) {
        String className = name.substring(0, name.lastIndexOf('.'));
        classes.add(className);
        className = className.replace('/','.');
        int packageIndex = className.lastIndexOf('.');

        if(packageIndex > -1) {
            String pkg = className.substring(0, packageIndex);
            packages.add(pkg);
            table.registerPackage(this, pkg);
        }
    }

    @WrapFunction
    private static ModCandidate wrap(net.minecraftforge.fml.common.discovery.ModCandidate candidate) {
        return new ModCandidate(candidate.getModContainer());
    }

    @UnwrapFunction
    private static net.minecraftforge.fml.common.discovery.ModCandidate unwrap(ModCandidate candidate) {
        return new net.minecraftforge.fml.common.discovery.ModCandidate(candidate.modContainer, candidate.modContainer, ContainerType.JAR);
    }

}