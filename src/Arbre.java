
public abstract class Arbre
{
    static String[] champs;

    public static void fromLine(String ligne)
    {
        champs = ligne.split(";");
    }

    public static int getAnnee() 
    {
    	try {
    		  return(int) Double.parseDouble(champs[5]);
		} catch (Exception e) {
			return 0;
		}
      
    }
    public static int getHauteur() 
    {
    	try {
    		return (int) Double.parseDouble(champs[6]);
		} catch (Exception e) {
			return 0;
		}
        
    }
    public static String getGenre() 
    {
    	try {
    		return champs[2];
		} catch (Exception e) {
			return null;
		}
        
    }
    public static int getArron() 
    {
    	try {
    		return (int) Double.parseDouble(champs[1]);
		} catch (Exception e) {
			return 0;
		}
        
    }
    public static int getAge() 
    {
    	try {
    		return 2021-Integer.parseInt(champs[1]);
		} catch (Exception e) {
			return 0;
		}
        
    }

    
}

