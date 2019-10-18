package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "OK")
@Outcome(id = "41", expect = Expect.ACCEPTABLE_INTERESTING, desc = "OK")
@Outcome(id = "42", expect = Expect.ACCEPTABLE_INTERESTING, desc = "OK")
@Outcome(id = "43", expect = Expect.ACCEPTABLE_INTERESTING, desc = "OK")
public class VolatileRead {
    int a = 0;
    volatile boolean ready = false;

    @Actor
    public void actor1() {
        a = 41;
        a = 42;
        ready = true;
        a = 43;
    }
    @Actor
    public void actor2(I_Result r) {
        while (!ready) {}
        r.r1 = a;
    }
}
