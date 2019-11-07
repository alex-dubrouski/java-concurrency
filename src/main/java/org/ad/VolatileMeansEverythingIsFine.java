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
 * Publishing the reference ot object and reading volatile field are distinct operations and do not prevent race
 * JMM doe snot prohibit result 0. Though I was not able to reproduce on x86
 *   Observed state   Occurrences              Expectation  Interpretation
 *               -1   128,269,174               ACCEPTABLE  OK
 *                0             0   ACCEPTABLE_INTERESTING  JMM does not prohibit this result
 *               42     8,089,587               ACCEPTABLE  OK
 */
@JCStressTest
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "JMM does not prohibit this result")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "OK")
@State
public class VolatileMeansEverythingIsFine {
    static class C {
        volatile int x;
        C() { x = 42; }
    }

    C c;

    @Actor
    void thread1() {
        c = new C();
    }

    @Actor
    void thread2(I_Result r) {
        C c = this.c;
        r.r1 = (c == null) ? -1 : c.x;
    }
}