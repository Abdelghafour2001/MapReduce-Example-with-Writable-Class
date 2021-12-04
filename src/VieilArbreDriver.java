import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class VieilArbreDriver
    extends Configured
    implements Tool
{
    @Override
    public int run(String[] args) throws Exception
    {
        // temps de démarrage
        Long initTime = System.currentTimeMillis();

        // vérifier les paramètres
        if (args.length != 2) {
            System.err.println("Usage: VieilArbreDriver <inputpath> <outputpath>");
            System.exit(-1);
        }

        // créer le job map-reduce
        Configuration conf = this.getConf();
        Job job = Job.getInstance(conf, "VieilArbre Job");
        job.setJarByClass(VieilArbreDriver.class);

        // définir la classe Mapper et la classe Reducer
        job.setMapperClass(VieilArbreMapper.class);
        /*job.setCombinerClass(VieilArbreCombiner.class);*/
        job.setReducerClass(VieilArbreReducer.class);

        // définir les données d'entrée : TextInputFormat => clés=LongWritable, valeurs=Text
        FileInputFormat.setInputDirRecursive(job, false);       // mettre true si les fichiers sont dans des sous-dossiers
        FileInputFormat.addInputPath(job, new Path(args[0]));   /** VOIR CONFIG ENTREES DANS LE MAKEFILE **/
        job.setInputFormatClass(TextInputFormat.class);

        // sorties du mapper = entrées du reducer et entrées et sorties du combiner
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ObjetWritable.class);

        // définir les données de sortie : dossier et types des clés et valeurs
        FileOutputFormat.setOutputPath(job, new Path(args[1])); /** VOIR CONFIG SORTIE DANS LE MAKEFILE **/
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // lancer le job et attendre sa fin
        Long startTime = System.currentTimeMillis();
        boolean success = job.waitForCompletion(true);
        Long endTime = System.currentTimeMillis();
        System.out.println("Job Duration seconds: " + ((endTime-startTime)/1000L));
        System.out.println("Total Duration seconds: " + ((endTime-initTime)/1000L));
        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception
    {
        // préparer et lancer un job
        VieilArbreDriver driver = new VieilArbreDriver();
        int exitCode = ToolRunner.run(driver, args);
        System.exit(exitCode);
    }
}
