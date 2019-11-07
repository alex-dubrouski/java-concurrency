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
 * By JMM rules result 0 is possible, but strong x86 model prevents this, though it can be easily reproduced on weaker
 * models like PowerPC, as reading reference and accessing object fields are distinct operations
 *   Observed state   Occurrences              Expectation  Interpretation
 *               -1   147,325,515               ACCEPTABLE  OK
 *               42     1,187,266               ACCEPTABLE  OK
 */
@JCStressTest
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "OK")
@Outcome(id = "0",expect = Expect.ACCEPTABLE_INTERESTING, desc = "JMM rules imply that result 0 is possible")
@State
public class SynchronizedPublish {
    RacyBoxy boxie = new RacyBoxy();

    @Actor
    void actor() {
        boxie.set(new Box(42));
    }

    @Actor
    void observer(I_Result r) {
        Box t = boxie.get();
        if (t != null) {
            r.r1 = t.x;
        } else {
            r.r1 = -1;
        }
    }

    class Box {
        int x;
        public Box(int v) {
            x = v;
        }
    }

    class RacyBoxy {
        Box box;

        public synchronized void set(Box v) {
            box = v;
        }

        public Box get() {
            return box;
        }
    }
}