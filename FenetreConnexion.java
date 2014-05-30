
// Import pour l'affichage du JFrame et des JPanel
import java.awt.Dimension;
import java.awt.GridLayout;

// Import pour les ecouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import pour les requete faites a  la base de donnees et pour la gestion des Exceptions

import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.HeadlessException;

// Import pour les elements du JFrame
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FenetreConnexion extends JFrame{

	private static final long serialVersionUID = -8259698762583706502L;
	
	// Champs necessaire pour la connexion d'un marchand de vin
	private JTextField login;
	private JTextField password;
	
	private InteractionBDD bdd = new InteractionBDD();
	
    // Constructeur par defaut de la fenetre de Connexion
	public FenetreConnexion() {
		// On appel le constructeur de JFrame avec un parametre qui est le nom de la fenetre
		super("Fenetre de connexion");
		// Ensuite on initialise cette fenetre
		init();
	}
	
	
	private void init(){
		
		// On instancie les champs de notre fenetre (JTextField pour le login et JPasswordField pour le password)
		login = new JTextField();
		password = new JPasswordField();

		// On cree le panel principal celui qui contiendra tous ce qui constitue notre fenetre
		JPanel panel = new JPanel();
		
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 2 colonnes
		panel.setLayout(new GridLayout(3, 2));
		// On met la dimension du panel a  300px de large et 80px de hauteur
		panel.setPreferredSize(new Dimension(300, 80));

		// On ajoute le champs Login :
		panel.add(new JLabel("Login :"));
		panel.add(login);
		
		// On ajoute le champs password
		panel.add(new JLabel("Password :"));
		panel.add(password);
		
		
		// On cree un bouton Valider
		JButton validateButton = new JButton("Valider");
		
		// On lui attribue un ecouteur
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				// On recupere les champs entres par l'utilisateur a  l'aide de la fonction login(String log, char[] pass)
				String [] t = login(login.getText(), ((JPasswordField) password).getPassword());
				// On instancie l'identifiant de l'utilisateur a  0
				String idUser = "0";
				
				// On cree un ResultSet qui contiendra les resultats de la requete
				ResultSet resultRequete;
				
				// Si au moins un des deux champs n'est pas renseigne, on affiche une erreur
				if (t[0].isEmpty() || t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les champs demandes pour la connexion.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
					
				}
				else
				// Sinon tous les champs sont non vide.
				{	
					// On recupere les resultats de la requete a  l'aide de la fonction MyConnexion(String nom, String mdp).
					String requete = "SELECT * FROM utilisateur WHERE nomUtilisateur='" + t[0] + "' AND motDePasse=MD5('" + t[1] + "')";
			    	
					resultRequete = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions du a  la requete.
					try {
						while(resultRequete.next()){
							idUser = resultRequete.getString("identifiantUtilisateur");
						}
						// Si l'idUser est different de 0 c'est que nous avons eu un marchand de vin qui demande a  se connecter
						if(idUser != "0") {
							// On affiche un message de redirection
							JOptionPane.showMessageDialog(null, "Vous allez etre redirige vers la page des admins.\n","Information", JOptionPane.INFORMATION_MESSAGE);
							
							// On cree une instance de FenetreAdmin qui est la fenetre des marchands de vins.
							FenetreAdmin fa = new FenetreAdmin(idUser);
							fa.pack();
							// On place la fenetre au centre de l'ecran
							fa.setLocationRelativeTo(null);
							// On la rend visible
							fa.setVisible(true);
							// On cache la fenetre de connexion
							setVisible(false);
						}
						// Sinon, le mot de passe est errone ou l'utilisateur n'existe pas.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est errone ou vous n'etes pas marchand.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							// On remet les champs a  vide.
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
		
		
		// On cree un bouton Annuler
		JButton cancelButton = new JButton("Annuler");
		
		// On lui attribut un ecouteur
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On remet les champs a vide
				login.setText("");
				password.setText("");
				// On cache la fenetre pour afficher la FenetreClient qui est la fenetre principale de notre application.
				setVisible(false);
			}
		});
		
		// On ajoute le bouton annuler au panel principal
		panel.add(cancelButton);

		// On ajoute a la fenetre le panel principal
		getContentPane().add(panel);
	}
	
	// Fonction qui nous permet de recuperer dans un tableau de String le contenu des champs login et password
	private String[] login(String username, char[] passwordChars){
		// On cree notre tableau de String
		String[] s = new String[2];
		String password = "";
		// On recupere le password
		for(char c: passwordChars){
			password+=c;
		}
		
		// On les ajoute au tableau
		s[0] = username;
		s[1] = password;
		
		// On renvoie ce tableau
		return s;
	}
}