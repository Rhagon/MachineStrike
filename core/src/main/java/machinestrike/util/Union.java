package machinestrike.util;

import java.util.function.Consumer;

public class Union<A, B> {

    public static <A, B> Union<A, B> first(A a, Consumer<A> aCons) {
        return new Union<>(a, null, aCons, null, false);
    }

    public static <A, B> Union<A, B> second(B b, Consumer<B> bCons) {
        return new Union<>(null, b, null, bCons, true);
    }

    private A a;
    private B b;

    private Consumer<A> aCons;
    private Consumer<B> bCons;

    private boolean isB;

    private Union(A a, B b, Consumer<A> aCons, Consumer<B> bCons, boolean isB) {
        this.a = a;
        this.b = b;
        this.aCons = aCons;
        this.bCons = bCons;
        this.isB = isB;
    }

    public void setFirst(A a) {
        this.a = a;
        this.b = null;
        isB = false;
    }

    public void setSecond(B b) {
        this.b = b;
        this.a = null;
        isB = true;
    }

    public Union<A, B> firstConsumer(Consumer<A> aCons) {
        this.aCons = aCons;
        return this;
    }

    public Union<A, B> secondConsumer(Consumer<B> bCons) {
        this.bCons = bCons;
        return this;
    }

    public void process() {
        if(isB) {
            bCons.accept(b);
        } else {
            aCons.accept(a);
        }
    }

}
