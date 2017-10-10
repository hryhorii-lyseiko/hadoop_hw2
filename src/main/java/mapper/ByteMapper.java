package mapper;

import custom_type.IpTextWriteble;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import eu.bitwalker.useragentutils.UserAgent;
import util.IpParser;

import java.io.IOException;


public class ByteMapper extends Mapper<LongWritable, Text, IntWritable, IpTextWriteble> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {



            IpParser parser = new IpParser();
            parser.matchLine(value.toString());

            int ip = Integer.valueOf(parser.getIp().substring(2, parser.getIp().length()));

            int bytes = parser.getBytes();

            String browser = parser.getUserAgent();
            context.getCounter(UserAgent.parseUserAgentString(browser).getBrowser()).increment(1);

        if (bytes != 0)
            context.write(new IntWritable(ip), new IpTextWriteble(1, bytes));




    }

}

