package pe.util;

public class IntTuple {
    public final int fst;
    public final int snd;
    public IntTuple(int fst, int snd) {
        this.fst = fst;
        this.snd = snd;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + fst;
        hash = hash * 31 + snd;
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntTuple)) return false;
        if (obj == this) return true;
        IntTuple another = (IntTuple) obj;
        return another.fst == this.fst && another.snd == this.snd;
    }
    @Override public String toString() {
        return String.format("(%d,%d)", this.fst, this.snd);
    }
}