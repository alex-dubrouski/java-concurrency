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

import java.util.concurrent.atomic.AtomicInteger;

/**
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 0    31,708,924   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             0, 1        41,160   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             1, 0         1,734   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 *             1, 1   116,110,963   ACCEPTABLE_INTERESTING  Independent synchronized blocks do not imply any ordering...
 */
@JCStressTest
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Independent synchronized blocks do not imply any ordering of reads")
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
