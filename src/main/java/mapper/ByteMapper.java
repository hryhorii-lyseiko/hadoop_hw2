package mapper;

import custom_type.IpTextWriteble;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import eu.bitwalker.useragentutils.UserAgent;

import java.io.IOException;

public class ByteMapper extends Mapper<LongWritable, Text, IntWritable, IpTextWriteble> {



    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        try {
            String line = value.toString();

            int ip = Integer.valueOf(line.substring(2, line.indexOf(" ")));

            int bytesStartPos = line.indexOf("\" ") + 6;

            if (bytesStartPos > 6) {
                String bytesTransfered = line.substring(bytesStartPos, line.indexOf(" ", bytesStartPos));
                int clientStartPos = line.indexOf("\" \"") + 3;

                int bytes = 0;
                if (!"-".equals(bytesTransfered)) {
                    bytes = Integer.valueOf(bytesTransfered);
                }

                String clientString = line.substring(clientStartPos, line.length());
                context.getCounter(UserAgent.parseUserAgentString(clientString).getBrowser()).increment(1);

                System.out.println(UserAgent.parseUserAgentString(clientString).getBrowser());
                context.write(new IntWritable(ip), new IpTextWriteble(1, bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}

