package net.minecraftforge.fml.internal.utils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Function;

public class TupleUtils {

    /**
     * Splits off an element from a Triple to make up a Pair.
     * @param triple the Triple to split
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Pair containing the middle and right value of the Triple
     */
    public static <L, M, R> Pair<M, R> splitL(Triple<L, M, R> triple) {
        return Pair.of(triple.getMiddle(), triple.getRight());
    }

    /**
     * Splits off an element from a Triple to make up a Pair.
     * @param triple the Triple to split
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Pair containing the left and right value of the Triple
     */
    public static <L, M, R> Pair<L, R> splitM(Triple<L, M, R> triple) {
        return Pair.of(triple.getLeft(), triple.getRight());
    }

    /**
     * Splits off an element from a Triple to make up a Pair.
     * @param triple the Triple to split
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Pair containing the left and middle value of the Triple
     */
    public static <L, M, R> Pair<L, M> splitR(Triple<L, M, R> triple) {
        return Pair.of(triple.getLeft(), triple.getMiddle());
    }

    /**
     * Joins a pair and a single element together to make up a Triple.
     * The single element represents the left value.
     * @param pair the pair to join
     * @param element the single element to join
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Triple containing both values from the pair and the single element
     */
    public static <L, M, R> Triple<L, M, R> joinL(Pair<M, R> pair, L element) {
        return Triple.of(element, pair.getLeft(), pair.getRight());
    }

    /**
     * Joins a pair and a single element together to make up a Triple.
     * The single element represents the middle value.
     * @param pair the pair to join
     * @param element the single element to join
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Triple containing both values from the pair and the single element
     */
    public static <L, M, R> Triple<L, M, R> joinM(Pair<L, R> pair, M element) {
        return Triple.of(pair.getLeft(), element, pair.getRight());
    }

    /**
     * Joins a pair and a single element together to make up a Triple.
     * The single element represents the right value.
     * @param pair the pair to join
     * @param element the single element to join
     * @param <L> the left type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Triple containing both values from the pair and the single element
     */
    public static <L, M, R> Triple<L, M, R> joinR(Pair<L, M> pair, R element) {
        return Triple.of(pair.getLeft(), pair.getRight(), element);
    }

    /**
     * Maps a Pair using two functions for the left and right value.
     * @param pair the pair to map
     * @param leftFunction the function for mapping the left value
     * @param rightFunction the function for mapping the right value
     * @param <LI> the left input type
     * @param <LO> the left output type
     * @param <RI> the right input type
     * @param <RO> the right output type
     * @return a Pair containing the mapped values
     */
    public static <LI, LO, RI, RO> Pair<LO, RO> map(Pair<LI, RI> pair, Function<LI, LO> leftFunction, Function<RI, RO> rightFunction) {
        LO left = leftFunction.apply(pair.getLeft());
        RO right = rightFunction.apply(pair.getRight());
        return Pair.of(left, right);
    }

    /**
     * Maps the left value of a Pair using a Function.
     * @param pair the pair to map
     * @param function the function applied to the right value
     * @param <LI> the left input type
     * @param <LO> the left output type
     * @param <R> the right type
     * @return a Pair containing the mapped left value
     */
    public static <LI, LO, R> Pair<LO, R> mapL(Pair<LI, R> pair, Function<LI, LO> function) {
        return Pair.of(function.apply(pair.getLeft()), pair.getRight());
    }

    /**
     * Maps the right value of a Pair using a Function.
     * @param pair the pair to map
     * @param function the function applied to the right value
     * @param <L> the left type
     * @param <RI> the right input type
     * @param <RO> the right output type
     * @return a Pair containing the mapped right value
     */
    public static <L, RI, RO> Pair<L, RO> mapR(Pair<L, RI> pair, Function<RI, RO> function) {
        return Pair.of(pair.getLeft(), function.apply(pair.getRight()));
    }

    /**
     * Converts an Entry to a Pair.
     * @param entry the Entry to be converted
     * @param <L> the left type
     * @param <R> the right type
     * @return a Pair containing the key and the value of the Entry
     */
    public static <L, R> Pair<L, R> toPair(Map.Entry<L, R> entry) {
        return Pair.of(entry.getKey(), entry.getValue());
    }

    /**
     * Converts a Pair to an Entry.
     * @param pair the Pair to be converted
     * @param <L> the left type
     * @param <R> the right type
     * @return an Entry containing the left and right value of the Pair
     */
    public static <L, R> Map.Entry<L, R> toEntry(Pair<L, R> pair) {
        return new Map.Entry<L, R>() {
            @Override
            public L getKey() {
                return pair.getLeft();
            }

            @Override
            public R getValue() {
                return pair.getRight();
            }

            @Override
            public R setValue(R r) {
                pair.setValue(r);
                return pair.getRight();
            }
        };
    }

    /**
     * Maps a triple using three functions for the left, middle and right value.
     * @param triple the Triple to map
     * @param leftFunction  the function for mapping the left value
     * @param middleFunction the function for mapping the middle value
     * @param rightFunction the function for mapping the right value
     * @param <LI> the left input type
     * @param <LO> the left output type
     * @param <MI> the middle input type
     * @param <MO> the middle output type
     * @param <RI> the right input type
     * @param <RO> the right output type
     * @return a Triple containing the mapped values
     */
    public static <LI, LO, MI, MO, RI, RO> Triple<LO, MO, RO> map(Triple<LI, MI, RI> triple, Function<LI, LO> leftFunction, Function<MI, MO> middleFunction, Function<RI, RO> rightFunction) {
        LO left = leftFunction.apply(triple.getLeft());
        MO middle = middleFunction.apply(triple.getMiddle());
        RO right = rightFunction.apply(triple.getRight());
        return Triple.of(left, middle, right);
    }

    /**
     * Maps the left value of a Triple using a Function.
     * @param triple the triple to map
     * @param function the function applied to the left value
     * @param <LI> the left input type
     * @param <LO> the left output type
     * @param <M> the middle type
     * @param <R> the right type
     * @return a Triple containing the mapped left value
     */
    public static <LI, LO, M, R> Triple<LO, M, R> mapL(Triple<LI, M, R> triple, Function<LI, LO> function) {
        return Triple.of(function.apply(triple.getLeft()), triple.getMiddle(), triple.getRight());
    }

    /**
     * Maps the middle value of a Triple using a Function.
     * @param triple the triple to map
     * @param function the function applied to the middle value
     * @param <L> the left type
     * @param <MI> the middle input type
     * @param <MO> the middle output type
     * @param <R> the right type
     * @return a Triple containing the mapped middle value
     */
    public static <L, MI, MO, R> Triple<L, MO, R> mapM(Triple<L, MI, R> triple, Function<MI, MO> function) {
        return Triple.of(triple.getLeft(), function.apply(triple.getMiddle()), triple.getRight());
    }

    /**
     * Maps the right value of a Triple using a Function.
     * @param triple the triple to map
     * @param function the function applied to the right value
     * @param <L> the left type
     * @param <M> the middle type
     * @param <RI> the right input type
     * @param <RO> the right output type
     * @return a Triple containing the mapped right value
     */
    public static <L, M, RI, RO> Triple<L, M, RO> mapR(Triple<L, M, RI> triple, Function<RI, RO> function) {
        return Triple.of(triple.getLeft(), triple.getMiddle(), function.apply(triple.getRight()));
    }

}