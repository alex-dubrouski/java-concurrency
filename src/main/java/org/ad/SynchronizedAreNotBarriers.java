package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 0     8,229,476   ACCEPTABLE_INTERESTING  Unobserved synchronized blocks do not imply any ordering ...
 *             0, 1        80,408   ACCEPTABLE_INTERESTING  Unobserved synchronized blocks do not imply any ordering ...
 *             1, 0            17   ACCEPTABLE_INTERESTING  Unobserved synchronized blocks do not imply any ordering ...
 *             1, 1   154,956,000   ACCEPTABLE_INTERESTING  Unobserved synchronized blocks do not imply any ordering ...
 */
@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Unobserved synchronized blocks do not imply any ordering of reads")
@State
public class SynchronizedAreNotBarriers {
    int x, y;

    @Actor
    public void actor1() {
        x = 1;
        synchronized (new Object()) {}
        y = 1;
    }

    @Actor
    public void actor2(II_Result r) {
        r.r1 = y;
        synchronized (new Object()) {}
        r.r2 = x;
    }
}
