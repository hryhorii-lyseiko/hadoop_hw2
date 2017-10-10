package custom_type;


import org.apache.hadoop.io.WritableComparable;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IpTextWriteble  implements WritableComparable<IpTextWriteble> {



    private int first;
    private int second;

    public IpTextWriteble() {
    }

    public IpTextWriteble(IpTextWriteble v) {
        this.first = v.first;
        this.second = v.second;
    }

    public IpTextWriteble(int first, int second) {
        set(first, second);
    }

    public void set(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(first);
        out.writeInt(second);
    }

    public void readFields(DataInput in) throws IOException {
        first = in.readInt();
        second = in.readInt();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IpTextWriteble))
            return false;
        IpTextWriteble other = (IpTextWriteble) o;


        return (this.first == other.first && this.second == other.second);
    }

    @Override
    public int hashCode() {
        return first;
    }



    public int compareTo(IpTextWriteble o) {
        int thisFirstValue = this.first;
        int thatFirstValue = o.first;
        int thisSecondValue = this.second;
        int thatSecondValue = o.second;

        if (thisFirstValue < thatFirstValue)
            return -1;

        if (thisFirstValue > thatFirstValue)
            return 1;

        return (thisSecondValue < thatSecondValue ? -1 : (thisSecondValue == thatSecondValue ? 0 : 1));

    }

    @Override
    public String toString() {
        return Integer.toString(first) + "," + Integer.toString(second);
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

}