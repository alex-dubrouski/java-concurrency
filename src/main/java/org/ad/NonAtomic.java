package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
public class NonAtomic {
    volatile int x;

    @Actor
    void actor1() {
        for (int i = 0; i < 10; i++) {
            x++;
        }
    }

    @Actor
    void actor2() {
        for (int i = 0; i < 10; i++) {
            x++;
        }
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = x;
    }
}
