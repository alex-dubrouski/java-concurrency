package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@State
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "HMMMM")
@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "HMMMM")
@Outcome(id = "2", expect = Expect.FORBIDDEN, desc = "HMMMM")
@Outcome(id = "3", expect = Expect.FORBIDDEN, desc = "HMMMM")
@Outcome(id = "4", expect = Expect.ACCEPTABLE, desc = "OK")
public class UnsafeInitialization {
    int x = 1;

    MyObject o; // non-volatile, race

    @Actor
    public void publish() {
        o = new MyObject(x);
    }

    @Actor
    public void consume(I_Result res) {
        //MyObject lo = o;
        if (o != null) {
            res.r1 = o.x00 + o.x01 + o.x02 + o.x03;
        } else {
            res.r1 = -1;
        }
    }

    static class MyObject {
        final int x00;
        int x01, x02, x03;
        public MyObject(int x) {
            x00 = x;
            x01 = x;
            x02 = x;
            x03 = x;
        }
    }
}