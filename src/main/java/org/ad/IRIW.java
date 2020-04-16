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
 *   Observed state   Occurrences   Expectation  Interpretation
 *       0, 0, 0, 0    39,610,018    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 0, 1     2,222,159    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 1, 0     2,746,021    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 0, 1, 1     2,598,416    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 0, 0     3,190,742    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 0, 1            80    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 1, 0     3,009,891    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       0, 1, 1, 1       961,365    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 0, 0     1,377,068    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 0, 1     3,168,075    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 0, 1, 1     2,173,106    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 0, 0     2,212,815    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 0, 1     1,499,365    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 1, 0       915,353    ACCEPTABLE  Any reordering or cache coherence effects are possible
 *       1, 1, 1, 1     3,107,477    ACCEPTABLE  Any reordering or cache coherence effects are possible
 */
@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE, desc = "Any reordering or cache coherence effects are possible")
public class IRIW {
    int x, y;

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