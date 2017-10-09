import combiner.ByteCombiber;
import custom_type.IpTextWriteble;
import mapper.ByteMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import reducer.ByteReducer;

public class App extends Configured {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        conf.set("mapred.compress.map.output","true");
        conf.set("mapred.map.output.compression.codec","org.apache.hadoop.io.compress.SnappyCodec");

        Job job = Job.getInstance(conf);
        job.setJarByClass(App.class);
        job.setJobName("ip parser");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);

        job.setOutputFormatClass(TextOutputFormat.class);
      // job.setOutputFormatClass(SequenceFileOutputFormat.class);
      // SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK);
      // SequenceFileOutputFormat.setCompressOutput(job, true);

        job.setMapperClass(ByteMapper.class);
        job.setCombinerClass(ByteCombiber.class);
        job.setReducerClass(ByteReducer.class);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IpTextWriteble.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}