package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "42, 0", expect = Expect.FORBIDDEN, desc = "Hmm")
@Outcome(id = "0, 42", expect = Expect.FORBIDDEN, desc = "Hmm")
@Outcome(id = "42, 42", expect = Expect.FORBIDDEN, desc = "Hmm")
@State
public class Oota {
    int a = 0, b = 0;

    @Actor
    public void actor1(II_Result r) {
        r.r1 = a;
        if (r.r1 != 0) {
            b = 42;
        }
    }

    @Actor
    public void actor2(II_Result r) {
        r.r2 = b;
        if (r.r2 != 0) {
            a = 42;
        }
    }
}
