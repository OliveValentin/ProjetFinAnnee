import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




//Import pour le driver jdbc pour la connexion a  la BDD
import com.mysql.jdbc.Statement;

public class InteractionBDD{
	// Champs pour la connexion a  la base de donnees avec le driver jdbc.
	private Connection conn;
	private static String url = "jdbc:mysql://localhost/cave";
    private static String pwd="";
    private static String log="root";
	    
	    public InteractionBDD(){
	    	try {
	            Class.forName("com.mysql.jdbc.Driver");
	            // Connexion a  la base de donnees a  l'aide du driver jdbc
	            this.conn = DriverManager.getConnection(url, log, pwd);
	    	 } catch (SQLException | ClassNotFoundException ex) {
	             System.out.println(ex.toString());
	         }
	    }
	    
	    public Connection getConnexion(){
	    	return conn;
	    }
	    
	    // Fonction qui nous permet de recuperer dans un ResultSet le resultat de la requete passee en parametre.
	    public ResultSet MyConnexionSelect(String requete) {
	    	
	    	// On initialise le resultat de la requete a null.
	        ResultSet s = null;
	       
	        // Creation et execution de la requete.
	        Statement statement;
			try {
				statement = (Statement) conn.createStatement();
				s = statement.executeQuery(requete);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // On retourne le resultat de la requete.
	        return s;
	    }

		// Fonction qui renvoie true si la requete s'est executee correctement, false sinon.
		public boolean MyConnexionInsertDeleteUpdate(String requete) {
	        try {
	             // Creation et execution de la requete.
	            Statement statement = (Statement) conn.createStatement();
	            statement.executeUpdate(requete);
	         // On retourne true si la requete a fonctionne.
	           return true;
	        } catch (SQLException ex) {
	            System.out.println(ex.toString());
	        }
	    	// On retourne false si la requete n'a pas fonctionne.
	        return false;
		}
}