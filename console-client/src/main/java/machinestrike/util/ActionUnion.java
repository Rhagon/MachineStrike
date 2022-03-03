package machinestrike.util;

import machinestrike.debug.Assert;
import machinestrike.game.action.Action;
import machinestrike.game.rule.RuleViolation;

public class ActionUnion<HandlerTypeA, HandlerTypeB> {

    private HandlerTypeA handlerA;
    private HandlerTypeB handlerB;

    private final Action<HandlerTypeA> actionA;
    private final Action<HandlerTypeB> actionB;

    private final boolean typeA;

    public static <T1, T2> ActionUnion<T1, T2> first(T1 handler, Action<T1> action) {
        return new ActionUnion<>(handler, action, null, null, true);
    }

    public static <T1, T2> ActionUnion<T1, T2> second(T2 handler, Action<T2> action) {
        return new ActionUnion<>(null, null, handler, action, false);
    }

    public static <T1, T2> ActionUnion<T1, T2> first(Action<T1> action) {
        return first(null, action);
    }

    public static <T1, T2> ActionUnion<T1, T2> second(Action<T2> action) {
        return second(null, action);
    }

    private ActionUnion(HandlerTypeA handlerA, Action<HandlerTypeA> actionA, HandlerTypeB handlerB, Action<HandlerTypeB> actionB, boolean typeA) {
        this.handlerA = handlerA;
        this.handlerB = handlerB;
        this.actionA = actionA;
        this.actionB = actionB;
        this.typeA = typeA;
    }

    public void execute() throws RuleViolation {
        if(typeA) {
            Assert.requireNotNull(actionA);
            Assert.requireNotNull(handlerA);
            actionA.execute(handlerA);
        } else {
            Assert.requireNotNull(actionB);
            Assert.requireNotNull(handlerB);
            actionB.execute(handlerB);
        }
    }

    public ActionUnion<HandlerTypeA, HandlerTypeB> firstHandler(HandlerTypeA handler) {
        handlerA = handler;
        return this;
    }

    public ActionUnion<HandlerTypeA, HandlerTypeB> secondHandler(HandlerTypeB handler) {
        handlerB = handler;
        return this;
    }

}
