package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
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
