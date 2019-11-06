package org.ad;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *               -1   108,469,043               ACCEPTABLE  Reference is not initialized
 *                0             0   ACCEPTABLE_INTERESTING  Surprise, initializer thread still haven't finished
 *                1             0   ACCEPTABLE_INTERESTING  Surprise, initializer thread still haven't finished
 *                2           645   ACCEPTABLE_INTERESTING  Surprise, initializer thread still haven't finished
 *                3         2,036   ACCEPTABLE_INTERESTING  Surprise, initializer thread still haven't finished
 *                4    27,923,937               ACCEPTABLE  Object is fully initialized
 */
@JCStressTest
@State
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "Reference is not initialized")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Surprise, initializer thread still haven't finished")
@Outcome(id = "1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Surprise, initializer thread still haven't finished")
@Outcome(id = "2", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Surprise, initializer thread still haven't finished")
@Outcome(id = "3", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Surprise, initializer thread still haven't finished")
@Outcome(id = "4", expect = Expect.ACCEPTABLE, desc = "Object is fully initialized")
public class UnsafeInitialization {
    int x = 1;

    MyObject o; // non-volatile, race

    @Actor
    public void publish() {
        o = new MyObject(x);
    }

    @Actor
    public void consume(I_Result res) {
        MyObject lo = o;
        if (lo != null) {
            res.r1 = lo.x00 + lo.x01 + lo.x02 + lo.x03;
        } else {
            res.r1 = -1;
        }
    }

    static class MyObject {
        int x00, x01, x02, x03; //non-final fields, no memory barrier in the end of constructor
        public MyObject(int x) {
            x00 = x;
            x01 = x;
            x02 = x;
            x03 = x;
        }
    }
}