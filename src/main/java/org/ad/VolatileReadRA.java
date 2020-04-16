package org.ad;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;


/**
 *   Observed state   Occurrences   Expectation  Interpretation
 *                0             0     FORBIDDEN  Forbidden by happens before order
 *               41             0     FORBIDDEN  Forbidden by happens before order
 *               42       151,446    ACCEPTABLE  Happens before order
 *               43   106,822,415    ACCEPTABLE  Race
 */

@JCStressTest
@State
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "Forbidden by happens before order")
@Outcome(id = "41", expect = Expect.FORBIDDEN, desc = "Forbidden by happens before order")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "Happens before order")
@Outcome(id = "43", expect = Expect.ACCEPTABLE, desc = "Race")
public class VolatileReadRA {
  int a = 0;
  boolean ready = false;
  static VarHandle VH;

  static {
    try {
      VH = MethodHandles.lookup().in(VolatileReadRA.class).findVarHandle(VolatileReadRA.class, "ready", boolean.class);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Actor
  public void actor1() {
    a = 41;
    a = 42;
    VH.setRelease(this, true);
    a = 43;
  }
  @Actor
  public void actor2(I_Result r) {
    while (!(boolean)VH.getAcquire(this)) {}
    r.r1 = a;
  }
}
