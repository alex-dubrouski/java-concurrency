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
import org.openjdk.jcstress.infra.results.II_Result;

/**
 * Initializing thread observes proper state, while second thread observes what ever luck gives it
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 4        11,567   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             1, 4         6,604   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             2, 4         4,041   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             4, 0        11,401   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             4, 1         7,450   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             4, 2         4,111   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 *             4, 4    30,166,987   ACCEPTABLE_INTERESTING  Everything is wrong with this DCL
 */
@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Everything is wrong with this DCL")
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
        private Singleton instance; // non-volatile, race
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
        int x00, x01, x02, x03; //non-final fields, no memory barrier in the end of constructor
        public Singleton(int x) {
            x00 = x;
            x01 = x;
            x02 = x;
            x03 = x;
        }
    }
}
