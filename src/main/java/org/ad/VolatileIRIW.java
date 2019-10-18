package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IIII_Result;

@JCStressTest
@State
@Outcome(id = "0, 0, 0, 0", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "1, 1, 1, 1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "1, 0, 1, 0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@Outcome(id = "0, 1, 0, 1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@Outcome(id = "1, 0, 0, 1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0, 1, 1, 0", expect = Expect.ACCEPTABLE, desc = "OK")
public class VolatileIRIW {
    volatile int x, y;

    @Actor
    public void actor1() {
        x = 1;
    }

    @Actor
    public void actor2() {
        y = 1;
    }

    @Actor
    public void actor3(IIII_Result r) {
        r.r1 = x;
        r.r2 = y;
    }

    @Actor
    public void actor4(IIII_Result r) {
        r.r3 = y;
        r.r4 = x;
    }
}
