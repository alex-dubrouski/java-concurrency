package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Let's see")
public class UnsafePublication {
    UnsafeDCLFactory dcl = new UnsafeDCLFactory();

    @Actor
    public void actor1(II_Result r) {
        Singleton s = dcl.get();
        r.r1 = s.x01 + s.x01 + s.x02 + s.x03;
    }

    @Actor
    public void actor2(II_Result r) {
        Singleton s = dcl.get();
        r.r2 = s.x01 + s.x01 + s.x02 + s.x03;
    }

    public class UnsafeDCLFactory {
        private Singleton instance;
        int x = 1;

        public Singleton get() {
            if (instance == null) {  // read 1, check 1
                synchronized (this) {
                    if (instance == null) { // read 2, check 2
                        instance = new Singleton(x);
                    }
                }
            }
            return instance; // read 3
        }
    }
    public static class Singleton {
        final int x00;
        int x01, x02, x03;
        public Singleton(int x) {
            x00 = x;
            x01 = x;
            x02 = x;
            x03 = x;
        }
    }
}
