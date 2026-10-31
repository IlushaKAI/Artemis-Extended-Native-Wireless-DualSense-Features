import com.limelight.dualsense.NativeFeedbackPolicy;
public class NativeFeedbackPolicyTest {
    private static int checks;
    private static void check(boolean value) { checks++; if (!value) throw new AssertionError("check " + checks); }
    public static void main(String[] args) {
        NativeFeedbackPolicy p = new NativeFeedbackPolicy();
        check(!p.evaluate("auto",true,true,20000,false,40,0));
        check(!p.evaluate("auto",true,true,20000,false,40,19999));
        check(p.evaluate("auto",true,true,20000,false,40,20000));
        check(p.evaluate("auto",true,true,20000,false,130,20500)); // Hysteresis holds when already enabled.
        check(!p.evaluate("auto",true,true,20000,true,40,21000)); // Loss callback stops immediately.
        check(!p.evaluate("auto",true,true,20000,false,40,21500));
        check(!p.evaluate("auto",true,true,20000,false,40,41499));
        check(p.evaluate("auto",true,true,20000,false,40,41500));
        check(!p.evaluate("auto",true,true,11999,false,40,42000));
        check(!p.evaluate("auto",true,true,12000,false,40,43000));
        check(p.evaluate("auto",true,true,12000,false,40,63000));
        check(!p.evaluate("auto",true,true,20000,false,150,64000));
        check(!p.evaluate("auto",true,true,20000,false,125,65000));
        check(!p.evaluate("auto",true,true,20000,false,119,66000));
        check(p.evaluate("auto",true,true,20000,false,119,86000));
        check(!p.evaluate("off",true,true,20000,false,10,87000));
        check(p.evaluate("always",true,true,1000,true,500,88000)); // Explicit override.
        check(!p.evaluate("always",false,true,10000,false,0,89000)); // Master off wins.
        check(!p.evaluate("always",true,false,10000,false,0,90000)); // Route off wins.
        check(!p.evaluate("invalid",true,true,1000,false,-1,91000));
        check(!p.evaluate("auto",true,true,20000,false,-1,92000));
        check(p.evaluate("auto",true,true,20000,false,-1,112000)); // RTT unavailable doesn't block forever.
        check(!p.evaluate("auto",true,true,20000,false,-1,100)); // Monotonic clock reset resets warmup.
        System.out.println("PASS: " + checks + " native feedback quality/budget/hysteresis checks");
    }
}
