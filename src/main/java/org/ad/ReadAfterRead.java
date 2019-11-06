package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 0    68,626,406   ACCEPTABLE_INTERESTING  Races are still possible
 *             0, 1       314,597   ACCEPTABLE_INTERESTING  Races are still possible
 *             1, 0             0                FORBIDDEN  Happens before order prohibits this, we observe read(x,1)...
 *             1, 1    37,293,198   ACCEPTABLE_INTERESTING  Races are still possible
 */
@JCStressTest
@Outcome(id = "1, 0",expect = Expect.FORBIDDEN, desc = "Happens before order prohibits this, we observe read(x,1), if read(g,1)")
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Races are still possible")
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