package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 0    31,708,924   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             0, 1        41,160   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             1, 0         1,734   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             1, 1   116,110,963   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 */
@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Independent synchronized blocks do not imply any ordering of reads")
@State
public class OrderOfReads {
    int x, y;
    AtomicInteger a;

    @Actor
    void actor() {
        synchronized(this) {
            x = 1;
        }
        synchronized(this) {
            y = 1;
        }
    }

    @Actor
    void observer(II_Result r) {
        r.r1 = y;
        r.r2 = x;
    }
}
