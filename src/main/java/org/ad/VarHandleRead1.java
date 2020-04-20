package org.ad;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;


@JCStressTest
@State
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Hmm")
public class VarHandleRead1 {
  volatile int ready;
  int dinner;
  static final VarHandle VH;

  static {
    try {
      VH = MethodHandles.lookup().in(VarHandleRead1.class).findVarHandle(VarHandleRead1.class, "ready", int.class);
    } catch (IllegalAccessException | NoSuchFieldException var1) {
      throw new AssertionError(var1);
    }
  }

  @Actor
  public void actor1() {
    dinner = 17;
    VH.set(this, 1);
  }

  @Actor
  public void actor2(II_Result r) {
    final int t = (int)VH.get(this);
    if (t == 1) {
      r.r1 = dinner;
    } else {
      r.r1 = -1;
    }
    r.r2 = t;
  }
}