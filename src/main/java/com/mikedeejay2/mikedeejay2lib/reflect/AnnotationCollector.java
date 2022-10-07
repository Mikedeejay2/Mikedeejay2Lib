package com.mikedeejay2.mikedeejay2lib.reflect;

import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.ImmutablePair;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Collects classes with a specified annotation from a package
 *
 * @param <A> The annotation type
 * @author Mikedeejay2
 */
public class AnnotationCollector<A extends Annotation, C> {
    /**
     * The {@link ClassCollector}, used to collect the classes from a package
     */
    protected ClassCollector<C> classCollector;
    /**
     * The class of the annotation to be collected
     */
    protected Class<A> annotationClass;

    /**
     * Construct a new <code>AnnotationCollector</code>
     *
     * @param classCollector  The {@link ClassCollector}, used to collect the classes from a package
     * @param annotationClass The class of the annotation to be collected
     */
    public AnnotationCollector(ClassCollector<C> classCollector, Class<A> annotationClass) {
        this.classCollector = classCollector;
        this.annotationClass = annotationClass;
    }

    /**
     * Collect the annotated classes
     *
     * @return A set of pairs containing the annotated class and the annotation associated with it
     */
    public Set<Pair<Class<? extends C>, A>> collect() {
        return classCollector.collect()
            .stream()
            .filter(clazz -> clazz.isAnnotationPresent(annotationClass))
            .<Pair<Class<? extends C>, A>>map(clazz -> new ImmutablePair<>(clazz, clazz.getAnnotation(annotationClass)))
            .collect(Collectors.toSet());
    }

    /**
     * Get the class of the annotation to be collected
     *
     * @return The class of the annotation to be collected
     */
    public Class<A> getAnnotationClass() {
        return annotationClass;
    }

    /**
     * Set the class of the annotation to be collected
     *
     * @param annotationClass The new annotation class
     */
    public void setAnnotationClass(Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }

    /**
     * Get the {@link ClassCollector}, used to collect the classes from a package
     *
     * @return The <code>ClassCollector</code>
     */
    public ClassCollector<C> getClassCollector() {
        return classCollector;
    }

    /**
     * Set the {@link ClassCollector}, used to collect the classes from a package
     *
     * @param classCollector The new <code>ClassCollector</code>
     */
    public void setClassCollector(ClassCollector<C> classCollector) {
        this.classCollector = classCollector;
    }
}
