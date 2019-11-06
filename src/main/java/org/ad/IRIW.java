package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IIII_Result;

/**
 *   Observed state   Occurrences   Expectation  Interpretation
 *       0, 0, 0, 0    39,610,018    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 0, 1     2,222,159    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 1, 0     2,746,021    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 1, 1     2,598,416    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 0, 0     3,190,742    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 0, 1            80    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 1, 0     3,009,891    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 1, 1       961,365    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 0, 0     1,377,068    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 0, 1     3,168,075    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 1, 1     2,173,106    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 0, 0     2,212,815    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 0, 1     1,499,365    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 1, 0       915,353    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 1, 1     3,107,477    ACCEPTABLE  Any reordering or cache coherence effects are possible
 */
@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE, desc = "Any reordering or cache coherence effects are possible")
public class IRIW {
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