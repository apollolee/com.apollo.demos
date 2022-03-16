/*
 * 此代码创建于 2022年2月4日 下午9:21:06。
 */
package com.apollo.demos.base.fsm.squirrel;

import static com.apollo.demos.base.fsm.squirrel.TestFsm.Event.ToB;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.Event.ToC;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.Event.ToD;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.State.A;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.State.B;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.State.C;
import static com.apollo.demos.base.fsm.squirrel.TestFsm.State.D;
import static org.squirrelframework.foundation.fsm.StateMachineBuilderFactory.create;
import static org.squirrelframework.foundation.fsm.StateMachineStatus.TERMINATED;
import static org.squirrelframework.foundation.fsm.TransitionPriority.HIGH;

import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

public class TestFsm {

    enum State {
        A, B, C, D
    }

    enum Event {
        ToA, ToB, ToC, ToD
    }

    public static class Context {

        public int m_value = 0;

        public Throwable m_ex = null;

        @Override
        public String toString() {
            return "context.value:" + m_value + ",context.ex:" + m_ex;
        }

    }

    static class Fsm extends AbstractStateMachine<Fsm, State, Event, Context> {

        void show(String method, State from, State to, Event event, Context context) {
            System.out.println("method:" + method + ",state:" + getCurrentState() + ",from:" + from + ",to:" + to + ",event:" + event + "," + context + ",fsm.state:" + getStatus());
        }

        protected void a2b(State from, State to, Event event, Context context) {
            show("a2b", from, to, event, context);
            context.m_value++;
            fire(ToC, context);
        }

        protected void b2c(State from, State to, Event event, Context context) {
            show("b2c", from, to, event, context);
            context.m_value++;
            fire(ToD, context);
        }

        protected void c2d(State from, State to, Event event, Context context) {
            show("c2d", from, to, event, context);
            context.m_value++;
            throw new IllegalAccessError("c2d error!");
        }

        protected void inB(State from, State to, Event event, Context context) {
            show("inB", from, to, event, context);
            context.m_value++;
        }

        protected void b2b(State from, State to, Event event, Context context) {
            show("b2b", from, to, event, context);
            context.m_value++;
            fire(ToC, context);
        }

        protected void outB(State from, State to, Event event, Context context) {
            show("outB", from, to, event, context);
            context.m_value++;
        }

        @Override
        protected void afterTransitionCausedException(State fromState, State toState, Event event, Context context) {
            context.m_ex = getLastException().getTargetException();
            setStatus(TERMINATED);
        }

    }

    static void show(Fsm fsm, Context context) {
        System.out.println("state:" + fsm.getCurrentState() + ",fsm.state:" + fsm.getStatus() + "," + context);
    }

    public static void main(String[] args) {
        StateMachineBuilder<Fsm, State, Event, Context> builder = create(Fsm.class, State.class, Event.class, Context.class);
        builder.externalTransition().from(A).to(B).on(ToB).callMethod("a2b");
        builder.externalTransition().from(B).to(C).on(ToC).whenMvel("b2c:::(context!=null && context.m_value>10)").callMethod("b2c");
        builder.externalTransition().from(C).to(D).on(ToD).callMethod("c2d");

        builder.onEntry(B).callMethod("inB");
        builder.internalTransition(HIGH).within(B).on(ToC).whenMvel("b2b:::(context!=null && context.m_value<=10)").callMethod("b2b");
        builder.onExit(B).callMethod("outB");

        Context context = new Context();
        Fsm fsm = builder.newStateMachine(A);
        show(fsm, context);

        fsm.start();
        show(fsm, context);

        fsm.fire(ToB, context);
        show(fsm, context);

        fsm.terminate();
        show(fsm, context);
    }

}
