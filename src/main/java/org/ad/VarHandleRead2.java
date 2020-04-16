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
public class VarHandleRead2 {
  volatile int ready;
  int dinner;
  static VarHandle VH;

  static {
    try {
      VH = MethodHandles.lookup().in(VarHandleRead2.class).findVarHandle(VarHandleRead2.class, "ready", int.class);
    } catch (IllegalAccessException | NoSuchFieldException var1) {
      var1.printStackTrace();
    }
  }

  @Actor
  public void actor1() {
    dinner = 17;
    VH.set(this, 1);
  }

  @Actor
  public void actor2(II_Result r) {
    r.r1 = (int)VH.get(this);
    r.r2 = dinner;
  }
}