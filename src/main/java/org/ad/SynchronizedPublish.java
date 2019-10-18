package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@State
public class SynchronizedPublish {
    RacyBoxy boxie = new RacyBoxy();

    @Actor
    void actor() {
        boxie.set(new Box(42)); // set is synchronized
    }

    @Actor
    void observer(I_Result r) {
        Box t = boxie.get(); // get is not synchronized
        if (t != null) {
            r.r1 = t.x;
        } else {
            r.r1 = -1;
        }
    }

    class Box {
        int x;
        public Box(int v) {
            x = v;
        }
    }

    class RacyBoxy {
        Box box;

        public synchronized void set(Box v) {
            box = v;
        }

        public Box get() {
            return box;
        }
    }
}