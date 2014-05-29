import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




//Import pour le driver jdbc pour la connexion à  la BDD
import com.mysql.jdbc.Statement;

public class InteractionBDD{
	// Champs pour la connexion à  la base de données avec le driver jdbc.
	private Connection conn;
	private static String url = "jdbc:mysql://localhost/cave";
    private static String pwd="";
    private static String log="root";
	    
	    public InteractionBDD(){
	    	try {
	            Class.forName("com.mysql.jdbc.Driver");
	            // Connexion à  la base de données à  l'aide du driver jdbc
	            this.conn = DriverManager.getConnection(url, log, pwd);
	    	 } catch (SQLException | ClassNotFoundException ex) {
	             System.out.println(ex.toString());
	         }
	    }
	    
	    public Connection getConnexion(){
	    	return conn;
	    }
	    
	    // Fonction qui nous permet de recupérer dans un ResultSet le resultat de la requête passée en paramètre.
	    public ResultSet MyConnexionSelect(String requete) {
	    	
	    	// On initialise le résultat de la requête à null.
	        ResultSet s = null;
	       
	        // Création et exécution de la requête.
	        Statement statement;
			try {
				statement = (Statement) conn.createStatement();
				s = statement.executeQuery(requete);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // On retourne le résultat de la requête.
	        return s;
	    }

		// Fonction qui renvoie true si la requete s'est exécutée correctement, false sinon.
		public boolean MyConnexionInsertDeleteUpdate(String requete) {
	        try {
	             // Création et exécution de la requête.
	            Statement statement = (Statement) conn.createStatement();
	            statement.executeUpdate(requete);
	         // On retourne true si la requête a fonctionné.
	           return true;
	        } catch (SQLException ex) {
	            System.out.println(ex.toString());
	        }
	    	// On retourne false si la requête n'a pas fonctionné.
	        return false;
		}
}