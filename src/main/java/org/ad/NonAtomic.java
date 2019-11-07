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
 *               10        84,637   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               11       120,742   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               12       172,543   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               13       266,244   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               14       421,387   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               15       747,821   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               16     1,097,878   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               17     1,081,693   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               18     2,600,772   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               19     2,683,562   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *               20    12,844,774   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                4             2   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                5             4   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                6            10   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                7         2,125   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                8        10,695   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 *                9        26,732   ACCEPTABLE_INTERESTING  1 to 20 is possible due to non-atomic operations on volat...
 */
@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "1 to 20 is possible due to non-atomic operations on volatile field")
public class NonAtomic {
    volatile int x;

    @Actor
    void actor1() {
        for (int i = 0; i < 10; i++) {
            x++;
        }
    }

    @Actor
    void actor2() {
        for (int i = 0; i < 10; i++) {
            x++;
        }
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = x;
    }
}
