package reducer;

import custom_type.IpTextWriteble;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ByteReducer extends Reducer<IntWritable, IpTextWriteble, IntWritable, Text> {

    @Override
    public void reduce(IntWritable key, Iterable<IpTextWriteble> values, Context context)
            throws IOException, InterruptedException {
        int total = 0;
        int count = 0;

        for (IpTextWriteble value : values) {
            count += value.getCount();
            total += value.getTotal();
        }

        int avg = total / count;
        context.write(key, new Text(avg + "," + total));
    }

}
