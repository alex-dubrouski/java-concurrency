/*
 * Copyright (c) 2017, Red Hat Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
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