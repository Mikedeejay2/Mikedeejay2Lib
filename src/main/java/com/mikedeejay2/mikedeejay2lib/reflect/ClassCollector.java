package com.mikedeejay2.mikedeejay2lib.reflect;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Collects classes from a specified package path. Also has the ability to deeply collect classes.
 *
 * @author Mikedeejay2
 */
public class ClassCollector<C> {
    /**
     * The {@link ClassLoader} used to collect the classes from
     */
    protected ClassLoader classLoader;
    /**
     * The qualified package path to collect classes from. Formatted like <code>java.util.function</code>
     */
    protected String qualifiedPath;
    /**
     * Whether to deeply traverse the package, getting sub-package classes as well
     */
    protected boolean deepTraversal;
    /**
     * The expected class type of the classes being collected. This can be a superclass of a class as well, such as an
     * Object class.
     */
    protected Class<C> expectedClassType;

    /**
     * Construct a new <code>ClassCollector</code>
     *
     * @param classLoader       The {@link ClassLoader} used to collect the classes from
     * @param qualifiedPath     The qualified package path to collect classes from. Formatted like
     *                          <code>java.util.function</code>
     * @param deepTraversal     Whether to deeply traverse the package, getting sub-package classes as well
     * @param expectedClassType The expected class type of the classes being collected. This can be a superclass of a
     *                          class as well, such as an Object class.
     */
    public ClassCollector(ClassLoader classLoader, String qualifiedPath, boolean deepTraversal, Class<C> expectedClassType) {
        this.classLoader = classLoader;
        this.qualifiedPath = qualifiedPath;
        this.deepTraversal = deepTraversal;
        this.expectedClassType = expectedClassType;
    }

    /**
     * Collect classes to a set
     *
     * @return A set of the classes within the specified package
     */
    public Set<Class<? extends C>> collect() {
        try {
            return ClassPath.from(classLoader)
                .getAllClasses()
                .stream()
                .filter(clazz -> deepTraversal ?
                                 clazz.getPackageName().startsWith(qualifiedPath) :
                                 clazz.getPackageName().equals(qualifiedPath))
                .map(ClassPath.ClassInfo::load)
                .filter(expectedClassType::isAssignableFrom)
                .map(clazz -> (Class<? extends C>) clazz)
                .collect(Collectors.toSet());
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the {@link ClassLoader} used to collect the classes from
     *
     * @return The <code>ClassLoader</code>
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Set the {@link ClassLoader} used to collect the classes from
     *
     * @param classLoader The new <code>ClassLoader</code>
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Get the qualified package path to collect classes from. Formatted like <code>java.util.function</code>
     *
     * @return The qualified path
     */
    public String getQualifiedPath() {
        return qualifiedPath;
    }

    /**
     * Set the qualified package path to collect classes from. Formatted like <code>java.util.function</code>
     *
     * @param qualifiedPath The new qualified path
     */
    public void setQualifiedPath(String qualifiedPath) {
        this.qualifiedPath = qualifiedPath;
    }

    /**
     * Get whether to deeply traverse the package, getting sub-package classes as well
     *
     * @return Whether to deeply traverse the package
     */
    public boolean isDeepTraversal() {
        return deepTraversal;
    }

    /**
     * Set whether to deeply traverse the package, getting sub-package classes as well
     *
     * @param deepTraversal The new deep traversal state
     */
    public void setDeepTraversal(boolean deepTraversal) {
        this.deepTraversal = deepTraversal;
    }

    /**
     * Get the expected class type of the classes being collected. This can be a superclass of a class as well, such as
     * an Object class.
     *
     * @return The expected class type
     */
    public Class<C> getExpectedClassType() {
        return expectedClassType;
    }

    /**
     * Set the expected class type of the classes being collected. This can be a superclass of a class as well, such as
     * an Object class.
     *
     * @param expectedClassType  The new expected class type
     */
    public void setExpectedClassType(Class<C> expectedClassType) {
        this.expectedClassType = expectedClassType;
    }
}
