package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@State
public class ReadAfterRead {
    int x;
    volatile int g;

    @Actor
    void actor1() {
        x = 1;
        g = 1;
    }

    @Actor
    void actor2(II_Result r) {
        r.r1 = g;
        r.r2 = x;
    }
}