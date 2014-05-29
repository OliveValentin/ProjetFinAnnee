import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




//Import pour le driver jdbc pour la connexion � la BDD
import com.mysql.jdbc.Statement;

public class InteractionBDD{
	// Champs pour la connexion � la base de donn�es avec le driver jdbc.
	private Connection conn;
	private static String url = "jdbc:mysql://localhost/cave";
    private static String pwd="";
    private static String log="root";
	    
	    public InteractionBDD(){
	    	try {
	            Class.forName("com.mysql.jdbc.Driver");
	            // Connexion � la base de donn�es � l'aide du driver jdbc
	            this.conn = DriverManager.getConnection(url, log, pwd);
	    	 } catch (SQLException | ClassNotFoundException ex) {
	             System.out.println(ex.toString());
	         }
	    }
	    
	    public Connection getConnexion(){
	    	return conn;
	    }
	    
	    // Fonction qui nous permet de recup�rer dans un ResultSet le resultat de la requ�te pass�e en param�tre.
	    public ResultSet MyConnexionSelect(String requete) {
	    	
	    	// On initialise le r�sultat de la requ�te � null.
	        ResultSet s = null;
	       
	        // Cr�ation et ex�cution de la requ�te.
	        Statement statement;
			try {
				statement = (Statement) conn.createStatement();
				s = statement.executeQuery(requete);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // On retourne le r�sultat de la requ�te.
	        return s;
	    }

		// Fonction qui renvoie true si la requete s'est ex�cut�e correctement, false sinon.
		public boolean MyConnexionInsertDeleteUpdate(String requete) {
	        try {
	             // Cr�ation et ex�cution de la requ�te.
	            Statement statement = (Statement) conn.createStatement();
	            statement.executeUpdate(requete);
	         // On retourne true si la requ�te a fonctionn�.
	           return true;
	        } catch (SQLException ex) {
	            System.out.println(ex.toString());
	        }
	    	// On retourne false si la requ�te n'a pas fonctionn�.
	        return false;
		}
}