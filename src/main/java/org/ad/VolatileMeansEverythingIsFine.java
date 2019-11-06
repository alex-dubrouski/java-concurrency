package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * Publishing the reference ot object and reading volatile field are distinct operations and do not prevent race
 * JMM doe snot prohibit result 0. Though I was not able to reproduce on x86
 *   Observed state   Occurrences              Expectation  Interpretation
 *               -1   128,269,174               ACCEPTABLE  OK
 *                0             0   ACCEPTABLE_INTERESTING  JMM does not prohibit this result
 *               42     8,089,587               ACCEPTABLE  OK
 */
@JCStressTest
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "JMM does not prohibit this result")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "OK")
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