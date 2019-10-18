package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@State
public class SynchronizedAreNotBarriers {
    int x, y;
    volatile int b;

    @Actor
    public void actor1() {
        x = 1;
        y = 1;
    }

    @Actor
    public void actor2(III_Result r) {
        r.r1 = y;
        r.r2 = b;
        r.r3 = x;
    }
}
