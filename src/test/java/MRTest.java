import combiner.ByteCombiner;
import custom_type.IpTextWriteble;
import mapper.ByteMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;
import reducer.ByteReducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MRTest  {

    MapDriver<LongWritable, Text, IntWritable, IpTextWriteble> mapDriver;
    ReduceDriver<IntWritable, IpTextWriteble, Text, Text> reduceDriver;
    ReduceDriver<IntWritable , IpTextWriteble, IntWritable , IpTextWriteble> combineDriver;
    MapReduceDriver<LongWritable, Text, IntWritable, IpTextWriteble, Text, Text> mapReduceDriver;

    @Before
    public void setUp() {
        ByteMapper mapper = new  ByteMapper();
        ByteReducer reducer = new ByteReducer();
        ByteCombiner combiner = new ByteCombiner();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        combineDriver = ReduceDriver.newReduceDriver(combiner);
    }

    @Test
    public void testCombiner() throws IOException {
        final int key = 19;
        List<IpTextWriteble> values = new ArrayList<>();
        values.add(new IpTextWriteble(5,500));
        values.add(new IpTextWriteble(1,320));
        combineDriver.withInput(new IntWritable(key), values);
        combineDriver.withOutput(new IntWritable(key),new IpTextWriteble(6,820));
        combineDriver.runTest();
    }

    @Test
    public void testMapper() throws IOException {
        final String testLine = "ip3 - - [24/Apr/2011:04:22:45 -0400] \"GET /personal/vanagon_1.jpg HTTP/1.1\" 200 72209 \"http://www.inetgiant.in/addetails/1985-vw-vanagon-transporter-single-cab-sinka-diesel-truck-2wd-rhd/3235819\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; WOW64; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C)\"";
        mapDriver.withInput(new LongWritable(0), new Text(
                testLine));
        mapDriver.withOutput(new IntWritable(3), new IpTextWriteble(1 , 72209));
        mapDriver.runTest();
    }

    @Test
    public void testReducer() throws IOException {
        int key = 19;
        List<IpTextWriteble> values = new ArrayList<>();
        values.add(new IpTextWriteble(5,500));
        values.add(new IpTextWriteble(1,340));
        reduceDriver.withInput(new IntWritable(key), values);
        reduceDriver.withOutput(new Text("ip " + key ),new Text( 140.0 + "," + 840));
        reduceDriver.runTest();
    }

    @Test
    public void testMapReduce() throws IOException {
        final String testLine = "ip3 - - [24/Apr/2011:04:22:45 -0400] \"GET /personal/vanagon_1.jpg HTTP/1.1\" 200 72209 \"http://www.inetgiant.in/addetails/1985-vw-vanagon-transporter-single-cab-sinka-diesel-truck-2wd-rhd/3235819\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; WOW64; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C)\"";
        mapReduceDriver.withInput(new LongWritable(1), new Text(new String(testLine)));
        mapReduceDriver.withOutput(new Text("ip 3"),new Text(72209.0 + "," + 72209));
        mapReduceDriver.runTest();

    }

}