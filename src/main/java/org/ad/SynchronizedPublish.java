package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * By JMM rules result 0 is possible, but strong x86 model prevents this, though it can be easily reproduced on weaker
 * models like PowerPC, as reading reference and accessing object fields are distinct operations
 *   Observed state   Occurrences              Expectation  Interpretation
 *               -1   147,325,515               ACCEPTABLE  OK
 *               42     1,187,266               ACCEPTABLE  OK
 */
@JCStressTest
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0",expect = Expect.ACCEPTABLE_INTERESTING, desc = "JMM rules imply that result 0 is possible")
@State
public class SynchronizedPublish {
    RacyBoxy boxie = new RacyBoxy();

    @Actor
    void actor() {
        boxie.set(new Box(42));
    }

    @Actor
    void observer(I_Result r) {
        Box t = boxie.get();
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