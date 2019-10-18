package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
@State
public class SafePublication {
    public class A {
        int f;
        A() { f = 42; }
    }
    A a;

    @Actor
    public void actor1() {
        a = new A();
    }

    @Actor
    public void actor2(I_Result r) {
        if (a != null) {
            r.r1 = a.f;
        }
    }
}
