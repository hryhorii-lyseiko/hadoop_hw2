package custom_type;

import org.apache.hadoop.io.Writable;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IpTextWriteble implements Writable {

    private int count;
    private int total;

    public IpTextWriteble() {
    }


    public IpTextWriteble(int first, int second) {
        set(first, second);
    }

    public void set(int count, int second) {
        this.count = count;
        this.total = second;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(count);
        out.writeInt(total);
    }

    public void readFields(DataInput in) throws IOException {
        count = in.readInt();
        total = in.readInt();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IpTextWriteble))
            return false;
        IpTextWriteble other = (IpTextWriteble) o;


        return (this.count == other.count && this.total == other.total);
    }

    @Override
    public int hashCode() {
        return count;
    }


    @Override
    public String toString() {
        return Integer.toString(count) + "," + Integer.toString(total);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}