// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

//Import pour les ecouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requete faites a  la base de donnees et pour la gestion des Exceptions
import java.sql.ResultSet;


//Import pour les elements du JFrame
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;



public class FenetreClient extends JFrame{

	private static final long serialVersionUID = 6770640374054147119L;

	// Champs necessaire pour la recherche de vins
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Annee");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	
	private InteractionBDD bdd = new InteractionBDD();
	
	// Champ pour le resultat de la recherche
	JTable tableau;
		
    // Constructeur par defaut de la fenetre de Client
   	public FenetreClient() {
   		// On appel le constructeur de JFrame avec un parametre qui est le nom de la fenetre
   		super("La cave a vin - Section clients");
   		// Ensuite on initialise cette fenetre
   		init();
   	}
	
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// On instancie les champs de notre fenetre (JTextField pour lla recherche par annee et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On cree le panel principal qui contiendra tous ce qui constitue notre fenetre
		JPanel panel = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panel.setLayout(new BorderLayout());
		// On met la dimension du panel a la taille de l'ecran
		panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
		// On cree le panel toptop qui contiendra le bouton se connecter
		JPanel toptop = new JPanel();
		// On lui dit que l'affichage des champs se par region
		toptop.setLayout(new BorderLayout());
				
		// On cree un bouton Se connecter que l'on ajoute a l'est du panel toptop
		JButton connexion = new JButton("Se connecter");
		toptop.add(connexion, BorderLayout.EAST);
		
		// On lui attribue un ecouteur
		connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cree une instance de FenetreConnexion qui est la fenetre de connexion.
				FenetreConnexion c = new FenetreConnexion();
				c.pack();
				// On place la fenetre au centre de l'ecran
			    c.setLocationRelativeTo(null);
			    // On la rend visible
			    c.setVisible(true);
			}
		});
		
		// On ajoute à la fenêtre le panel toptop au nord
		add(toptop, BorderLayout.NORTH);
				
				
		// On cree le panel top qui contiendra le panel toptop et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par region
		top.setLayout(new BorderLayout());
		
		// On cree le panel innerTop qui contiendra les champs de recherche de vins
		JPanel innerTop = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerTop.setLayout(new GridLayout(3,3));
		
		// On met la dimension du JTextField associe a recherche par annee a  200px de large et 30px de hauteur
		rechercherannee.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par annee
		innerTop.add(new JLabel("Annee a rechercher :"));
		innerTop.add(rechercherannee);
		innerTop.add(checkannee);
		
		// On met la dimension du JTextField associe a recherche par nom a  200px de large et 30px de hauteur
		recherchernom.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par nom
		innerTop.add(new JLabel("Nom a rechercher :"));
		innerTop.add(recherchernom);
		innerTop.add(checknom);
		
		// On met la dimension du JComboxBox<String> associe a recherche par couleur a  200px de large et 30px de hauteur
		recherchercouleur.setPreferredSize(new Dimension(200,30));
		// On ajoute au JComboBox<String> les differentes couleur du vin
		recherchercouleur.addItem("");
		recherchercouleur.addItem("blanc");
		recherchercouleur.addItem("rose");
		recherchercouleur.addItem("rouge");
		// On ajoute au panel innerTop le champ de recherche par couleur
		innerTop.add(new JLabel("Couleur a rechercher :"));
		innerTop.add(recherchercouleur);
		innerTop.add(checkcouleur);
		
		// On ajoute au panel top le panel innerTop au centre
		top.add(innerTop, BorderLayout.CENTER);
		
		
		// On cree le panel bottom qui contiendra les resultats de la recherche. on lui donne le mot cle final pour pouvoir l'utiliser dans l'ecouteur du bouton Rechercher
		final JPanel bottom = new JPanel();
		// On lui dit que l'affichage des champs se par region
		bottom.setLayout(new BorderLayout()); 
		// On chache ce panel
		bottom.setVisible(false);
		
		// On cree un bouton Rechercher
		JButton searchButton = new JButton("Rechercher");
		
		// On lui attribue un ecouteur
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cree un tableau de String pour recuperer les elements selectionnes
				String [] t = new String[3];
				// On prepare notre requete de recherche
				String requete = "SELECT * FROM `vins` WHERE";
				
				// Si aucune case n'est cochee c'est que l'utilisateur n'a pas selectionne d'option de recherche donc on lui affiche un message d'erreur
				if(!(checkannee.isSelected()) && !(checknom.isSelected()) && !(checkcouleur.isSelected())){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une option de recherche.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
				// Si l'option recherche par annee est cochee, on regarde si le champs de recherche n'est pas vide.
				if(checkannee.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(rechercherannee.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par annee mais vous n'avez pas renseigner de valeur\nVeuillez en renseigner une", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon
					else
					{
						// Si le champs ne contient pas exactement 4 caracteres, on affiche une erreur
						if(rechercherannee.getText().length() != 4){
							JOptionPane.showMessageDialog(null, "Veuillez en renseigner une annee valide", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						// Sinon on recupere le contenu du champs rechercherannee
						else
						{
							t[0] = rechercherannee.getText();
						}
					}
				}
				
				// Si l'option recherche par nom est cochee, on regarde si le champs de recherche n'est pas vide.
				if(checknom.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchernom.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on recupere le contenu du champs recherchernom
					else
					{
						t[1] = recherchernom.getText();
					}
				}
				
				// Si l'option recherche par couleur est cochee, on regarde si le champs de recherche n'est pas vide.
				if(checkcouleur.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchercouleur.getSelectedItem().toString().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on recupere le contenu du champs recherchercouleur
					else
					{
						t[2] = recherchercouleur.getSelectedItem().toString();
					}
				}
				
				// Si les trois options de recherche sont cochees, on prepare la requete avec les trois parametres.
				// Si deux des trois options de recherche sont cochees, on prepare la requete avec les deux parametres coches.
				// Si l'une des trois options de recherche est cochee, prepare la requete avec le parametre coche.
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
				
				// On recupere le resultat de la requete a l'aide de MyConnexionRecherche(String requete)
				ResultSet resultRequete = bdd.MyConnexionSelect(requete);
				// Bloc try catch pour la gestion des excetions du a  la requete.
				try {
					// On laisse le panel bottom cache
					bottom.setVisible(false);
					// Si le resultat de la requete contient une ou des donnees
					if((resultRequete.isBeforeFirst())){
						int i = 0;
						// On cree un tableau donnees qui recuperera toutes les donnees du resultat de la requete.
						// Pour cela on cree un tableau de String a deux dimensions de 25 lignes et de 6 colonnes.
						String[][] 	donnees = new String[25][6];
						// Tant qu'il y a une donnee, on enregistre ses champs.
						while(resultRequete.next()) {
							donnees[i][0] = resultRequete.getString("regionVin");
							donnees[i][1] = resultRequete.getString("domaineVin");
							donnees[i][2] = resultRequete.getString("châteauVin");
							donnees[i][3] = resultRequete.getString("couleur");
							donnees[i][4] = resultRequete.getString("cepageVin");
							donnees[i][5] = resultRequete.getString("dateVin") + "\n";
							i++;
						}
						// On cree un tableau entetes qui contient toutes les entetes du resultat de la requete.
						String[] entetes = {"Region", "Domaine", "Chateau", "Couleur", "Cepage", "Annee"};
						// On cree un nouveau JTable avec les donnees recuperees.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant les resultats de la recherche.
						tableau.setEnabled(false);;
					}
					else
					{
						// On cree le tableau de string a deux dimensions qui contient Nous n'avons trouve aucun vin correspondant a votre recherche
						String[][] 	donnees = new String[1][1];
						donnees[0][0] = "Nous n'avons trouve aucun vin correspondant a votre recherche";
						// On cree un tableau entetes qui contient NoResultFound.
						String[] entetes = {"NoResultFound"};
						// On cree un nouveau JTable avec ces donnees.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant ces donnees.
						tableau.setEnabled(false);;
					}
					// On fait appel a rafraichirData(JPanel bottom, JTable tableau) pour rafraichir les donnees d'une recherche a l'autre.
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
		
		// On ajoute le panel principal a la fenetre
		getContentPane().add(panel);
	}

	// Fonction qui nous permet d'actualiser l'affichage du resultat de la requete
	public void rafraichirData(JPanel p, JTable o){
			// On enleve tous les elements du panel bottom
			p.removeAll();
			if(o == null){
				p.setVisible(false);
			}
			else
			{
				// On lui ajoute au nord les entetes du tableau
				p.add(o.getTableHeader(), BorderLayout.NORTH);
				// On lui ajoute au centre les donnees du resultat de la requete
				p.add(o, BorderLayout.CENTER);
					
			}
	}
}