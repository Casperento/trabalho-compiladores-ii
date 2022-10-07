package Temp;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class LabelList extends LinkedList<Label> {
    public Label head;
    public LabelList tail;
    public LabelList(Label h, LabelList t) {
        head=h;
        tail=t;
    }

    @Override
    public void replaceAll(UnaryOperator<Label> operator) {
        super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Label> c) {
        super.sort(c);
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return super.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super Label> filter) {
        return super.removeIf(filter);
    }

    @Override
    public Stream<Label> stream() {
        return super.stream();
    }

    @Override
    public Stream<Label> parallelStream() {
        return super.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super Label> action) {
        super.forEach(action);
    }
}

