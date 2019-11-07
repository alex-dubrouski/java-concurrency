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
import org.openjdk.jcstress.infra.results.IIII_Result;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *       0, 0, 0, 0    52,474,571   ACCEPTABLE_INTERESTING  OK
 *       0, 0, 0, 1     1,876,008   ACCEPTABLE_INTERESTING  OK
 *       0, 0, 1, 0     1,645,615   ACCEPTABLE_INTERESTING  OK
 *       0, 0, 1, 1     1,238,948   ACCEPTABLE_INTERESTING  OK
 *       0, 1, 0, 0     1,928,399   ACCEPTABLE_INTERESTING  OK
 *       0, 1, 0, 1            52   ACCEPTABLE_INTERESTING  OK
 *       0, 1, 1, 0     2,553,879   ACCEPTABLE_INTERESTING  OK
 *       0, 1, 1, 1       244,738   ACCEPTABLE_INTERESTING  OK
 *       1, 0, 0, 0     1,660,231   ACCEPTABLE_INTERESTING  OK
 *       1, 0, 0, 1     2,283,172   ACCEPTABLE_INTERESTING  OK
 *       1, 0, 1, 0             0                FORBIDDEN  Forbidden by JMM, as it emits proper memory barriers
 *       1, 0, 1, 1       271,167   ACCEPTABLE_INTERESTING  OK
 *       1, 1, 0, 0     1,266,364   ACCEPTABLE_INTERESTING  OK
 *       1, 1, 0, 1       274,328   ACCEPTABLE_INTERESTING  OK
 *       1, 1, 1, 0       174,503   ACCEPTABLE_INTERESTING  OK
 *       1, 1, 1, 1     1,429,196   ACCEPTABLE_INTERESTING  OK
 */
@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "OK")
@Outcome(id = "1, 0, 1, 0", expect = Expect.FORBIDDEN, desc = "Forbidden by JMM, as it emits proper memory barriers")
public class VolatileIRIW {
    volatile int x, y;

    @Actor
    public void actor1() {
        x = 1;
    }

    @Actor
    public void actor2() {
        y = 1;
    }

    @Actor
    public void actor3(IIII_Result r) {
        r.r1 = x;
        r.r2 = y;
    }

    @Actor
    public void actor4(IIII_Result r) {
        r.r3 = y;
        r.r4 = x;
    }
}
