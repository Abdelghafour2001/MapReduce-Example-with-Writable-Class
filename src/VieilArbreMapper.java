import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class VieilArbreMapper
        extends Mapper<LongWritable, Text, Text, ObjetWritable>
{
    private Text cleI = new Text();

    @Override
    public void map(LongWritable cleE, Text valeurE, Context context)
            throws IOException, InterruptedException
    {

           try {
            String ligne = valeurE.toString();
            if(cleE.get() == 0L) return;
	    Arbre.fromLine(ligne);
	     ObjetWritable obj = new ObjetWritable(Arbre.getAnnee(),Arbre.getArron());    
            cleI.set(Arbre.getGenre());
            context.write(cleI, obj);
        } catch (Exception e) {
            // ignorer la donnée d'entrée
            // e.printStackTrace();
        }

    }
}
