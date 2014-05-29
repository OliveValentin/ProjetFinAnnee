
// Import pour l'affichage du JFrame et des JPanel
import java.awt.Dimension;
import java.awt.GridLayout;

// Import pour les écouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import pour les requête faites à  la base de données et pour la gestion des Exceptions
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.HeadlessException;

// Import pour les éléments du JFrame
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Import pour le driver jdbc pour la connexion à  la BDD
import com.mysql.jdbc.Statement;


public class FenetreConnexion extends JFrame{

	private static final long serialVersionUID = -8259698762583706502L;
	
	// Champs nécessaire pour la connexion d'un marchand de vin
	private JTextField login;
	private JTextField password;
	
	// Champs pour la connexion à  la base de données avec le driver jdbc.
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
	
    // Constructeur par défaut de la fenêtre de Connexion
	public FenetreConnexion() {
		// On appel le constructeur de JFrame avec un paramètre qui est le nom de la fenêtre
		super("Fenetre de connexion");
		// Ensuite on initialise cette fenêtre
		init();
	}
	
	
	private void init(){
		
		// On instancie les champs de notre fenetre (JTextField pour le login et JPasswordField pour le password)
		login = new JTextField();
		password = new JPasswordField();

		// On créé le panel principal celui qui contiendra tous ce qui constitue notre fenetre
		JPanel panel = new JPanel();
		
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 2 colonnes
		panel.setLayout(new GridLayout(3, 2));
		// On met la dimension du panel à  300px de large et 80px de hauteur
		panel.setPreferredSize(new Dimension(300, 80));

		// On ajoute le champs Login :
		panel.add(new JLabel("Login :"));
		panel.add(login);
		
		// On ajoute le champs password
		panel.add(new JLabel("Password :"));
		panel.add(password);
		
		
		// On cree un bouton Valider
		JButton validateButton = new JButton("Valider");
		
		// On lui attribue un écouteur
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				// On récupère les champs entrés par l'utilisateur à  l'aide de la fonction login(String log, char[] pass)
				String [] t = login(login.getText(), ((JPasswordField) password).getPassword());
				// On instancie l'identifiant de l'utilisateur à  0
				String idUser = "0";
				// On crée un ResultSet qui contiendra les résultats de la requête
				ResultSet resultRequete;
				
				// Si au moins un des deux champs n'est pas renseigné, on affiche une erreur
				if (t[0].isEmpty() || t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les champs demandés pour la connexion.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else
				// Sinon tous les champs sont non vide.
				{
					
					// On récupère les résultats de la requête à  l'aide de la fonction MyConnexion(String nom, String mdp).
					resultRequete = MyConnexion(t[0], t[1]);
					// Bloc try catch pour la gestion des excetions dû à  la requête.
					try {
						while(resultRequete.next()){
							idUser = resultRequete.getString("identifiantUtilisateur");
						}
						// Si l'idUser est différent de 0 c'est que nous avons eu un marchand de vin qui demande à  se connecter
						if(idUser != "0") {
							// On affiche un message de redirection
							JOptionPane.showMessageDialog(null, "Vous allez être redirigé vers la page des admins.\n","Information", JOptionPane.INFORMATION_MESSAGE);
							
							// On créé une instance de FenetreAdmin qui est la fenêtre des marchands de vins.
							FenetreAdmin fa = new FenetreAdmin(idUser);
							fa.pack();
							// On place la fenêtre au centre de l'écran
							fa.setLocationRelativeTo(null);
							// On la rend visible
							fa.setVisible(true);
							// On cache la fenêtre de connexion
							setVisible(false);
						}
						// Sinon, le mot de passe est erroné ou l'utilisateur n'existe pas.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est erroné ou vous n'êtes pas marchand.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							// On remet les champs à  vide.
							login.setText("");
							password.setText("");
						}
					} catch (HeadlessException | SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		// On ajoute le bouton Valider au panel principal
		panel.add(validateButton);
		
		
		// On créé un bouton Annuler
		JButton cancelButton = new JButton("Annuler");
		
		// On lui attribut un écouteur
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On remet les champs à vide
				login.setText("");
				password.setText("");
				// On cache la fenêtre pour afficher la FenetreClient qui est la fenêtre principale de notre application.
				setVisible(false);
			}
		});
		
		// On ajoute le bouton annuler au panel principal
		panel.add(cancelButton);

		// On ajoute a la fenetre le panel principal
		getContentPane().add(panel);
	}
	
	// Fonction qui nous permet de recupérer dans un tableau de String le contenu des champs login et password
	private String[] login(String username, char[] passwordChars){
		// On créé notre tableau de String
		String[] s = new String[2];
		String password = "";
		// On récupère le password
		for(char c: passwordChars){
			password+=c;
		}
		
		// On les ajoute au tableau
		s[0] = username;
		s[1] = password;
		
		// On renvoie ce tableau
		return s;
	}
     
    private ResultSet MyConnexion(String name, String mdp) {
    	
    	// On initialise le résultat de la requête à  null.
        ResultSet s = null;
        
        // Mise en forme de la requête vue que nous enregistrons les mots de passe en encodant en MD5 
        String requete = "SELECT * FROM utilisateur WHERE nomUtilisateur='" + name + "' AND motDePasse=MD5('" + mdp + "')";
    	
        // Bloc try catch pour la gestion d'Exception par rapport de la connexion à  l'aide du driver ou par rapport à  la requête SQL.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Connexion à  la base de données à  l'aide du driver jdbc
            conn = DriverManager.getConnection(url, log, pwd);
           
            // Création et exécution de la requête.
            Statement statement = (Statement) conn.createStatement();
            s = statement.executeQuery(requete);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    	// On retourne le résultat de la requête.
        return s;
    }
}