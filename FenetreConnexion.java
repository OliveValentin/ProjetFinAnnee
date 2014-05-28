import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


public class FenetreConnexion extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField login;
	private JTextField password;
	
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
	
	public FenetreConnexion() {
		super("Fenetre de connexion");
		init();
	}
	
	
	private void init(){
		
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		//setDefaultCloseOperation(EXIT_ON_CLOSE);

		// On instancie les champs de notre fenetre (JTextField pour le login et JPasswordField pour le password)
		login = new JTextField();
		password = new JPasswordField();

		// On créé le panel principal celui qui contiendra tous ce qui constitue notre fenetre
		JPanel panel = new JPanel();
		
		// On lui dit que c'est une grille avec 0 ligne et 2 colonnes
		panel.setLayout(new GridLayout(0, 2));
		panel.setPreferredSize(new Dimension(300, 80));

		// On ajoute le champs Login :
		panel.add(new JLabel("Login :"));
		panel.add(login);
		
		// On ajoute le champs password
		panel.add(new JLabel("Password :"));
		panel.add(password);
		
		
		// On cree un bouton Valider
		JButton validateButton = new JButton("Valider");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si c'est un client ou un marchand de vin)
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On récupère les champs
				String [] t = login(login.getText(), ((JPasswordField) password).getPassword());
				String idUser = "0";
				ResultSet resultRequete;
				
				// Si les champs sont non vide.
				if(!(t[0].equals("")) && !(t[1].equals(""))){
					// On récupère les résultats de la requête.
					resultRequete = MyConnexion(t[0], t[1]);
					try {
						// Si nous avons un resultat c'est que le marchand de vins à entrer le bon mot de passe.
						while(resultRequete.next()){
							idUser = resultRequete.getString("identifiantUtilisateur");
						}
						if(idUser != "0") {
							JOptionPane.showMessageDialog(null, "Vous allez être redirigé vers la page des admins.\n","Information", JOptionPane.INFORMATION_MESSAGE);
							FenetreAdmin fa = new FenetreAdmin(idUser);
							fa.pack();
							fa.setLocationRelativeTo(null);
							fa.setVisible(true);
							setVisible(false);
						}
						// Sinon, le mot de passe est erroné ou l'utilisateur n'existe pas.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est erroné ou vous n'êtes pas marchand.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							password.setText("");
						}
					} catch (HeadlessException | SQLException e) {
						e.printStackTrace();
					}
				}
				// 1 des deux champs ou les deux ne sont pas renseigner
				else if (t[0].equals("") || t[1].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les champs demandés pour la connexion.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		});
		
		// On ajoute le bouton Valider au panel principal
		panel.add(validateButton );
		
		
		// On créé un bouton Annuler
		JButton cancelButton = new JButton("Annuler");
		
		// On lui attribut un écouteur (si on clique sur annuler les champs sont RAZ et on ferme la fenetre).
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				login.setText("");
				password.setText("");
				setVisible(false);
			}
		});
		
		// On ajoute le bouton annuler au panel principal
		panel.add(cancelButton);

		// On ajoute a la fenetre le panel principal
		getContentPane().add(panel);
	}
	
	// Fonction qui nous permet d'afficher le contenu des champs login et password
	private String[] login(String username, char[] passwordChars){
		String[] s = new String[2];
		String password = "";
		for(char c: passwordChars){
			password+=c;
		}
		
		s[0] = username;
		s[1] = password;
 		return s;
	}
     
 
    private ResultSet MyConnexion(String name, String mdp) {
    	
    	// Resultat de la requête.
        ResultSet s = null;
        
        // Mise en forme de la requête vue que nous enregistrons les mots de passe en encodant en MD5
        String requete = "SELECT * FROM utilisateur WHERE nomUtilisateur='" + name + "' AND motDePasse=MD5('" + mdp + "')";
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            // Connexion à la base de données
            conn = DriverManager.getConnection(url, log, pwd);
           
            // Création et exécution de la requête.
            Statement statement = (Statement) conn.createStatement();
            s = statement.executeQuery(requete);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    	// On retourne le résultat de la requête pour pouvoir faire le traitement en conséquence.
        return s;
    }
}