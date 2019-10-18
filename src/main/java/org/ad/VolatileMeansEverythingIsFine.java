package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@State
public class VolatileMeansEverythingIsFine {
    static class C {
        volatile int x;
        C() { x = 42; }
    }

    C c;

    @Actor
    void thread1() {
        c = new C();
    }

    @Actor
    void thread2(I_Result r) {
        C c = this.c;
        r.r1 = (c == null) ? -1 : c.x;
    }
}