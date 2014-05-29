// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

//Import pour les écouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requête faites à  la base de données et pour la gestion des Exceptions
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//Import pour les éléments du JFrame
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

//Import pour le driver jdbc pour la connexion à  la BDD
import com.mysql.jdbc.Statement;



public class FenetreClient extends JFrame{

	private static final long serialVersionUID = 6770640374054147119L;

	// Champs nécessaire pour la recherche de vins
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Année");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	// Champ pour le résultat de la recherche
	JTable tableau;
	
	// Champs pour la connexion à  la base de données avec le driver jdbc.
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
    
	
    // Constructeur par défaut de la fenêtre de Client
   	public FenetreClient() {
   		// On appel le constructeur de JFrame avec un paramètre qui est le nom de la fenêtre
   		super("La cave à vin - Section clients");
   		// Ensuite on initialise cette fenêtre
   		init();
   	}
	
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// On instancie les champs de notre fenetre (JTextField pour lla recherche par année et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On créé le panel principal qui contiendra tous ce qui constitue notre fenêtre
		JPanel panel = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panel.setLayout(new BorderLayout());
		// On met la dimension du panel à la taille de l'écran
		panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

		
		// On créé le panel toptop qui contiendra le bouton se connecter
		JPanel toptop = new JPanel();
		// On lui dit que l'affichage des champs se par région
		toptop.setLayout(new BorderLayout());
				
		// On créé un bouton Se connecter que l'on ajoute à l'est du panel toptop
		JButton connexion = new JButton("Se connecter");
		toptop.add(connexion, BorderLayout.EAST);
		
		// On lui attribue un écouteur
		connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On créé une instance de FenetreConnexion qui est la fenêtre de connexion.
				FenetreConnexion c = new FenetreConnexion();
				c.pack();
				// On place la fenêtre au centre de l'écran
			    c.setLocationRelativeTo(null);
			    // On la rend visible
			    c.setVisible(true);
			}
		});
		
		// On créé le panel top qui contiendra le panel toptop et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par région
		top.setLayout(new BorderLayout());
		
		// On lui ajoute le panel toptop au nord
		top.add(toptop, BorderLayout.NORTH);
		
		// On créé le panel innerTop qui contiendra les champs de recherche de vins
		JPanel innerTop = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerTop.setLayout(new GridLayout(3,3));
		
		// On met la dimension du JTextField associé à recherche par année à  200px de large et 30px de hauteur
		rechercherannee.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par année
		innerTop.add(new JLabel("Année à rechercher :"));
		innerTop.add(rechercherannee);
		innerTop.add(checkannee);
		
		// On met la dimension du JTextField associé à recherche par nom à  200px de large et 30px de hauteur
		recherchernom.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par nom
		innerTop.add(new JLabel("Nom à rechercher :"));
		innerTop.add(recherchernom);
		innerTop.add(checknom);
		
		// On met la dimension du JComboxBox<String> associé à recherche par couleur à  200px de large et 30px de hauteur
		recherchercouleur.setPreferredSize(new Dimension(200,30));
		// On ajoute au JComboBox<String> les différentes couleur du vin
		recherchercouleur.addItem("");
		recherchercouleur.addItem("blanc");
		recherchercouleur.addItem("rose");
		recherchercouleur.addItem("rouge");
		// On ajoute au panel innerTop le champ de recherche par couleur
		innerTop.add(new JLabel("Couleur à rechercher :"));
		innerTop.add(recherchercouleur);
		innerTop.add(checkcouleur);
		
		// On ajoute au panel top le panel innerTop au centre
		top.add(innerTop, BorderLayout.CENTER);
		
		
		// On créé le panel bottom qui contiendra les résultats de la recherche. on lui donne le mot clé final pour pouvoir l'utiliser dans l'écouteur du bouton Rechercher
		final JPanel bottom = new JPanel();
		// On lui dit que l'affichage des champs se par région
		bottom.setLayout(new BorderLayout()); 
		// On chache ce panel
		bottom.setVisible(false);
		
		// On cree un bouton Rechercher
		JButton searchButton = new JButton("Rechercher");
		
		// On lui attribue un écouteur
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On créé un tableau de String pour récupérer les éléments sélectionnés
				String [] t = new String[3];
				// On prépare notre requête de recherche
				String requete = "SELECT * FROM `vins` WHERE";
				
				// Si aucune case n'est cochée c'est que l'utilisateur n'a pas sélectionné d'option de recherche donc on lui affiche un message d'erreur
				if(!(checkannee.isSelected()) && !(checknom.isSelected()) && !(checkcouleur.isSelected())){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une option de recherche.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
				// Si l'option recherche par année est cochée, on regarde si le champs de recherche n'est pas vide.
				if(checkannee.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(rechercherannee.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par année mais vous n'avez pas renseigner de valeur\nVeuillez en renseigner une", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon
					else
					{
						// Si le champs ne contient pas exactement 4 caractères, on affiche une erreur
						if(rechercherannee.getText().length() != 4){
							JOptionPane.showMessageDialog(null, "Veuillez en renseigner une année valide", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						// Sinon on récupère le contenu du champs rechercherannée
						else
						{
							t[0] = rechercherannee.getText();
						}
					}
				}
				
				// Si l'option recherche par nom est cochée, on regarde si le champs de recherche n'est pas vide.
				if(checknom.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchernom.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on récupère le contenu du champs recherchernom
					else
					{
						t[1] = recherchernom.getText();
					}
				}
				
				// Si l'option recherche par couleur est cochée, on regarde si le champs de recherche n'est pas vide.
				if(checkcouleur.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchercouleur.getSelectedItem().toString().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on récupère le contenu du champs recherchercouleur
					else
					{
						t[2] = recherchercouleur.getSelectedItem().toString();
					}
				}
				
				// Si les trois options de recherche sont cochées, on prépare la requête avec les trois paramètres.
				// Si deux des trois options de recherche sont cochées, on prépare la requête avec les deux paramètres cochés.
				// Si l'une des trois options de recherche est cochée, prépare la requête avec le paramètre coché.
				if(checknom.isSelected()){
					requete += " `cepageVin`='" + t[1] + "'";
					if(checkannee.isSelected()){
						requete += " AND `dateVin`=" +  t[0];
						if(checkcouleur.isSelected()){
							requete += " AND `couleur`='" + t[2] + "'";
						}
					}
					else
					{
						if(checkcouleur.isSelected()){
							requete += " AND `couleur`='" + t[2] + "'";
						}
					}
					
				}
				else
				{
					if(checkannee.isSelected()){
						requete += " `dateVin`=" +  t[0];
						if(checkcouleur.isSelected()){
							requete += " AND `couleur`='" + t[2] + "'";
						}
					}
					else
					{
						if(checkcouleur.isSelected()){
							requete += " `couleur`='" + t[2] + "'";
						}
					}
				}
				
				// On récupère le resultat de la requête à l'aide de MyConnexionRecherche(String requête)
				ResultSet resultRequete = MyConnexionRecherche(requete);
				// Bloc try catch pour la gestion des excetions dû à  la requête.
				try {
					// On laisse le panel bottom caché
					bottom.setVisible(false);
					// Si le résultat de la requête contient une ou des données
					if((resultRequete.isBeforeFirst())){
						int i = 0;
						// On créé un tableau donnees qui récupèrera toutes les données du résultat de la requête.
						// Pour cela on créé un tableau de String à deux dimensions de 25 lignes et de 6 colonnes.
						String[][] 	donnees = new String[25][6];
						// Tant qu'il y a une donnée, on enregistre ses champs.
						while(resultRequete.next()) {
							donnees[i][0] = resultRequete.getString("regionVin");
							donnees[i][1] = resultRequete.getString("domaineVin");
							donnees[i][2] = resultRequete.getString("châteauVin");
							donnees[i][3] = resultRequete.getString("couleur");
							donnees[i][4] = resultRequete.getString("cepageVin");
							donnees[i][5] = resultRequete.getString("dateVin") + "\n";
							i++;
						}
						// On créé un tableau entetes qui contient toutes les entêtes du résultat de la requête.
						String[] entetes = {"Region", "Domaine", "Château", "Couleur", "Cépage", "Année"};
						// On créé un nouveau JTable avec les données récupérées.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant les résultats de la recherche.
						tableau.setEnabled(false);;
					}
					else
					{
						// On créé le tableau de string à deux dimensions qui contient Nous n'avons trouvé aucun vin correspondant à votre recherche
						String[][] 	donnees = new String[1][1];
						donnees[0][0] = "Nous n'avons trouvé aucun vin correspondant à votre recherche";
						// On créé un tableau entetes qui contient NoResultFound.
						String[] entetes = {"NoResultFound"};
						// On créé un nouveau JTable avec ces données.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant ces données.
						tableau.setEnabled(false);;
					}
					// On fait appel à rafraichirData(JPanel bottom, JTable tableau) pour rafraîchir les données d'une recherche à l'autre.
					rafraichirData(bottom, tableau);
					// Ensuite on affiche le panel bottom.
					bottom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// On au panel top le bouton searchButton au sud
		top.add(searchButton, BorderLayout.SOUTH);
		
		// On ajoute au panel principal le panel top au nord
		panel.add(top, BorderLayout.NORTH);
		
		// On ajoute au panel principal le panel bottom au centre
		panel.add(bottom, BorderLayout.CENTER);
		
		// On ajoute le panel principal à la fenêtre
		getContentPane().add(panel);
	}
	
	// Fonction qui nous permet de recupérer dans un ResultSet le resultat de la requête passée en paramètre.
	private ResultSet MyConnexionRecherche(String requete) {
    	
    	// On initialise le résultat de la requête à null.
        ResultSet s = null;
        
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

	// Fonction qui nous permet d'actualiser l'affichage du résultat de la requête
	public void rafraichirData(JPanel p, JTable o){
			// On enlève tous les éléments du panel bottom
			p.removeAll();
			// On lui ajoute au nord les entêtes du tableau
			p.add(o.getTableHeader(), BorderLayout.NORTH);
			// On lui ajoute au centre les données du résultat de la requête
			p.add(o, BorderLayout.CENTER);
	}

		// Class main qui créé et affiche la fenêtre principale de notre application
		public static void main(String[] args) {
			// On créé une instance de FenetreClient qui est la fenêtre des clients.
			FenetreClient fc = new FenetreClient();
			fc.pack();
			// On place la fenêtre au centre de l'écran
			fc.setLocationRelativeTo(null);
			// On la rend visible
			fc.setVisible(true);
					
		}
}