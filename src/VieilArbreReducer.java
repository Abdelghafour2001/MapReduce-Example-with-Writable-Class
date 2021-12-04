import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class VieilArbreReducer
        extends Reducer<Text, ObjetWritable, Text, Text>
{
    private Text cleS;
    private Text valeurS = new Text();

    @Override
    public void reduce(Text cleI, Iterable<ObjetWritable> valeursI, Context context)
            throws IOException, InterruptedException
    {
        // définir la clé de sortie
        // cle hia genre and valeur ikun objet fih age et arron
 cleS=cleI;
        // calculer la valeur de sortie
        int res = 0;
        ObjetWritable obj = new ObjetWritable();
        for(ObjetWritable valeurI : valeursI) {
        	int val = valeurI.getAge();
        	if(obj.getAge()<val) {
        		obj.setAge(val);
        		obj.setArron(valeurI.getArron());
        	}
        }
       valeurS.set(obj.toString());

        // émettre une paire (clé, valeur)
        context.write(cleS, valeurS);
    }
}
