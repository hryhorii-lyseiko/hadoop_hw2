package combiner;

import custom_type.IpTextWriteble;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class  ByteCombiber  extends Reducer<IntWritable, IpTextWriteble, IntWritable, IpTextWriteble> {

    @Override
    public void reduce(IntWritable key, Iterable<IpTextWriteble> values, Context context)
            throws IOException, InterruptedException {
        int total = 0;
        int count = 0;

        for (IpTextWriteble value : values) {
            count += value.getCount();
            total += value.getTotal();
        }

        context.write(key, new IpTextWriteble(count, total));
    }

}
