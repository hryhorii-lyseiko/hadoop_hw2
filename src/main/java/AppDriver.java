import combiner.ByteCombiner;
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
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import reducer.ByteReducer;

public class AppDriver extends Configured {

    public static void main(String[] args) throws Exception {
        Configuration c = new Configuration();

        if(args[2].equals("seq")) {
            c.set("mapred.compress.map.output", "true");
            c.set("mapred.map.output.compression.codec", "org.apache.hadoop.io.compress.SnappyCodec");
        }

        if(args[2].equals("csv"))
            c.set("mapred.textoutputformat.separator", ",");

        Path input = new Path(args[0]);
        Path output = new Path(args[1]);
        Job j = new Job(c, "Byte task");
        j.setJarByClass(AppDriver.class);
        j.setMapperClass(ByteMapper.class);
        j.setCombinerClass(ByteCombiner.class);
        j.setReducerClass(ByteReducer.class);

        j.setMapOutputKeyClass(IntWritable.class);
        j.setMapOutputValueClass(IpTextWriteble.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(Text.class);

        j.setNumReduceTasks(1);


        j.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(j, input);
        if(args[2].equals("seq")){
            j.setOutputFormatClass(SequenceFileOutputFormat.class);
            SequenceFileOutputFormat.setOutputPath(j, output);

        }else if(args[2].equals("csv")){
            j.setOutputFormatClass(TextOutputFormat.class);

            FileOutputFormat.setOutputPath(j,output);
        }

        System.exit(j.waitForCompletion(true) ? 0 : 1);

    }
}