package machinestrike.debug;

import machinestrike.game.rule.RuleViolation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Assert {

    public enum Level {
        ignore((message) -> {}),
        warn(System.err::println),
        warn_and_explain(x -> {
            System.err.println(x);
            new RuleViolation(x).printStackTrace(System.err);
        }),
        severe((message) -> {throw new AssertionError(message);});

        private interface FailHandler {
            void handle(@NotNull String message);
        }

        @NotNull
        private final FailHandler handler;

        Level(@NotNull FailHandler handler) {
            this.handler = handler;
        }

        public void handle(String message) {
            handler.handle(message);
        }
    }

    private static Level level = Level.severe;

    public static void setLevel(Level level) {
        Assert.level = level;
    }

    public static void range(int min, int value, int max) {
        range(min, value, max, value + "Failed assertion: " + value + " ∈ [" + min + ", " + max + "]", level);
    }

    public static void range(int min, int value, int max, @NotNull String message, @NotNull Level level) {
        if(value < min || value > max) {
            level.handle(message);
        }
    }

    public static void lessOrEqual(int a, int b) {
        lessOrEqual(a, b, "Failed assertion: " + a + " ≤ " + b, level);
    }

    public static void lessOrEqual(int a, int b, @NotNull String message, @NotNull Level level) {
        if(a > b) {
            level.handle(message);
        }
    }

    public static void less(int a, int b) {
        less(a, b, "Failed assertion: " + a + " < " + b, level);
    }

    public static void less(int a, int b, @NotNull String message, @NotNull Level level) {
        if(a >= b) {
            level.handle(message);
        }
    }

    public static <T> void equal(T a, T b) {
        equal(a, b, "Failed assertion: " + a + " \u2261 " + b, level);
    }

    public static <T> void equal(T a, T b, @NotNull String message, @NotNull Level level) {
        if(a == null) {
            if(b != null) {
                level.handle(message);
            }
        } else if(!a.equals(b)) {
            level.handle(message);
        }
    }

    public static <T> void condition(Predicate<T> predicate, T value, @NotNull String message) {
        condition(predicate, value, message, level);
    }

    public static <T> void condition(Predicate<T> predicate, T value, @NotNull String message, @NotNull Level level) {
        if(!predicate.test(value)) {
            level.handle(message);
        }
    }

    public static <T, U> void condition(BiPredicate<T, U> predicate, T a, U b, @NotNull String message) {
        condition(predicate, a, b, message, level);
    }

    public static <T, U> void condition(BiPredicate<T, U> predicate, T a, U b, @NotNull String message, @NotNull Level level) {
        if(!predicate.test(a, b)) {
            level.handle(message);
        }
    }

    @Contract("null -> fail")
    public static <T> void requireNotNull(T t) {
        notNull(t);
    }

    public static <T> void notNull(T t) {
        notNull(t, level);
    }

    public static <T> void notNull(T t, Level level) {
        notNull(t, "Failed assertion: object is not null", level);
    }

    public static <T> void notNull(T t, @NotNull String message, @NotNull Level level) {
        if (t == null) {
            level.handle(message);
        }
    }

    public static <T> void isNull(T t) {
        isNull(t, "Failed assertion: object is null", level);
    }

    public static <T> void isNull(T t, String message, Level level) {
        if(t != null) {
            level.handle(message);
        }
    }

    @Contract("!null -> fail")
    public static <T> void requireNull(T t) {
        isNull(t);
    }

    public static <T> void same(T t1, T t2, String message, Level level) {
        if(t1 != t2) {
            level.handle(message);
        }
    }

    public static <T> void same(T t1, T t2) {
        same(t1, t2, "Assertion failed: " + t1 + " = " + t2, level);
    }

    public interface CrashableRunnable {
        void run() throws Throwable;
    }

    public static void requireNoThrow(CrashableRunnable run) {
        try {
            run.run();
        } catch(Throwable t) {
            Level.severe.handle("Unexpected throw: " + t);
        }
    }
}
