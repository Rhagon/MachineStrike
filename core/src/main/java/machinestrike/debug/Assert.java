package machinestrike.debug;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class Assert {

    public enum Level {
        IGNORE((message) -> {}),
        WARN(System.err::println),
        WARN_AND_TRACK(x -> {
            System.err.println(x);
            new AssertionError(x).printStackTrace(System.err);
        }),
        SEVERE((message) -> {throw new AssertionError(message);});

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

    private static final Level DEFAULT_LEVEL = Level.WARN;
    private static final Stack<Level> stack = new Stack<>();

    public static void push(@NotNull Level newLevel) {
        stack.push(newLevel);
    }

    @NotNull
    public static Level pop() {
        return stack.pop();
    }

    @NotNull
    public static Level level() {
        if(stack.empty()) {
            return DEFAULT_LEVEL;
        }
        return stack.peek();
    }

    public static void range(int min, int value, int max) {
        if(value < min || value > max) {
            level().handle(value + "Failed assertion: " + value + " ∈ [" + min + ", " + max + "]");
        }
    }

    public static void lessOrEqual(int a, int b) {
        if(a > b) {
            level().handle("Failed assertion: " + a + " ≤ " + b);
        }
    }

    public static void less(int a, int b) {
        if(a >= b) {
            level().handle("Failed assertion: " + a + " < " + b);
        }
    }

    public static <T> void equal(T a, T b) {
        String message = "Failed assertion: " + a + " \u2261 " + b;
        if(a == null) {
            if(b != null) {
                level().handle(message);
            }
        } else if(!a.equals(b)) {
            level().handle(message);
        }
    }

    public static <T> void same(T t1, T t2) {
        if(t1 != t2) {
            level().handle("Failed assertion: " + t1 + " = " + t2);
        }
    }

    public static <T> void notNull(T t) {
        if (t == null) {
            level().handle("Failed assertion: <object> ≠ null");
        }
    }

    public static <T> void isNull(T t) {
        if(t != null) {
            level().handle("Failed assertion: " + t + " = null");
        }
    }

    public static void isTrue(boolean bool) {
        if(!bool) {
            level().handle("Failed assertion: value is true");
        }
    }

    @Contract("null -> fail; !null -> !null")
    public static <T> T requireNotNull(T t) {
        if(t == null) {
            Level.SEVERE.handle("Requirement not met: <object> ≠ null");
        }
        return t;
    }

    @Contract("!null -> fail")
    public static <T> void requireNull(T t) {
        if(t != null) {
            Level.SEVERE.handle("Requirement not met: " + t + " = null");
        }
    }

    public interface UnsafeRunnable {
        void run() throws Throwable;
    }

    public static void requireNoThrow(@NotNull UnsafeRunnable run) {
        try {
            run.run();
        } catch(Throwable t) {
            Level.SEVERE.handle("Requirement not met: No throw: " + t.getMessage() + "\n");
        }
    }
}
