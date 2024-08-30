package elections;

public class DHondtMethod extends DivisorMethod {

    @Override
    public String toString() {
        return "D'Hondt Method\n";
    }

    @Override
    protected int getDivisor() {
        return 1;
    }
}
