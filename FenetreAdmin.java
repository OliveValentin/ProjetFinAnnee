// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

//Import pour les ecouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requete faites a  la base de donnees et pour la gestion des Exceptions
import java.sql.ResultSet;
import java.sql.SQLException;





//Import pour les elements du JFrame
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;






public class FenetreAdmin extends JFrame{

	private static final long serialVersionUID = -122199509843219096L;
	// Champs pour la connexion a  la base de donnees avec le driver jdbc.
	private InteractionBDD bdd = new InteractionBDD();
	
	// Champs necessaire pour garder l'identifiant de l'administrateur, le nom de la cave courante et le vin courant (pour les modifications de ces deux derniers)
	private String idAdmin;
	private String caveAVinCourante;
	private String vinCourant;
	
	// La fenetre principale contient 5 onglets principals
	private		JTabbedPane tabbedPane = new JTabbedPane();;
	private		JPanel		panelRecherche;
	private		JPanel		panelAjoutCave;
	private		JPanel		panelModificationCave;
	private		JPanel		panelSuppressionCave;
	private		JPanel		panelModificationMDP;
	
	// Champs necessaire pour la recherche de vins (Onglet Recherche)
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Annee");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	// Champ pour le resultat de la recherche (Onglet Recherche)
	JTable tableau;
	
	// Champs necessaire pour l'ajout d'une cave (Onglet AjoutCave)
	private JTextField nomCaveAAjouter = new JTextField();
	private JTextArea commentaireCave = new JTextArea();
	private JButton ajouterCave = new JButton("Ajouter Cave");
	
	// Champs necessaire pour la modification d'une cave (Onglet ModificationCave)
	private JComboBox<String> caveAModifier = new JComboBox<String>();
	private JButton modifierCave = new JButton("Modifier Cave");
	
	// Dans le panel de l'onglet de modification de cave, nous avons 4 onglets
	private		JTabbedPane tabbedPaneModif;
	private		JPanel		panelEtageres;
	private		JPanel		panelBouteilles;
	private		JPanel		panelVins;
	private		JPanel		panelRangement;
	
	// Dans le panel de l'onglet de modification d'etageres, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifEtageres;
	private		JPanel		panelAjoutEtageres;
	private		JPanel		panelModifEtageres;
	private		JPanel		panelSupprimerEtageres;
	
	// Champs necessaire pour la modification d'une etagere (Onglet ModifEtagere)
	private JComboBox<String> etagereAModifier = new JComboBox<String>();
	
	// Champs necessaire pour la suppression d'une etagere (Onglet SupprimerEtagere)
	private JComboBox<String> etagereASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de bouteilles, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifBouteilles;
	private		JPanel		panelAjoutBouteilles;
	private		JPanel		panelModifBouteilles;
	private		JPanel		panelSupprimerBouteilles;
		
	// Champs necessaire pour la modification d'une bouteille (Onglet ModifBouteilles)
	private JComboBox<String> bouteilleAModifier = new JComboBox<String>();
	
	// Champs necessaire pour la suppression d'une bouteille (Onglet SupprimerBouteilles)
	private JComboBox<String> bouteilleASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de vins, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifVins;
	private		JPanel		panelAjoutVins;
	private		JPanel		panelModifVins;
	private		JPanel		panelSupprimerVins;
	
					
	// Champs necessaire pour l'ajout d'un vin (Onglet AjoutVin)
	private		JTextField regionVins;
	private		JTextField domaineVins;
	private		JTextField châteauVins;
	private		JComboBox<String> couleurVins;
	private		JTextField cepageVins;
	
	// Champs necessaire pour la modification d'un vin (Onglet ModifVins)
	private JComboBox<String> vinAModifier = new JComboBox<String>();
	
	// Champs necessaire pour la suppression d'un vin (Onglet SupprimerVins)
	private JComboBox<String> vinASupprimer = new JComboBox<String>();
	
	// Dans le panel de l'onglet de rangement de vins, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneRangementVins;
	private		JPanel		panelRangementAjout;
	private		JPanel		panelRangementModif;
	private		JPanel		panelRangementSupprimer;
		
	// Champs necessaire pour le rangement des vins/bouteilles sur les etageres (Onglet Rangement Ajout)
	private JComboBox<String> listeetagere = new JComboBox<String>();
	private JComboBox<String> listebouteille = new JComboBox<String>();
	private JComboBox<String> listevin = new JComboBox<String>();
	private		JTextField dateVins;
	
	// Champs necessaire pour le rangement des vins/bouteilles sur les etageres (Onglet Rangement Supprimer)	
	private JComboBox<String> rangementAModifier = new JComboBox<String>();
		
	// Champs necessaire pour le rangement des vins/bouteilles sur les etageres (Onglet Rangement Supprimer)	
	private JComboBox<String> rangementASupprimer = new JComboBox<String>();
		
	
	// Champs necessaire pour la suppression d'une cave (Onglet SuppressionCave)
	private JComboBox<String> caveASupprimer = new JComboBox<String>();
	private JButton supprimerCave = new JButton("Supprimer Cave");
	
	// Champs necessaire pour la modification du mot de passe d'une cave (Onglet ModificationMDP)
	private JTextField oldMDP;
	private JTextField newMDP;
	private JTextField confirmationMDP;
	
	
	// Constructeur avec comme parametre l'identifiant du marchand de vin de la fenetre de Admin
   	public FenetreAdmin(String id) {
   		// On appel le constructeur de JFrame avec un parametre qui est le nom de la fenetre
   		super("La cave a vin - Gestion administrateur");
   		// On enregistre dans la variable globale l'identifiant du marchand de vin
		this.idAdmin = id;
		// Ensuite on initialise cette fenetre
		init();
	}
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// On met la dimension du panel principal a la taille de l'ecran
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		

		// On cree le panel topPanel qui contiendra tous les elements de la FenetreAdmin
		JPanel topPanel = new JPanel();
		// On lui dit que l'affichage des champs se par region
		topPanel.setLayout(new BorderLayout());
					
		// On ajoute le topPanel a la fenetre
		getContentPane().add(topPanel);
		
		// On cree les differents onglets principaux
		createRecherche();
		createAjout();
		createModification();
		createSuppression();
		createMDP();
		
		// On les ajoute dans la barre d'onglet tabbedPane qui est la barre d'onglet principale.
		tabbedPane.addTab( "Rechercher Vins", panelRecherche );
		tabbedPane.addTab( "Ajouter une cave", panelAjoutCave );
		tabbedPane.addTab( "Modifier une cave", panelModificationCave );
		tabbedPane.addTab( "Supprimer une cave", panelSuppressionCave );
		tabbedPane.addTab( "Modifier votre mot de passe", panelModificationMDP );
		
		// On ajoute tabbedPane au panel topPanel au centre
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}
	
	// Creation du panel de Recherche
	public void createRecherche()
	{
		// On cree le panel panelRecherche qui contiendra tous les elements de l'onglet Recherche de la FenetreAdmin
		panelRecherche = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelRecherche.setLayout(new BorderLayout());
	
		// On instancie les champs de notre fenetre (JTextField pour lla recherche par annee et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		// On cree le panel toptop qui contiendra le bouton se connecter
		
		final JPanel toptop = new JPanel();
		// On lui dit que l'affichage des champs se par region
		toptop.setLayout(new BorderLayout());
				
		// On cree un bouton Se connecter que l'on ajoute a l'est du panel toptop
		JButton deconnexion = new JButton("Se Déconnecter");
		toptop.add(deconnexion, BorderLayout.EAST);
		
		// On lui attribue un ecouteur
		deconnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On demande au marchand s'il souhaite vraiment se déconnecter
				int option = JOptionPane.showConfirmDialog(panelRecherche, new JLabel("Voulez-vous vous déconnecter ?"), "Deconnexion" ,JOptionPane.YES_NO_OPTION);
				// S'il dit on on quitte
				if(option == JOptionPane.YES_OPTION){
					setVisible(false);
				}
			}
		});

				
		// On cree le panel top qui contiendra le bouton de recherche et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par region
		top.setLayout(new BorderLayout());
		
		add(toptop, BorderLayout.NORTH);
		// On cree le panel toptop qui contiendra les champs de recherche de vins
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
		
		// On lui attribue un ecouteur (c'est ici que l'on gerera si la recherche se fait par nom,couleur, ou annee.)
		
		// On lui attribue un ecouteur
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cree un tableau de String pour recuperer les elements selectionnes
				String [] t = new String[3];
				// On prepare notre requete de recherche
				String requete = "SELECT * FROM vins, bouteille_has_vins WHERE vins.identifiantVin=bouteille_has_vins.Vins_identifiantVin";
				
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
					requete += " AND cepageVin='" + t[1] + "'";
					if(checkannee.isSelected()){
						requete += " AND bouteille_has_vins.dateVin=" +  t[0];
						if(checkcouleur.isSelected()){
							requete += " AND couleur='" + t[2] + "'";
						}
					}
					else
					{
						if(checkcouleur.isSelected()){
							requete += " AND couleur='" + t[2] + "'";
						}
					}
					
				}
				else
				{
					if(checkannee.isSelected()){
						requete += " AND bouteille_has_vins.dateVin=" +  t[0];
						if(checkcouleur.isSelected()){
							requete += " AND couleur='" + t[2] + "'";
						}
					}
					else
					{
						if(checkcouleur.isSelected()){
							requete += " AND couleur='" + t[2] + "'";
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
					rafraichirDataRecherche(bottom, tableau);
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
		panelRecherche.add(top, BorderLayout.NORTH);
		
		// On ajoute au panelRecherche le panel bottom au centre
		panelRecherche.add(bottom, BorderLayout.CENTER);
	}

	// Creation du panel d'Ajout d'une cave
	public void createAjout()
	{
		// On cree le panel panelAjoutCave qui contiendra tous les elements de l'onglet AjoutCave de la FenetreAdmin
		panelAjoutCave = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelAjoutCave.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour ajouter une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(2,2));
		
		// On met la dimension du JTextField associe au nom de la cave a  200px de large et 30px de hauteur
		nomCaveAAjouter.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Nom de la cave :"));
		inner.add(nomCaveAAjouter);
				
		// On met la dimension du JTextField associe au commentaire de la cave a  200px de large et 30px de hauteur
		commentaireCave.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Commentaire a faire :"));
		inner.add(commentaireCave);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un ecouteur au bouton aujouterCave
		ajouterCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'ajout
				String requete = "INSERT INTO caveavin(nomCave, commentaire, Utilisateur_identifiantUtilisateur) VALUES (";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[2];
				t[0] = nomCaveAAjouter.getText();
				t[1] = commentaireCave.getText();
				
				// Si au moins un argument n'est pas renseigne par l'utilisateur, nous lui affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les trois champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += "'" + t[0]+ "','" + t[1] + "'," + idAdmin + ")";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons les champs d'ajout de la cave a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " n'a pas ete ajoutee dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// Sinon on affiche un message de succes et nous remettons les champs d'ajout de la cave a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a ete ajoutee dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// On fait appel a MAJpanelModificationCave(JComboBox<String> caveAModifier) pour rafraichir les donnees de la liste de caveAModifier.
					MAJpanelCave(caveAModifier);
					// On fait appel a MAJpanelSuppressionCave(JComboBox<String> caveASupprimer) pour rafraichir les donnees de la liste de caveASupprimer.
					MAJpanelCave(caveASupprimer);
				}
			}
		});
		
		// On au panel nord le bouton ajouterCave au sud
		nord.add(ajouterCave, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutCave le panel nord au centre
		panelAjoutCave.add(nord, BorderLayout.CENTER);
	}

	// Creation du panel de modification d'une cave
	public void createModification()
	{
		// On cree le panel panelModificationCave qui contiendra tous les elements de l'onglet ModifCave de la FenetreAdmin
		panelModificationCave = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelModificationCave.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton modifierCave
		final JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());

		// On cree le panel inner qui contiendra les champs pour modifier une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste de cave a modifier
		creerListeCave(caveAModifier);

		// On ajoute au panel inner la liste de cave a modifier
		inner.add(new JLabel("Nom de la cave a modifier:"));
		inner.add(caveAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un ecouteur au bouton modifierCave
		modifierCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cree un tableau de String pour recuperer les champs pour la modification de la cave
				String [] t = new String[1];
				t[0] = caveAModifier.getSelectedItem().toString();;
				
				// Si aucune cave n'est selectionnee, on affiche un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une cave a modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On met dans une variable globale le nom de la cave selectionnee
					caveAVinCourante = t[0];
					// On cache le panel nord.
					nord.setVisible(false);
					// On affiche la table d'onglet tabbedPaneModif qui permet la gestion de la cave a vin.
					tabbedPaneModif.setVisible(true);
					// On ajoute au panelModificationCave la barre d'onglet tabbedPaneModif au centre
					panelModificationCave.add(tabbedPaneModif, BorderLayout.CENTER);
					
				}
				// On met a jour les listes de vins, de bouteilles et d'etageres contenu dans les onglets de tabbedPaneModif
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelBouteilles(bouteilleAModifier);
				MAJpanelBouteilles(bouteilleASupprimer);
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On au panel nord le bouton modifierCave au sud
		nord.add(modifierCave, BorderLayout.SOUTH);
		
		// On ajoute au panelModificationCave le panel nord au centre
		panelModificationCave.add(nord, BorderLayout.CENTER);
		
		// On cree les onglets de tabbedPaneModif
		createPanelEtageres();
		createPanelBouteilles();
		createPanelVins();
		createPanelRangement();
		
		// On les ajoute dans la barre d'onglet tabbedPaneModif qui est la barre d'onglet pour la modification d'une cave.
		tabbedPaneModif = new JTabbedPane();
		tabbedPaneModif.addTab("Gestion etageres", panelEtageres);
		tabbedPaneModif.addTab("Gestion bouteilles", panelBouteilles);
		tabbedPaneModif.addTab("Gestion vins", panelVins);
		tabbedPaneModif.addTab("Rangement des vins", panelRangement);
		// On cache la tabbedPaneModif
		tabbedPaneModif.setVisible(false);
		
	}
	
	// Creation du panel de suppression d'une cave
	public void createSuppression()
	{
		// On cree le panel panelSuppressionCave qui contiendra tous les elements de l'onglet SuppressionCave de la FenetreAdmin
		panelSuppressionCave = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelSuppressionCave.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste de cave a supprimer
		creerListeCave(caveASupprimer);
		
		// On ajoute au panel inner la liste de cave a supprimer
		inner.add(new JLabel("Nom de la cave a supprimer:"));
		inner.add(caveASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un ecouteur au bouton supprimerCave
		supprimerCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "DELETE FROM caveavin WHERE nomCave='";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = caveASupprimer.getSelectedItem().toString();;
				
				// Si aucune cave n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une cave a supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0] + "'";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ de suppression de la cave a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + "n'a pas ete supprimee de votre base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succes et nous remettons le champ de suppression de la cave a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a ete supprimee de votre base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// On met a jour les listes de caveAModifier et de caveASupprimer
					MAJpanelCave(caveAModifier);
					MAJpanelCave(caveASupprimer);
				}
			}
		});
		
		// On ajoute au panel nord le bouton supprimerCave au sud
		nord.add(supprimerCave, BorderLayout.SOUTH);
		
		// On ajoute au panelSuppressionCave le panel nord au centre
		panelSuppressionCave.add(nord, BorderLayout.CENTER);
	}
	
	// Creation du panel de modification du motdepasse
	public void createMDP(){
		// On cree le panel panelModificationMDP qui contiendra tous les elements de l'onglet ModificationMDP de la FenetreAdmin
		panelModificationMDP = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelModificationMDP.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour modifier son mot de passe dans la base de donnees
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(3,2));
		
		// On ajoute au panel inner le champ ancien mot de passe
		inner.add(new JLabel("Veuillez renseigner votre mot de passe : "));
		oldMDP = new JPasswordField();
		inner.add(oldMDP);

		// On ajoute au panel inner le champ nouveau mot de passe
		inner.add(new JLabel("Veuillez renseigner votre nouveau mot de passe : "));
		newMDP = new JPasswordField();
		inner.add(newMDP);
		
		// On ajoute au panel inner le champ confirmation nouveau mot de passe
		inner.add(new JLabel("Veuillez confirmer votre nouveau mot de passe : "));
		confirmationMDP = new JPasswordField();
		inner.add(confirmationMDP);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree un bouton Changer le mot de passe
		JButton changerMDP = new JButton("Changer le mot de passe.\n");
		
		// On lui attribut un ecouteur
		changerMDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete pour recuperer l'ancien mot de passe de l'utilisateur
				String select = "SELECT * FROM cave.utilisateur WHERE identifiantUtilisateur=";
				// On prepare la requete mettre a jour le mot de passe de l'utilisateur avec le nouveau mot de passe
				String update = "UPDATE cave.utilisateur SET motDePasse = MD5('";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[3];
				t[0] = oldMDP.getText();
				t[1] = newMDP.getText();
				t[2] = confirmationMDP.getText();
				
				// Si au moins un argument n'est pas renseigne par l'utilisateur, nous lui affichons un message d'erreur et on met tous les champs a vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					oldMDP.setText("");
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Si le nouveau mot de passe et la confirmation de celui-ci ne sont pas egals.
				// On affiche un message d'erreur et nous mettons tous les champs a vide.
				if(!t[1].equals(t[2])){
					JOptionPane.showMessageDialog(null, "Le nouveau mot de passe et la confirmation ne sont pas identique.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Sinon
				else
				{
					// On fini la preparation de la requete de recuperation de l'ancien mot de passe en ajoutant le parametre necessaire a la requete.
					select += idAdmin + " AND motDePasse=MD5('" + t[0] + "')";
					// On recupere le resultat de la requete a l'aide de MyConnexionSelect(String requete)
					ResultSet changeMDP = bdd.MyConnexionSelect(select);
					try {
						// Si le resultat de la requete select contient une ou des donnees
						if(changeMDP.next()){
							// On fini la preparation de la requete de mise a jour du en ajoutant les parametres necessaires a la requete.
							update += t[2] + "') WHERE utilisateur.identifiantUtilisateur = " + idAdmin;
							// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = bdd.MyConnexionInsertDeleteUpdate(update);
							// Si la requete a reussi, nous affichons un message pour dire que le mot de passe a ete change.
							// Et nous remettons tous les champs a vide.
							if(statut){
								JOptionPane.showMessageDialog(null, "Votre mot de passe a ete change.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
							// Sinon, nous affichons un message d'erreur pour dire que le mot de passe n'a pas ete change.
							// Et nous remettons tous les champs a vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Votre mot de passe n'a pas ete change.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
								
						}
						// Si le resultat de la requete select ne contient pas de donnees, on affiche un message d'erreur et nous mettons tous les champs a vide.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est errone.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							oldMDP.setText("");
							newMDP.setText("");
							confirmationMDP.setText("");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// On ajoute au panel nord le bouton changerMDP au sud
		nord.add(changerMDP, BorderLayout.SOUTH);
		
		// On ajoute au panelModificationMDP le panel nord au centre
		panelModificationMDP.add(nord, BorderLayout.CENTER);
		
		
	}
	
	// Creation du panel de Gestion des etageres
	public void createPanelEtageres(){
		// On cree le panel panelEtageres qui contiendra tous les elements de l'onglet GestionEtagere de la FenetreAdmin
		panelEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelEtageres.setLayout(new BorderLayout());

		// On cree les onglets de tabbedPaneModif
		createPanelAjoutEtageres();
		createPanelModifEtageres();
		createPanelSupprimerEtageres();
				
		// On les ajoute dans la barre d'onglet tabbedPaneModifEtageres qui est la barre d'onglet pour la modification d'une etagere.
		tabbedPaneModifEtageres = new JTabbedPane();
		tabbedPaneModifEtageres.addTab("Ajouter une etagere", panelAjoutEtageres);
		tabbedPaneModifEtageres.addTab("Modifier une etagere", panelModifEtageres);
		tabbedPaneModifEtageres.addTab("Supprimer une etagere", panelSupprimerEtageres);
		
		// On ajoute au panelEtageres la barre d'onglet tabbedPaneModifEtageres au centre 
		panelEtageres.add(tabbedPaneModifEtageres, BorderLayout.CENTER);		
		
	}
	
	// Creation du panel d'Ajout d'etagere
	public void createPanelAjoutEtageres(){
		
		// On cree le panel panelAjoutEtageres qui contiendra tous les elements de l'onglet AjoutEtageres de la FenetreAdmin
		panelAjoutEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelAjoutEtageres.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new GridLayout(2,0));
		
		// On cree le panel inner qui contiendra les champs pour ajouter une etagere
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
		
		
		// Creation et ajout du champs position dans le panel innerNord
		final JTextField position = new JTextField();
		innerNord.add(new JLabel("L'étagère à ajouter (position dans la cave): "));
		innerNord.add(position);
		
		// On ajoute au panel nord le panel innerNord
		nord.add(innerNord);
		
		// On cree un bouton addEtagere
		JButton addEtagere = new JButton("Ajouter une etagere");
		
		// On lui attribut un ecouteur
		addEtagere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'ajout
				String requete = "INSERT INTO etagère(positionEtagère, CaveAVin_nomCave, CaveAVin_Utilisateur_identifiantUtilisateur) VALUES ('";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = position.getText();
				
				// Si l'utilisateur n'a pas donne de position, nous lui affichons un message d'erreur et nous mettons le champ a vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					position.setText("");
				}
				else{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += t[0] + "','"+ caveAVinCourante + "'," + idAdmin + ")";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une etagere a la position " + t[0] + " n'a pas ete ajoutee dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						position.setText("");
					}
					// Sinon on affiche un message de succes et nous remettons le champ a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une etagere a la position " + t[0] + " a ete ajoutee dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						position.setText("");
					}
				}
				// On met a jour les listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton addEtagere
		nord.add(addEtagere);
		
		// On ajoute au panelAjoutEtageres le panel nord au nord
		panelAjoutEtageres.add(nord,BorderLayout.NORTH);
	}

	// Creation du panel de modification d'etagere
	public void createPanelModifEtageres(){

		// On cree le panel panelModifEtageres qui contiendra tous les elements de l'onglet ModifEtageres de la FenetreAdmin
		panelModifEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelModifEtageres.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour modifier une etagere
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On cree la liste des etageres.
		creerListeEtagere(etagereAModifier);
		
		// On ajoute le champs etagereAModifier au panel inner
		inner.add(new JLabel("L'étagère à modifier : \n"));
		inner.add(etagereAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree le panel bottomEtageres qui contiendra les champs a remplir avant de modifier une etagere
		final JPanel bottomEtageres = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomEtageres.setLayout(new GridLayout(5,2));
		
		// On cree les JTextField pour ajouter les donnees de l'etagere a modifier dedans.
		final JTextField idEtagere = new JTextField();
		final JTextField positionEtagere = new JTextField();
		final JTextField idCave = new JTextField();
		final JTextField idUser = new JTextField();
		
		// On ajoute au panel bottomEtageres le champ identifiant Etagere
		bottomEtageres.add(new JLabel("Identifiant etagere : "));
		bottomEtageres.add(idEtagere);
		// On rend ce champ non modifiable
		idEtagere.setEditable(false);
		
		// On ajoute au panel bottomEtageres le champ position Etagere
		bottomEtageres.add(new JLabel("Position de l'etagere (sans ' ou \" ) : "));
		bottomEtageres.add(positionEtagere);
		
		// On ajoute au panel bottomEtageres le champ identifiant cave
		bottomEtageres.add(new JLabel("Identifiant cave : "));
		bottomEtageres.add(idCave);
		// On rend ce champ non modifiable
		idCave.setEditable(false);
		
		// On ajoute au panel bottomEtageres le champ identifiant utilisateur
		bottomEtageres.add(new JLabel("Identifiant utilisateur : "));
		bottomEtageres.add(idUser);
		// On rend ce champ non modifiable
		idUser.setEditable(false);
		
		// On cache le panel bottomEtageres
		bottomEtageres.setVisible(false);
		
		// On cree un nouveau bouton modifierEtageres
		JButton modifierEtageres = new JButton("Modifier l'etagere selectionnee");
		
		// On lui attribue un ecouteur
		modifierEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de selection
				String requete = "SELECT * FROM etagère WHERE positionEtagère='";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[1];
				t[0] = etagereAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une etagere a modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0] + "'";
					// On recupere le resultat de la requete a l'aide de MyConnexionSelect(String requete)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions du a  la requete.
					try {
						// Si la requete a renvoye des donnees, nous mettons ces donnees dans les champs de modification de l'etagere 
						if(statut.first()){
							idEtagere.setText(statut.getString("identifiantEtagère"));
							positionEtagere.setText(statut.getString("positionEtagère"));
							idCave.setText(statut.getString("CaveAVin_nomCave"));
							idUser.setText(statut.getString("CaveAVin_Utilisateur_identifiantUtilisateur"));
						}
						// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier a vide.
						else
						{
							JOptionPane.showMessageDialog(null, "L'etagere a la position " + t[0] + " n'a pas ete selectionner dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							etagereAModifier.setSelectedItem("");
						}
					} catch (HeadlessException | SQLException e) {
						e.printStackTrace();
					}
					// Nous affichons le panel bottomEtageres
					bottomEtageres.setVisible(true);;
				}
			}
		});
		
		// On ajoute au panel nord le bouton modifierEtageres au sud.
		nord.add(modifierEtageres, BorderLayout.SOUTH);
		
		// Nous creeons un bouton updateDataEtagere.
		JButton updateDataEtagere = new JButton("Valider les donnees\n");
		
		// On lui attribue un ecouteur
		updateDataEtagere.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// On prepare la requete d'update
						String requete = "UPDATE etagère SET ";
						// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
						String [] t = new String[4];
						t[0] = idEtagere.getText();
						t[1] = positionEtagere.getText();
						t[2] = idCave.getText();
						t[3] = idUser.getText();
						
						// Si le champ de positionEtagere est vide, nous affichons un message d'erreur.
						if(t[1].isEmpty()){
							JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						// Sinon
						else
						{
							// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
							requete += "positionEtagère='"+ t[1] + "'";
							requete += " WHERE identifiantEtagère=" + t[0];
							// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
							// Si la requete a reussi, nous affichons un message pour dire que l'etagere a ete modifie.
							// Et nous recachons le panel bottomEtageres.
							if(statut){
								JOptionPane.showMessageDialog(null, "L'etagere d'identifiant " + t[0] + " a ete modifie dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								bottomEtageres.setVisible(false);
							}
							// Sinon, nous affichons un message d'erreur
							else
							{
								JOptionPane.showMessageDialog(null, "L'etagere d'identifiant " + t[0] + " n'a pas ete modifie dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						// On effectue une mise a jour des listes etagereAModifier et etagereASupprimer
						MAJpanelEtageres(etagereAModifier);
						MAJpanelEtageres(etagereASupprimer);
						MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
					}
				});
		// On ajoute au panel bottomEtageres le bouton updateDataEtagere
		bottomEtageres.add(updateDataEtagere);
		
		// On ajoute au panelModifEtageres le panel nord a la position nord et le panel bottomEtageres a la position sud.
		panelModifEtageres.add(nord, BorderLayout.NORTH);
		panelModifEtageres.add(bottomEtageres, BorderLayout.SOUTH);
	}

	// Creation du panel de suppression d'etagere
	public void createPanelSupprimerEtageres(){
		// On cree le panel panelSupprimerEtageres qui contiendra tous les elements de l'onglet SupprimerEtageres de la FenetreAdmin
		panelSupprimerEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelSupprimerEtageres.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton supprimerEtageres
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On cree le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste des etageres a supprimer
		creerListeEtagere(etagereASupprimer);
		
		// On ajoute au panel inner la liste d'etageres a supprimer
		inner.add(new JLabel("Etagère(s) a supprimer:"));
		inner.add(etagereASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);

		// On cree un bouton supprimerEtageres
		JButton supprimerEtageres = new JButton("Supprimer l'étagère sélectionnée.");
		
		// On lui attribue un ecouteur
		supprimerEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "DELETE FROM etagère WHERE positionEtagère='";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = etagereASupprimer.getSelectedItem().toString();;
				
				// Si aucune etagere n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une etagere a supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0] + "'";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean status = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ de suppression de l'etagere a vide.
					if(!(status)){
						JOptionPane.showMessageDialog(null, "L'etagere de la position " + t[0] + " n'a pas ete supprime dans la base de donnees", "Erreur", JOptionPane.ERROR_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succes et nous remettons le champ de suppression de l'etagere a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "L'etagere de la position " + t[0] + " a pas ete supprime dans la base de donnees", "Information", JOptionPane.INFORMATION_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
				}
				// Mise a jour des listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerEtageres
		nord.add(supprimerEtageres);
		
		// On ajoute au panelSupprimerEtageres le panel nord au centre
		panelSupprimerEtageres.add(nord, BorderLayout.CENTER);
	}
	
	// Creation du panel de Gestion des bouteilles
	public void createPanelBouteilles(){
		// On cree le panel panelBouteilles qui contiendra tous les elements de l'onglet GestionBouteille de la FenetreAdmin
		panelBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelBouteilles.setLayout(new BorderLayout());

		// On cree les onglets de tabbedPaneModifBouteilles
		createPanelAjoutBouteilles();
		createPanelModifBouteilles();
		createPanelSupprimerBouteilles();
		
		// On les ajoute dans la barre d'onglet tabbedPaneModifBouteilles qui est la barre d'onglet pour la modification d'une bouteille.
		tabbedPaneModifBouteilles = new JTabbedPane();
		tabbedPaneModifBouteilles.addTab("Ajouter une bouteille", panelAjoutBouteilles);
		tabbedPaneModifBouteilles.addTab("Modifier une bouteille", panelModifBouteilles);
		tabbedPaneModifBouteilles.addTab("Supprimer une bouteille", panelSupprimerBouteilles);
		
		// On ajoute au panelBouteilles la barre d'onglet tabbedPaneModifBouteilles au centre 
		panelBouteilles.add(tabbedPaneModifBouteilles, BorderLayout.CENTER);	
	}
	
	// Creation du panel d'Ajout de bouteilles
	public void createPanelAjoutBouteilles(){
		// On cree le panel panelAjoutBouteilles qui contiendra tous les elements de l'onglet AjoutBouteilles de la FenetreAdmin
		panelAjoutBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelAjoutBouteilles.setLayout(new BorderLayout());
	
		// On cree le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new GridLayout(2,1));
		
		// On cree le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
				
		// On ajoute le champ volume de la bouteille dans le panel innerNord
		innerNord.add(new JLabel("Volume de la bouteille (en L): "));
		final JTextField capacity = new JTextField();
		innerNord.add(capacity);
		nord.add(innerNord);
		
		// On cree un bouton addBouteille
		JButton addBouteille = new JButton("Ajouter une bouteille");
		
		// On lui attribut un ecouteur
		addBouteille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'ajout
				String requete = "INSERT INTO bouteille(taille) VALUES (";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = capacity.getText();
				
				
				
				// Si l'utilisateur n'a pas donne de volume pour la bouteille, nous lui affichons un message d'erreur et nous mettons le champ a vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					capacity.setText("");
				}
				else{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += t[0] + ")";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litres n'a pas ete ajoutee dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						capacity.setText("");
					}
					// Sinon on affiche un message de succes et nous remettons le champ a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a ete ajoutee dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						capacity.setText("");
					}
					// On met a jour les listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
					MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
				}
			}
		});
		
		// On ajoute au panel nord le bouton addBouteille 
		nord.add(addBouteille);
		
		// On ajoute au panelAjoutBouteilles le panel nord au nord 
		panelAjoutBouteilles.add(nord,BorderLayout.NORTH);
		
	}
	
	// Creation du panel de modification de bouteille
	public void createPanelModifBouteilles(){
		// On cree le panel panelModifBouteilles qui contiendra tous les elements de l'onglet ModifBouteilles de la FenetreAdmin
		panelModifBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelModifBouteilles.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton modifierBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour modifier une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On cree la liste des bouteilles.
		creerListeBouteille(bouteilleAModifier);
		
		// On ajoute le champ capacite au panel inner
		inner.add(new JLabel("Choisir la bouteille : \n"));
		inner.add(bouteilleAModifier);
		
		// On ajoute le panel inner au panel nord a la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		
		// On cree le panel bottomEtageres qui contiendra les champs a remplir avant de modifier une etagere
		final JPanel bottomBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomBouteilles.setLayout(new GridLayout(3,2));
		
		// On cree les JTextField pour ajouter les donnees de la bouteille a modifier dedans.
		final JTextField idBouteille = new JTextField();
		final JTextField capacityBottle = new JTextField();
		
		// On ajoute au panel bottomBouteilles le champ identifiant Bouteille
		bottomBouteilles.add(new JLabel("Id Bouteille : "));
		bottomBouteilles.add(idBouteille);
		// On rend ce champ nom editable
		idBouteille.setEditable(false);
		
		// On ajoute au panel bottomBouteilles le champ capacite
		bottomBouteilles.add(new JLabel("Capacite en Litre(s) : "));
		bottomBouteilles.add(capacityBottle);
		
		// On cache le panel bottomBouteilles
		bottomBouteilles.setVisible(false);

		// On cree un bouton modifierBouteille
		JButton modifierBouteilles = new JButton("Modifier la bouteille selectionnee");
		
		// On lui attribue un ecouteur
		modifierBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de selection
				String requete = "SELECT * FROM bouteille WHERE taille=";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[1];
				t[0] = bouteilleAModifier.getSelectedItem().toString();;
				// FLOTTANT marche seulement avec 1 chiffre apres la virgule.
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une bouteille a modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0];
					// On recupere le resultat de la requete a l'aide de MyConnexionSelect(String requete)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions du a  la requete.					
						try {
							// Si la requete a renvoye des donnees, nous mettons ces donnees dans les champs de modification de la bouteille 
							if(statut.first()){
								idBouteille.setText(statut.getString("identifiantBouteille"));
								capacityBottle.setText(statut.getString("taille"));
							}
							// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier a vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas ete selectionnee dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Nous affichons le panel bottomBouteilles
						bottomBouteilles.setVisible(true);;
				}
			}
		});
		// On ajoute au panel nord le bouton modifierBouteilles au sud.
		nord.add(modifierBouteilles, BorderLayout.SOUTH);
		
		// On cree un bouton updateDataBottle
		JButton updateDataBottle = new JButton("Valider les donnees\n");
		
		// On lui attribue un ecouteur
		updateDataBottle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'update
				String requete = "UPDATE bouteille SET ";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[2];
				t[0] = idBouteille.getText();
				t[1] = capacityBottle.getText();
				
				// Si le champ de positionEtagere est vide, nous affichons un message d'erreur.
				if(t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += "taille=\'"+ t[1] + "\'";
					requete += " WHERE identifiantBouteille=" + t[0];
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a reussi, nous affichons un message pour dire que la bouteille a ete modifie.
					// Et nous recachons le panel bottomBouteilles.
					if(statut){
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " a ete modifie dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						bottomBouteilles.setVisible(false);
					}
					// Sinon, nous affichons un message d'erreur
					else
					{
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " n'a pas ete modifie dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// On effectue une mise a jour des listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
					MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
				}
				
			}
		});
		// On ajoute au panel bottomBouteilles le bouton updateDataBottle
		bottomBouteilles.add(updateDataBottle);
	
		// On ajoute au panelModifBouteilles le panel nord a la position nord et le panel bottomBouteilles a la position sud.
		panelModifBouteilles.add(nord, BorderLayout.NORTH);
		panelModifBouteilles.add(bottomBouteilles, BorderLayout.SOUTH);
		
	}

	// Creation du panel de suppression de bouteille
	public void createPanelSupprimerBouteilles(){
		// On cree le panel panelSupprimerBouteilles qui contiendra tous les elements de l'onglet SupprimerBouteilles de la FenetreAdmin
		panelSupprimerBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelSupprimerBouteilles.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton supprimerBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On cree le panel inner qui contiendra les champs pour supprimer une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste des bouteilles a supprimer
		creerListeBouteille(bouteilleASupprimer);
		
		// On ajoute au panel inner la liste des bouteilles a supprimer
		inner.add(new JLabel("Bouteille(s) a supprimer:"));
		inner.add(bouteilleASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On cree un bouton supprimerBouteilles
		JButton supprimerBouteilles = new JButton("Supprimer la bouteille selectionnee.");
		
		// On lui attribue un ecouteur
		supprimerBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "DELETE FROM bouteille WHERE taille=\'";
				// On prepare la requete pour la selection
				String requeteSelect = "SELECT * FROM bouteille WHERE taille=\'";
				String idBottle = null;
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = bouteilleASupprimer.getSelectedItem().toString();;
				
				// Si aucune bouteille n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une bouteille a supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la preparation de la requete de suppression en ajoutant le parametre necessaire a la requete
					requete += t[0] + "\'";
					// On fini la preparation de la requete de selection en ajoutant le parametre necessaire a la requete
					requeteSelect += t[0] + "\'";
					// On recupere le resultat de la requete a l'aide de MyConnexionSelect(String requete)
					ResultSet statut = bdd.MyConnexionSelect(requeteSelect);
					// Bloc try catch pour la gestion des excetions du a  la requete.					
						try {
							// Si la requete a renvoye des donnees, nous recuperons l'identifiant de la bouteille a supprimer
							if(statut.first()){
								idBottle = statut.getString("identifiantBouteille");
							}
							// Sinon, nous affichons un message d'erreur et nous mettons la bouteille a supprimer a vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas ete supprime dans la base de donnees", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Si nous avons changer la valeur de l'idBottle (nous avons selectionne la bouteille dans la base)
						if(idBottle != null)
						{
							// On fini la preparation de la requete de suppression en ajoutant le parametre necessaire a la requete
							requete += " AND identifiantBouteille=" + idBottle;
							// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean status = bdd.MyConnexionInsertDeleteUpdate(requete);
							// Si la requete a reussi, nous affichons un message pour dire que la bouteille a ete supprime.
							// Et nous remettons le champ de suppression de la bouteille a vide.
							if((status)){
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a pas ete supprime dans la base de donnees", "Information", JOptionPane.INFORMATION_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
							// Sinon on affiche un message d'erreur et nous remettons le champ de suppression de la bouteille a vide.
							else
							{
								JOptionPane.showMessageDialog(null, "La bouteille de " + t[0] + " litre n'a pas ete supprime dans la base de donnees", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						}
						// On met a jour les listes bouteilleAModifier et bouteilleASupprimer
						MAJpanelBouteilles(bouteilleAModifier);
						MAJpanelBouteilles(bouteilleASupprimer);
						MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
				}
			}
		});
		
		// On ajoute au panel nord le bouton supprimerBouteilles au sud
		nord.add(supprimerBouteilles, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerBouteilles le panel nord au centre
		panelSupprimerBouteilles.add(nord, BorderLayout.CENTER);
	}

	// Creation du panel de Gestion des vins
	public void createPanelVins(){
		// On cree le panel panelVins qui contiendra tous les elements de l'onglet GestionVin de la FenetreAdmin
		panelVins = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelVins.setLayout(new BorderLayout());

		// On cree les onglets de tabbedPaneModifBouteilles
		createPanelAjoutVins();
		createPanelModifVins();
		createPanelSupprimerVins();
		
		// On les ajoute dans la barre d'onglet tabbedPaneModifVins qui est la barre d'onglet pour la modification d'un vin.
		tabbedPaneModifVins = new JTabbedPane();
		tabbedPaneModifVins.addTab("Ajouter un vins", panelAjoutVins);
		tabbedPaneModifVins.addTab("Modifier un vins", panelModifVins);
		tabbedPaneModifVins.addTab("Supprimer un vins", panelSupprimerVins);
		
		// On ajoute au panelVins la barre d'onglet tabbedPaneModifBouteilles au centre 
		panelVins.add(tabbedPaneModifVins, BorderLayout.CENTER);	
	}

	// Creation du panel d'Ajout de vins
	public void createPanelAjoutVins(){
		// On cree le panel panelAjoutVins qui contiendra tous les elements de l'onglet AjoutVins de la FenetreAdmin
		panelAjoutVins = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelAjoutVins.setLayout(new BorderLayout());
	
		// On cree le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(5,2));

		// On initialise les champs d'ajout du vin
		regionVins = new JTextField();
		domaineVins = new JTextField();
		châteauVins = new JTextField();
		couleurVins = new JComboBox<String>();
		cepageVins = new JTextField();
		
		// On met la taille du JTextField a 200px de long et 30 px de hauteur
		regionVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Region du vin dans le panel inner
		inner.add(new JLabel("Region du vin :"));
		inner.add(regionVins);
				
		// On met la taille du JTextField a 200px de long et 30 px de hauteur
		domaineVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Domaine du vin dans le panel inner
		inner.add(new JLabel("Domaine du vin :"));
		inner.add(domaineVins);
		
		// On met la taille du JTextField a 200px de long et 30 px de hauteur
		châteauVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Chateau du vin dans le panel inner
		inner.add(new JLabel("Chateau du vin :"));
		inner.add(châteauVins);
		
		// On met la taille du JComboBox a 200px de long et 30 px de hauteur
		couleurVins.setPreferredSize(new Dimension(200,30));
		// On rajoute les couleurs de vins dans la JComboBox<String>
		couleurVins.addItem("");
		couleurVins.addItem("rouge");
		couleurVins.addItem("blanc");
		couleurVins.addItem("rose");
		// On ajoute le champ Couleur du vin dans le panel inner
		inner.add(new JLabel("Couleur du vin :"));
		inner.add(couleurVins);
				
		// On met la taille du JTextField a 200px de long et 30 px de hauteur
		cepageVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Cepage du vin dans le panel inner
		inner.add(new JLabel("Cepage du vin :"));
		inner.add(cepageVins);
				
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree un bouton ajouterVins
		JButton ajouterVins = new JButton("Ajouter le vins");
		
		// On lui attribut un ecouteur
		ajouterVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'ajout
				String requete = "INSERT INTO vins(domaineVin, regionVin, châteauVin, couleur, cepageVin) VALUES (";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[6];
				t[0] = domaineVins.getText();
				t[1] = regionVins.getText();
				t[2] = châteauVins.getText();
				t[3] = couleurVins.getSelectedItem().toString();
				t[4] = cepageVins.getText();
				
				// Si un ou plusieurs arguments n'ont pas ete renseignes, nous lui affichons un message d'erreur et nous mettons les champs a vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					domaineVins.setText("");
					regionVins.setText("");
					châteauVins.setText("");
					couleurVins.setSelectedItem("");;
					cepageVins.setText("");
				}
				else
				{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += "'" + t[0] + "','"+ t[1] + "','" + t[2] + "','" + t[3] + "','" + t[4] + "')";
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " n'a pas ete ajoutee dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						domaineVins.setText("");
						regionVins.setText("");
						châteauVins.setText("");
						couleurVins.setSelectedItem("");;
						cepageVins.setText("");
					}
					// Sinon on affiche un message de succes et nous remettons le champ a vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " a ete ajoutee dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						domaineVins.setText("");
						regionVins.setText("");
						châteauVins.setText("");
						couleurVins.setSelectedItem("");;
						cepageVins.setText("");
					}
				}
				// On met a jour les listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton ajouterVins au sud
		nord.add(ajouterVins, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutVins le panel nord au centre
		panelAjoutVins.add(nord,BorderLayout.CENTER);
		
	}
	
	
	// Creation du panel de modification de vins
	public void createPanelModifVins(){
		// On cree le panel panelModifVins qui contiendra tous les elements de l'onglet ModifVins de la FenetreAdmin
		panelModifVins = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelModifVins.setLayout(new BorderLayout());
	
		// On cree le panel nord qui le panel inner et le bouton modifierVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,0));


		// On creer la liste de vin a modifier
		creerListeVin(vinAModifier);
		
		// On ajoute le champs vinAModifier dans le panel inner
		inner.add(new JLabel("Choisir le vin a modifier.\n"));
		inner.add(vinAModifier);
		
		// On ajoute le panel inner dans le panel nord a la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree le panel bottomEtageres qui contiendra les champs a remplir avant de modifier une etagere
		final JPanel bottomVins = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomVins.setLayout(new GridLayout(6,2));
				
		// On cree les JTextField pour ajouter les donnees de la bouteille a modifier dedans.
		final JTextField regionVinsM = new JTextField();
		final JTextField domaineVinsM = new JTextField();
		final JTextField châteauVinsM = new JTextField();
		final JTextField cepageVinsM = new JTextField();
		
		// On cree la JComboBox<String> pour la couleur du vin
		final JComboBox<String> couleurVinsM = new JComboBox<String>();
		couleurVinsM.addItem("blanc");
		couleurVinsM.addItem("rose");
		couleurVinsM.addItem("rouge");
		
		// On ajoute le champs Region au panel bottomVins
		bottomVins.add(new JLabel("Region : "));
		bottomVins.add(regionVinsM);
		
		// On ajoute le champs Domaine au panel bottomVins
		bottomVins.add(new JLabel("Domaine : "));
		bottomVins.add(domaineVinsM);
		
		// On ajoute le champs Chateau au panel bottomVins
		bottomVins.add(new JLabel("Chateau : "));
		bottomVins.add(châteauVinsM);
		
		// On ajoute le champs Couleur au panel bottomVins
		bottomVins.add(new JLabel("Couleur : "));
		bottomVins.add(couleurVinsM);
		
		// On ajoute le champs Cepage au panel bottomVins
		bottomVins.add(new JLabel("Cepage : "));
		bottomVins.add(cepageVinsM);
		
			
		// On cache le panel bottomVins
		bottomVins.setVisible(false);
	
		// On cree le bouton modifierVins
		JButton modifierVins = new JButton("Modifier le vin selectionne.");
		
		// On lui attribue un ecouteur
		modifierVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de selection
				String requete = "SELECT * FROM vins WHERE identifiantVin=";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[1];
				t[0] = vinAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un vin a modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0];
					// On recupere le resultat de la requete a l'aide de MyConnexionSelect(String requete)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions du a  la requete.					
						try {
							// Si la requete a renvoye des donnees, nous mettons ces donnees dans les champs de modification du vin 
							if(statut.first()){
								vinCourant = t[0];
								regionVinsM.setText(statut.getString("regionVin"));
								domaineVinsM.setText(statut.getString("domaineVin"));
								châteauVinsM.setText(statut.getString("châteauVin"));
								couleurVinsM.setSelectedItem(statut.getString("couleur"));
								cepageVinsM.setText(statut.getString("cepageVin"));	
							}
							// Sinon, nous affichons un message d'erreur et nous mettons le vinAModifier a vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " n'a pas ete selectionner dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								vinAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Nous affichons le bottomVins
						bottomVins.setVisible(true);;
				}
				// On met a jour les listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				
			}
		});
		
		// On ajoute au panel nord le bouton modifierVins au sud
		nord.add(modifierVins, BorderLayout.SOUTH);
		
		// On cree un bouton updateDataVins
		JButton updateDataVins = new JButton("Valider les donnees.\n");
		
		// On lui attribue un ecouteur
		updateDataVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'update
				String requete = "UPDATE cave.vins SET ";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[6];
				t[0] = regionVinsM.getText();
				t[1] = domaineVinsM.getText();
				t[2] = châteauVinsM.getText();
				t[3] = couleurVinsM.getSelectedItem().toString();
				t[4] = cepageVinsM.getText();
				
				// Si le champ de positionEtagere est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += "regionVin='"+ t[0] + "', domaineVin='" + t[1] + "', châteauVin='" + t[2] + "', couleur='" + t[3] + "', cepageVin='" + t[4] + "'";
					requete += " WHERE identifiantVin=" + vinCourant;
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a reussi, nous affichons un message pour dire que la vin a ete modifie.
					// Et nous recachons le panel bottomVins.
					if((statut)){
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " a ete modifie dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						bottomVins.setVisible(false);
					}
					// Sinon on affiche un message d'erreur.
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " n'a pas ete modifie dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
				// On met a jour les listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel bottomVins le bouton updateDataVins
		bottomVins.add(updateDataVins);
		
		// On ajoute au panelModifVins le panel nord a la position nord et le panel bottomVins a la position sud.
		panelModifVins.add(nord, BorderLayout.NORTH);
		panelModifVins.add(bottomVins, BorderLayout.SOUTH);
		
	}

	// Creation du panel de suppression de vin
	public void createPanelSupprimerVins(){
		// On cree le panel panelSupprimerVins qui contiendra tous les elements de l'onglet SupprimerVins de la FenetreAdmin
		panelSupprimerVins = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelSupprimerVins.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton supprimerVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour supprimer un vin
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste des vins a supprimer
		creerListeVin(vinASupprimer);
		
		// On ajoute au panel inner la liste des vins a supprimer
		inner.add(new JLabel("Vins a supprimer:"));
		inner.add(vinASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree un bouton supprimerVins
		JButton supprimerVins = new JButton("Supprimer le vin selectionne.");
		
		// On lui attribue un ecouteur
		supprimerVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "DELETE FROM vins WHERE identifiantVin=";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = vinASupprimer.getSelectedItem().toString();;
				
				// Si aucune etagere n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un vin a supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += t[0];
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ de suppression de vin a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + "n'a pas ete supprimee de votre base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}
					// Sinon, on met un message de succes et on remet le champ de suppression de vin a vide
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " a ete supprimee de votre base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}	
				}
				// Mise a jour des listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerVins au sud 
		nord.add(supprimerVins, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerVins le panel nord au centre
		panelSupprimerVins.add(nord, BorderLayout.CENTER);
	}
	
	// Creation du panel de Rangement
	public void createPanelRangement(){
		// On cree le panel panelRangement qui contiendra tous les elements de l'onglet Gestion Rangement de la FenetreAdmin
		panelRangement = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelRangement.setLayout(new BorderLayout());

		// On cree les onglets de tabbedPaneModifBouteilles
		createPanelRangementAjout();
		createPanelRangementModif();
		createPanelRangementSupprimer();
		
		// On les ajoute dans la barre d'onglet tabbedPaneRangementVins qui est la barre d'onglet pour la modification d'un vin.
		tabbedPaneRangementVins = new JTabbedPane();
		tabbedPaneRangementVins.addTab("Ranger un vins", panelRangementAjout);
		tabbedPaneRangementVins.addTab("Modifier le rangement d'un vins", panelRangementModif);
		tabbedPaneRangementVins.addTab("Supprimer le rangement d'un vins", panelRangementSupprimer);
		
		// On ajoute au panelVins la barre d'onglet tabbedPaneRangementVins au centre 
		panelRangement.add(tabbedPaneRangementVins, BorderLayout.CENTER);	
	}
		

	// Creation du panel de RangementAjout de vin/bouteille sur etagere
	public void createPanelRangementAjout(){
		// On cree le panel panelRangement qui contiendra tous les elements de l'onglet Rangement de la FenetreAdmin
		panelRangementAjout = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelRangementAjout.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton ajouterRangement
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		nord.setLayout(new GridLayout(2,0));
		
		// On cree le panel inner qui contiendra les champs pour ajouter un rangement de vin/bouteille dans la cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(5,2));
		
		// On cree la liste des etageres disponibles
		creerListeEtagere(listeetagere);
		
		// On ajoute le champ etagere au panel inner
		inner.add(new JLabel("Veuillez selectionner une etagere"));
		inner.add(listeetagere);
		
		// On cree la liste des bouteilles disponibles
		creerListeBouteilleRangement(listebouteille);
		
		// On ajoute le champ bouteille au panel inner
		inner.add(new JLabel("Veuillez selectionner une bouteille"));
		inner.add(listebouteille);
		
		
		// On cree la liste des vins disponibles
		creerListeVin(listevin);
		
		// On ajoute le champ vin au panel inner
		inner.add(new JLabel("Veuillez selectionner un vin"));
		inner.add(listevin);
		
		dateVins = new JTextField();
		// On met la taille du JTextField a 200px de long et 30 px de hauteur
		dateVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Annee du vin dans le panel inner
		inner.add(new JLabel("Annee de mise en bouteille :"));
		inner.add(dateVins);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On cree un bouton ajouterRangement
		JButton ajouterRangement = new JButton("Ranger le vins");
		
		// On lui attribue un ecouteur
		ajouterRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'insertion
				String requeteInsert = "INSERT INTO bouteille_has_vins(Bouteille_identifiantBouteille, Vins_identifiantVin, dateVin,Etagère_identifiantEtagère) VALUES (";
				// On prepare la requete de selection de bouteilles
				String requeteSelectBottle = "SELECT * FROM bouteille WHERE taille=";
				String idBottle = null;
				// On prepare la requete de selection d'etageres
				String requeteSelectEtagere = "SELECT * FROM etagère WHERE positionEtagère='";
				String idEtagere = null;
				
				// On cree un tableau de String pour recuperer les champs pour le rangement du vin/bouteille
				String [] t = new String[4];
				t[0] = listeetagere.getSelectedItem().toString();
				t[1] = listebouteille.getSelectedItem().toString();
				t[2] = listevin.getSelectedItem().toString();
				t[3] = dateVins.getText();
				
				// Si c'est une recherche par annee on fait la requete et on affiche le panel bottom des resultats.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandes.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					listeetagere.setSelectedItem("");
					listebouteille.setSelectedItem("");
					listevin.setSelectedItem("");
					dateVins.setText("");
				}
				else
				{
					// Si la date possede plus de 4 caracteres, nous affichons un message d'erreur et nous mettons le champ date a vide.
					if(t[3].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une date coherante.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						dateVins.setText("");
					}
					else
					{
					
						// On fini de preparer les requetes de selection avec les champs recuperes.
						requeteSelectBottle += t[1] + "";
						requeteSelectEtagere += t[0] + "\'";
						// On effectue les deux select
						ResultSet statutBottle = bdd.MyConnexionSelect(requeteSelectBottle);
						ResultSet statutEtagere = bdd.MyConnexionSelect(requeteSelectEtagere);
							try {
								// Si la selection des bouteilles a donner un resultat on recupere l'identifiant du premier resultat
								if(statutBottle.first()){
									idBottle = statutBottle.getString("identifiantBouteille");
								}
								// Sinon, on affiche une erreur et on dit que l'on a pas selectionne de bouteille. On met le champ a vide.
								else
								{
									JOptionPane.showMessageDialog(null, "Une bouteille de " + t[1] + " litre n'a pas ete selectionne dans la base de donnees", "Erreur", JOptionPane.ERROR_MESSAGE);
									listebouteille.setSelectedItem("");
								}
								// Si la selection des etageres a donner un resultat, on recupere l'identifiant du premier resultat.
								if(statutEtagere.first()){
									idEtagere = statutEtagere.getString("identifiantEtagère");
								}
								// Sinon, message d'erreur et on met le champ a vide
								else
								{
									JOptionPane.showMessageDialog(null, "L'etagere a la position " + t[0] + " n'a pas ete selectionne dans la base de donnees", "Erreur", JOptionPane.ERROR_MESSAGE);
									listeetagere.setSelectedItem("");
								}
								
							} catch (HeadlessException | SQLException e) {
								e.printStackTrace();
							}
							// Si on a recupere une bouteille et une etagere
							if(idBottle != null && idEtagere != null)
							{
								// On fini de preparer la requete d'insert avec tous les champs necessaires a la requete.
								requeteInsert += idBottle + "," + t[2] + "," + t[3] + "," + idEtagere + ")";
								// On effectue le insert
								boolean status = bdd.MyConnexionInsertDeleteUpdate(requeteInsert);
								// Si la requete n'a pas fonctionne, on aura une erreur. On met tous les champs a vide
								if(!(status)){
									JOptionPane.showMessageDialog(null, "Il y a eu une erreur", "Erreur", JOptionPane.ERROR_MESSAGE);
									listeetagere.setSelectedItem("");
									listebouteille.setSelectedItem("");
									listevin.setSelectedItem("");
									dateVins.setText("");
								}
								// Sinon, on a un message de succes et on met tous les champs a vide.
								else
								{
									JOptionPane.showMessageDialog(null, "Le vin a ete range", "Information", JOptionPane.INFORMATION_MESSAGE);
									listeetagere.setSelectedItem("");
									listebouteille.setSelectedItem("");
									listevin.setSelectedItem("");
									dateVins.setText("");
								}
							}
					}
				}
				// On met a jour les listes contenu dans ce panel : listeetagere, listebouteille et listevin
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton ajouterRangement
		nord.add(ajouterRangement, BorderLayout.SOUTH);
		
		// On ajoute au panelRangement le panel nord au centre
		panelRangementAjout.add(nord,BorderLayout.CENTER);
	}
	
	public void createPanelRangementModif(){
		// On cree le panel panelRangementSupprimer qui contiendra tous les elements de l'onglet RangementSupprimer de la FenetreAdmin
		panelRangementModif = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelRangementModif.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton supprimerVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour supprimer un vin
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste des vins a supprimer
		creerListeRangement(rangementAModifier);
		
		// On ajoute au panel inner la liste des vins a supprimer
		inner.add(new JLabel("Rangement a modifier :"));
		inner.add(rangementAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		final JPanel bottomRangement = new JPanel();
		bottomRangement.setLayout(new GridLayout(5,2));
		
		final JTextField bouteilleN = new JTextField();
		final JTextField vinN = new JTextField();
		final JTextField etagereN = new JTextField();
		final JTextField dateN = new JTextField();
		
		bottomRangement.add(new JLabel("Bouteille : "));
		bouteilleN.setEditable(false);
		bottomRangement.add(bouteilleN);
		bottomRangement.add(new JLabel("Vin : "));
		vinN.setEditable(false);
		bottomRangement.add(vinN);
		bottomRangement.add(new JLabel("Etagère : "));
		etagereN.setEditable(false);
		bottomRangement.add(etagereN);
		bottomRangement.add(new JLabel("Date : "));
		bottomRangement.add(dateN);
		
		bottomRangement.setVisible(false);
		
		// On cree un bouton supprimerVins
		JButton modifierRangement = new JButton("Modifier le rangement selectionne.");
		
		// On lui attribue un ecouteur
		modifierRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "SELECT * FROM bouteille_has_vins WHERE Bouteille_identifiantBouteille=";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = rangementAModifier.getSelectedItem().toString();;
				
				String idBout = "";
				String idVin = "";
				String idEtagere = "";
				
				// Si aucune etagere n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un rangement à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					idBout = t[0].substring(0, 1);
					idVin = t[0].substring(1, 2);
					idEtagere = t[0].substring(2, 3);
				
					if(!(idBout.isEmpty()) && !(idVin.isEmpty()) && !(idEtagere.isEmpty()))
						// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
						requete += idBout + " AND Vins_identifiantVin=" + idVin + " AND Etagère_identifiantEtagère=" + idEtagere;
						// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
						ResultSet statut = bdd.MyConnexionSelect(requete);
						// Bloc try catch pour la gestion des excetions du a  la requete.					
							try {
								// Si la requete a renvoye des donnees, nous mettons ces donnees dans les champs de modification de la bouteille 
								if(statut.first()){
									bouteilleN.setText(statut.getString("Bouteille_identifiantBouteille"));
									vinN.setText(statut.getString("Vins_identifiantVin"));
									etagereN.setText(statut.getString("Etagère_identifiantEtagère"));
									dateN.setText(statut.getString("dateVin"));
								}
								// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier a vide.
								else
								{
									JOptionPane.showMessageDialog(null, "Le rangement n'a pas été sélectionné.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
									rangementAModifier.setSelectedItem("");
								}
							} catch (HeadlessException | SQLException e) {
								e.printStackTrace();
							}
							// Nous affichons le panel bottomBouteilles
							bottomRangement.setVisible(true);;
					}
				}
		});
		
		// On ajoute au panel nord le bouton modifierBouteilles au sud.
		nord.add(modifierRangement, BorderLayout.SOUTH);
		
		// On cree un bouton updateDataBottle
		JButton updateDataRangement = new JButton("Valider les donnees\n");	
		// On lui attribue un ecouteur
		updateDataRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete d'update
				String requete = "UPDATE bouteille_has_vins SET ";
				// On cree un tableau de String pour recuperer le champ pour la modification de l'etagere
				String [] t = new String[4];
				t[0] = bouteilleN.getText();
				t[1] = vinN.getText();
				t[2] = etagereN.getText();
				t[3] = dateN.getText();
				
				// Si le champ de positionEtagere est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					if(t[3].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez donner une date correcte.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// On fini la preparation de la requete en ajoutant les parametres necessaires a la requete
					requete += "dateVin="+ t[3];
					requete += " WHERE Bouteille_identifiantBouteille=" + t[0] + " AND Vins_identifiantVin=" + t[1] + " AND Etagère_identifiantEtagère=" + t[2];
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a reussi, nous affichons un message pour dire que la bouteille a ete modifie.
					// Et nous recachons le panel bottomBouteilles.
					if(statut){
						JOptionPane.showMessageDialog(null, "La rangement a ete modifie dans la base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						rangementAModifier.setSelectedItem("");
						bottomRangement.setVisible(false);
					}
					// Sinon, nous affichons un message d'erreur
					else
					{
						JOptionPane.showMessageDialog(null, "La rangement n'a pas ete modifie dans la base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// On effectue une mise a jour des listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
					MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
				}
				
			}
		});
		// On ajoute au panel bottomBouteilles le bouton updateDataBottle
		bottomRangement.add(updateDataRangement);
	
		// On ajoute au panelModifBouteilles le panel nord a la position nord et le panel bottomBouteilles a la position sud.
		panelRangementModif.add(nord, BorderLayout.NORTH);
		panelRangementModif.add(bottomRangement, BorderLayout.SOUTH);	
	}
	
	// Création du panel de suppression des rangements
	public void	createPanelRangementSupprimer(){
		// On cree le panel panelRangementSupprimer qui contiendra tous les elements de l'onglet RangementSupprimer de la FenetreAdmin
		panelRangementSupprimer = new JPanel();
		// On lui dit que l'affichage des champs se par region
		panelRangementSupprimer.setLayout(new BorderLayout());

		// On cree le panel nord qui le panel inner et le bouton supprimerVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par region
		nord.setLayout(new BorderLayout());
		
		// On cree le panel inner qui contiendra les champs pour supprimer un vin
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cree la liste des vins a supprimer
		creerListeRangement(rangementASupprimer);
		
		// On ajoute au panel inner la liste des vins a supprimer
		inner.add(new JLabel("Rangement a supprimer:"));
		inner.add(rangementASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cree un bouton supprimerVins
		JButton supprimerRangement = new JButton("Supprimer le rangement selectionne.");
		
		// On lui attribue un ecouteur
		supprimerRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prepare la requete de suppression
				String requete = "DELETE FROM bouteille_has_vins WHERE Bouteille_identifiantBouteille=";
				// On cree un tableau de String pour recuperer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = rangementASupprimer.getSelectedItem().toString();;
				
				String idBout = "";
				String idVin = "";
				String idEtagere = "";
				
				// Si aucune etagere n'est selectionnee, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner un rangement a supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					idBout = t[0].substring(0, 1);
					idVin = t[0].substring(1, 2);
					idEtagere = t[0].substring(2, 3);
				}
				if(!(idBout.isEmpty()) && !(idVin.isEmpty()) && !(idEtagere.isEmpty()))
					// On fini la preparation de la requete en ajoutant le parametre necessaire a la requete
					requete += idBout + " AND Vins_identifiantVin=" + idVin + " AND Etagère_identifiantEtagère=" + idEtagere;
					// Nous effectuons la requete a l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requete a echouer, nous affichons un message d'erreur et nous remettons le champ de suppression de vin a vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le rangement avec la bouteille " + idBout + ", contenant le vin " + idVin + " sur l'étagère " + idEtagere + " n'a pas ete supprimee de votre base de donnees.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						rangementASupprimer.setSelectedItem("");
					}
					// Sinon, on met un message de succes et on remet le champ de suppression de vin a vide
					else
					{
						JOptionPane.showMessageDialog(null, "Le rangement avec la bouteille " + idBout + ", contenant le vin " + idVin + " sur l'étagère " + idEtagere + " a ete supprimee de votre base de donnees.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						rangementASupprimer.setSelectedItem("");
					}	
				MAJpanelAjoutRangement(listeetagere, listebouteille, listevin);
				MAJpanelModifRangement(rangementASupprimer);
				MAJpanelSupprimerRangement(rangementASupprimer);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerVins au sud 
		nord.add(supprimerRangement, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerVins le panel nord au centre
		panelRangementSupprimer.add(nord, BorderLayout.CENTER);
	}
	
	// Fonction qui nous permet d'actualiser l'affichage du resultat de la requete dans onglet Recherche
	public void rafraichirDataRecherche(JPanel p, JTable o){
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
	
	// Fonction qui permet de rafraichir les listes de caves
	public void MAJpanelCave(JComboBox<String> p){
		p.removeAllItems();
		creerListeCave(p);
	}
	
	// Fonction qui permet de recuperer la liste des cave a vin de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
	public void creerListeCave(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		ResultSet statement = bdd.MyConnexionSelect("SELECT * FROM  caveavin WHERE Utilisateur_identifiantUtilisateur=" + idAdmin);
		try {
			while(statement.next()){
				String nomCaveSup =statement.getString("nomCave");
				c.addItem(nomCaveSup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fonction qui permet de rafraichir les listes de vins
	public void MAJpanelVins(JComboBox<String> p){
		p.removeAllItems();
		creerListeVin(p);
	}
	
	// Fonction qui permet de recuperer la liste des vins dans la base et de les organiser dans une JComboBox<String>
	public void creerListeVin(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM vins";
		ResultSet statement = bdd.MyConnexionSelect(req);
		try {
			while(statement.next()){
				String nomVinRes =statement.getString("identifiantVin");
				c.addItem(nomVinRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Fonction qui permet de rafraichir les listes de bouteilles
	public void MAJpanelBouteilles(JComboBox<String> p){
		p.removeAllItems();
		creerListeBouteille(p);
	}
	
	// Fonction qui permet de recuperer la liste des bouteilles dans la base et de les organiser dans une JComboBox<String>
	public void creerListeBouteille(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM bouteille";
		ResultSet statement = bdd.MyConnexionSelect(req);
		try {
			while(statement.next()){
				String tailleBouteilleRes = statement.getString("taille");
				c.addItem(tailleBouteilleRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Fonction qui permet de rafraichir les listes d"etageres
	public void MAJpanelEtageres(JComboBox<String> p){
		p.removeAllItems();
		creerListeEtagere(p);
	}
	
	// Fonction qui permet de recuperer la liste des etageres de la cave courante et de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
	public void creerListeEtagere(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM etagère WHERE CaveAVin_Utilisateur_identifiantUtilisateur = " + idAdmin + " AND CaveAVin_nomCave = '" + caveAVinCourante + "'";
		ResultSet statement = bdd.MyConnexionSelect(req);
		try {
			while(statement.next()){
				String posEtagereRes =statement.getString("positionEtagère");
				c.addItem(posEtagereRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fonction qui permet de rafraichir les listes de contenu dans le panel RangementAjout
	public void MAJpanelAjoutRangement(JComboBox<String> le, JComboBox<String> lb, JComboBox<String> lv){
		le.removeAllItems();
		creerListeEtagere(le);
		lb.removeAllItems();
		creerListeBouteilleRangement(lb);
		lv.removeAllItems();
		creerListeVin(lv);
		
	}
	
	public void MAJpanelModifRangement(JComboBox<String> lr){
		lr.removeAllItems();
		creerListeRangement(lr);
	}
	
	public void MAJpanelSupprimerRangement(JComboBox<String> lr){
		lr.removeAllItems();
		creerListeRangement(lr);
	}
	
	private void creerListeRangement(JComboBox<String> lr) {
		lr.setPreferredSize(new Dimension(200,30));
		lr.addItem("");
		String req = "SELECT * FROM bouteille_has_vins";
		ResultSet statement = bdd.MyConnexionSelect(req);
		try {
			while(statement.next()){
				String nomRangement = statement.getString("Bouteille_identifiantBouteille");
				nomRangement += statement.getString("Vins_identifiantVin");
				nomRangement += statement.getString("Etagère_identifiantEtagère");
				lr.addItem(nomRangement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Fonction qui permet de recuperer la liste des bouteilles qui ne sont pas deja pleine de vin dans la base et de les organiser dans une JComboBox<String> (pour onglet Rangement)
	public void creerListeBouteilleRangement(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		// Requete pour ne selectionner que les bouteilles non utilisees.  
		String req = "SELECT * FROM bouteille"; //LEFT JOIN bouteille_has_vins ON bouteille.identifiantBouteille = bouteille_has_vins.Bouteille_identifiantBouteille WHERE bouteille_has_vins.Bouteille_identifiantBouteille IS NULL";
		ResultSet statement = bdd.MyConnexionSelect(req);
		try {
			while(statement.next()){
				String tailleBouteilleRes = statement.getString("taille");
				c.addItem(tailleBouteilleRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

