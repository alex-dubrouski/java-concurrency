package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 *   Observed state   Occurrences   Expectation  Interpretation
 *                0             0     FORBIDDEN  Forbidden by happens before order
 *               41             0     FORBIDDEN  Forbidden by happens before order
 *               42    17,798,714    ACCEPTABLE  Happens before order
 *               43    69,923,167    ACCEPTABLE  Race
 */

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "Forbidden by happens before order")
@Outcome(id = "41", expect = Expect.FORBIDDEN, desc = "Forbidden by happens before order")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "Happens before order")
@Outcome(id = "43", expect = Expect.ACCEPTABLE, desc = "Race")
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
