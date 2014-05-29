// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

//Import pour les écouteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requête faites à  la base de données et pour la gestion des Exceptions
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
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;






public class FenetreAdmin extends JFrame{

	private static final long serialVersionUID = -122199509843219096L;
	// Champs pour la connexion à  la base de données avec le driver jdbc.
	private InteractionBDD bdd = new InteractionBDD();
	
	// Champs necéssaire pour garder l'identifiant de l'administrateur, le nom de la cave courante et le vin courant (pour les modifications de ces deux derniers)
	private String idAdmin;
	private String caveAVinCourante;
	private String vinCourant;
	
	// La fenêtre principale contient 5 onglets principals
	private		JTabbedPane tabbedPane = new JTabbedPane();;
	private		JPanel		panelRecherche;
	private		JPanel		panelAjoutCave;
	private		JPanel		panelModificationCave;
	private		JPanel		panelSuppressionCave;
	private		JPanel		panelModificationMDP;
	
	// Champs nécessaire pour la recherche de vins (Onglet Recherche)
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Année");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	// Champ pour le résultat de la recherche (Onglet Recherche)
	JTable tableau;
	
	// Champs nécessaire pour l'ajout d'une cave (Onglet AjoutCave)
	private JTextField nomCaveAAjouter = new JTextField();
	private JTextArea commentaireCave = new JTextArea();
	private JButton ajouterCave = new JButton("Ajouter Cave");
	
	// Champs nécessaire pour la modification d'une cave (Onglet ModificationCave)
	private JComboBox<String> caveAModifier = new JComboBox<String>();
	private JButton modifierCave = new JButton("Modifier Cave");
	
	// Dans le panel de l'onglet de modification de cave, nous avons 4 onglets
	private		JTabbedPane tabbedPaneModif;
	private		JPanel		panelEtageres;
	private		JPanel		panelBouteilles;
	private		JPanel		panelVins;
	private		JPanel		panelRangement;
	
	// Dans le panel de l'onglet de modification d'étagères, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifEtageres;
	private		JPanel		panelAjoutEtageres;
	private		JPanel		panelModifEtageres;
	private		JPanel		panelSupprimerEtageres;
	
	// Champs nécessaire pour la modification d'une étagère (Onglet ModifEtagère)
	private JComboBox<String> etagereAModifier = new JComboBox<String>();
	
	// Champs nécessaire pour la suppression d'une étagère (Onglet SupprimerEtagère)
	private JComboBox<String> etagereASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de bouteilles, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifBouteilles;
	private		JPanel		panelAjoutBouteilles;
	private		JPanel		panelModifBouteilles;
	private		JPanel		panelSupprimerBouteilles;
		
	// Champs nécessaire pour la modification d'une bouteille (Onglet ModifBouteilles)
	private JComboBox<String> bouteilleAModifier = new JComboBox<String>();
	
	// Champs nécessaire pour la suppression d'une bouteille (Onglet SupprimerBouteilles)
	private JComboBox<String> bouteilleASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de vins, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifVins;
	private		JPanel		panelAjoutVins;
	private		JPanel		panelModifVins;
	private		JPanel		panelSupprimerVins;
	
					
	// Champs nécessaire pour l'ajout d'un vin (Onglet AjoutVin)
	private		JTextField regionVins;
	private		JTextField domaineVins;
	private		JTextField châteauVins;
	private		JComboBox<String> couleurVins;
	private		JTextField cepageVins;
	private		JTextField dateVins;
	
	// Champs nécessaire pour la modification d'un vin (Onglet ModifVins)
	private JComboBox<String> vinAModifier = new JComboBox<String>();
	
	// Champs nécessaire pour la suppression d'un vin (Onglet SupprimerVins)
	private JComboBox<String> vinASupprimer = new JComboBox<String>();
	
	// Champs nécessaire pour le rangement des vins/bouteilles sur les étagères (Onglet Rangement)
	private JComboBox<String> listeetagere = new JComboBox<String>();
	private JComboBox<String> listebouteille = new JComboBox<String>();
	private JComboBox<String> listevin = new JComboBox<String>();
		
	// Champs nécessaire pour la suppression d'une cave (Onglet SuppressionCave)
	private JComboBox<String> caveASupprimer = new JComboBox<String>();
	private JButton supprimerCave = new JButton("Supprimer Cave");
	
	// Champs nécessaire pour la modification du mot de passe d'une cave (Onglet ModificationMDP)
	private JTextField oldMDP;
	private JTextField newMDP;
	private JTextField confirmationMDP;
	
	
	// Constructeur avec comme paramètre l'identifiant du marchand de vin de la fenêtre de Admin
   	public FenetreAdmin(String id) {
   		// On appel le constructeur de JFrame avec un paramètre qui est le nom de la fenêtre
   		super("La cave à vin - Gestion administrateur");
   		// On enregistre dans la variable globale l'identifiant du marchand de vin
		this.idAdmin = id;
		// Ensuite on initialise cette fenêtre
		init();
	}
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// On met la dimension du panel principal à la taille de l'écran
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		

		// On créé le panel topPanel qui contiendra tous les éléments de la FenetreAdmin
		JPanel topPanel = new JPanel();
		// On lui dit que l'affichage des champs se par région
		topPanel.setLayout(new BorderLayout());
					
		// On ajoute le topPanel à la fenêtre
		getContentPane().add(topPanel);
		
		// On créé les différents onglets principaux
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
	
	// Création du panel de Recherche
	public void createRecherche()
	{
		// On créé le panel panelRecherche qui contiendra tous les éléments de l'onglet Recherche de la FenetreAdmin
		panelRecherche = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelRecherche.setLayout(new BorderLayout());
	
		// On instancie les champs de notre fenetre (JTextField pour lla recherche par année et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On créé le panel top qui contiendra le bouton de recherche et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par région
		top.setLayout(new BorderLayout());
		
		// On créé le panel toptop qui contiendra les champs de recherche de vins
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
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par année mais vous n'avez pas renseigné de valeur\nVeuillez en renseigner une", "Erreur", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseigné de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseigné de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
				ResultSet resultRequete = bdd.MyConnexionSelect(requete);
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

	// Création du panel d'Ajout d'une cave
	public void createAjout()
	{
		// On créé le panel panelAjoutCave qui contiendra tous les éléments de l'onglet AjoutCave de la FenetreAdmin
		panelAjoutCave = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelAjoutCave.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour ajouter une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(2,2));
		
		// On met la dimension du JTextField associé au nom de la cave à  200px de large et 30px de hauteur
		nomCaveAAjouter.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Nom de la cave :"));
		inner.add(nomCaveAAjouter);
				
		// On met la dimension du JTextField associé au commentaire de la cave à  200px de large et 30px de hauteur
		commentaireCave.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Commentaire à faire :"));
		inner.add(commentaireCave);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un écouteur au bouton aujouterCave
		ajouterCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'ajout
				String requete = "INSERT INTO caveavin(nomCave, commentaire, Utilisateur_identifiantUtilisateur) VALUES (";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[2];
				t[0] = nomCaveAAjouter.getText();
				t[1] = commentaireCave.getText();
				
				// Si au moins un argument n'est pas renseigné par l'utilisateur, nous lui affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les trois champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
					requete += "'" + t[0]+ "','" + t[1] + "'," + idAdmin + ")";
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons les champs d'ajout de la cave à vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// Sinon on affiche un message de succès et nous remettons les champs d'ajout de la cave à vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// On fait appel à MAJpanelModificationCave(JComboBox<String> caveAModifier) pour rafraîchir les données de la liste de caveAModifier.
					MAJpanelCave(caveAModifier);
					// On fait appel à MAJpanelSuppressionCave(JComboBox<String> caveASupprimer) pour rafraîchir les données de la liste de caveASupprimer.
					MAJpanelCave(caveASupprimer);
				}
			}
		});
		
		// On au panel nord le bouton ajouterCave au sud
		nord.add(ajouterCave, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutCave le panel nord au centre
		panelAjoutCave.add(nord, BorderLayout.CENTER);
	}

	// Création du panel de modification d'une cave
	public void createModification()
	{
		// On créé le panel panelModificationCave qui contiendra tous les éléments de l'onglet ModifCave de la FenetreAdmin
		panelModificationCave = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelModificationCave.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton modifierCave
		final JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());

		// On créé le panel inner qui contiendra les champs pour modifier une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On créé la liste de cave à modifier
		creerListeCave(caveAModifier);

		// On ajoute au panel inner la liste de cave à modifier
		inner.add(new JLabel("Nom de la cave à modifier:"));
		inner.add(caveAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un écouteur au bouton modifierCave
		modifierCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On créé un tableau de String pour récupérer les champs pour la modification de la cave
				String [] t = new String[1];
				t[0] = caveAModifier.getSelectedItem().toString();;
				
				// Si aucune cave n'est sélectionnée, on affiche un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une cave à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On met dans une variable globale le nom de la cave sélectionnée
					caveAVinCourante = t[0];
					// On cache le panel nord.
					nord.setVisible(false);
					// On affiche la table d'onglet tabbedPaneModif qui permet la gestion de la cave à vin.
					tabbedPaneModif.setVisible(true);
					// On ajoute au panelModificationCave la barre d'onglet tabbedPaneModif au centre
					panelModificationCave.add(tabbedPaneModif, BorderLayout.CENTER);
					
				}
				// On met à jour les listes de vins, de bouteilles et d'étagères contenu dans les onglets de tabbedPaneModif
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelBouteilles(bouteilleAModifier);
				MAJpanelBouteilles(bouteilleASupprimer);
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On au panel nord le bouton modifierCave au sud
		nord.add(modifierCave, BorderLayout.SOUTH);
		
		// On ajoute au panelModificationCave le panel nord au centre
		panelModificationCave.add(nord, BorderLayout.CENTER);
		
		// On créé les onglets de tabbedPaneModif
		createPanelEtageres();
		createPanelBouteilles();
		createPanelVins();
		createPanelRangement();
		
		// On les ajoute dans la barre d'onglet tabbedPaneModif qui est la barre d'onglet pour la modification d'une cave.
		tabbedPaneModif = new JTabbedPane();
		tabbedPaneModif.addTab("Gestion étagères", panelEtageres);
		tabbedPaneModif.addTab("Gestion bouteilles", panelBouteilles);
		tabbedPaneModif.addTab("Gestion vins", panelVins);
		tabbedPaneModif.addTab("Rangement des vins", panelRangement);
		// On cache la tabbedPaneModif
		tabbedPaneModif.setVisible(false);
		
	}
	
	// Création du panel de suppression d'une cave
	public void createSuppression()
	{
		// On créé le panel panelSuppressionCave qui contiendra tous les éléments de l'onglet SuppressionCave de la FenetreAdmin
		panelSuppressionCave = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelSuppressionCave.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On créé la liste de cave à supprimer
		creerListeCave(caveASupprimer);
		
		// On ajoute au panel inner la liste de cave à supprimer
		inner.add(new JLabel("Nom de la cave à supprimer:"));
		inner.add(caveASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un écouteur au bouton supprimerCave
		supprimerCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de suppression
				String requete = "DELETE FROM caveavin WHERE nomCave='";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = caveASupprimer.getSelectedItem().toString();;
				
				// Si aucune cave n'est sélectionnée, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une cave à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0] + "'";
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ de suppression de la cave à vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + "n'a pas été supprimée de votre base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succès et nous remettons le champ de suppression de la cave à vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a été supprimée de votre base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// On met à jour les listes de caveAModifier et de caveASupprimer
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
	
	// Création du panel de modification du motdepasse
	public void createMDP(){
		// On créé le panel panelModificationMDP qui contiendra tous les éléments de l'onglet ModificationMDP de la FenetreAdmin
		panelModificationMDP = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelModificationMDP.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour modifier son mot de passe dans la base de données
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
		
		// On créé un bouton Changer le mot de passe
		JButton changerMDP = new JButton("Changer le mot de passe.\n");
		
		// On lui attribut un écouteur
		changerMDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête pour récupérer l'ancien mot de passe de l'utilisateur
				String select = "SELECT * FROM cave.utilisateur WHERE identifiantUtilisateur=";
				// On prépare la requête mettre à jour le mot de passe de l'utilisateur avec le nouveau mot de passe
				String update = "UPDATE cave.utilisateur SET motDePasse = MD5('";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[3];
				t[0] = oldMDP.getText();
				t[1] = newMDP.getText();
				t[2] = confirmationMDP.getText();
				
				// Si au moins un argument n'est pas renseigné par l'utilisateur, nous lui affichons un message d'erreur et on met tous les champs à vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					oldMDP.setText("");
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Si le nouveau mot de passe et la confirmation de celui-ci ne sont pas égals.
				// On affiche un message d'erreur et nous mettons tous les champs à vide.
				if(!t[1].equals(t[2])){
					JOptionPane.showMessageDialog(null, "Le nouveau mot de passe et la confirmation ne sont pas identique.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Sinon
				else
				{
					// On fini la préparation de la requête de récupération de l'ancien mot de passe en ajoutant le paramètre nécessaire à la requête.
					select += idAdmin + " AND motDePasse=MD5('" + t[0] + "')";
					// On récupère le resultat de la requête à l'aide de MyConnexionSelect(String requête)
					ResultSet changeMDP = bdd.MyConnexionSelect(select);
					try {
						// Si le résultat de la requête select contient une ou des données
						if(changeMDP.next()){
							// On fini la préparation de la requête de mise à jour du en ajoutant les paramètres nécessaires à la requête.
							update += t[2] + "') WHERE utilisateur.identifiantUtilisateur = " + idAdmin;
							// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = bdd.MyConnexionInsertDeleteUpdate(update);
							// Si la requête à réussi, nous affichons un message pour dire que le mot de passe a été changé.
							// Et nous remettons tous les champs à vide.
							if(statut){
								JOptionPane.showMessageDialog(null, "Votre mot de passe a été changé.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
							// Sinon, nous affichons un message d'erreur pour dire que le mot de passe n'a pas été changé.
							// Et nous remettons tous les champs à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Votre mot de passe n'a pas été changé.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
								
						}
						// Si le résultat de la requête select ne contient pas de données, on affiche un message d'erreur et nous mettons tous les champs à vide.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est erroné.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
	
	// Création du panel de Gestion des étagères
	public void createPanelEtageres(){
		// On créé le panel panelEtageres qui contiendra tous les éléments de l'onglet GestionEtagère de la FenetreAdmin
		panelEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelEtageres.setLayout(new BorderLayout());

		// On créé les onglets de tabbedPaneModif
		createPanelAjoutEtageres();
		createPanelModifEtageres();
		createPanelSupprimerEtageres();
				
		// On les ajoute dans la barre d'onglet tabbedPaneModifEtageres qui est la barre d'onglet pour la modification d'une étagère.
		tabbedPaneModifEtageres = new JTabbedPane();
		tabbedPaneModifEtageres.addTab("Ajouter une étagère", panelAjoutEtageres);
		tabbedPaneModifEtageres.addTab("Modifier une étagère", panelModifEtageres);
		tabbedPaneModifEtageres.addTab("Supprimer une étagère", panelSupprimerEtageres);
		
		// On ajoute au panelEtageres la barre d'onglet tabbedPaneModifEtageres au centre 
		panelEtageres.add(tabbedPaneModifEtageres, BorderLayout.CENTER);		
		
	}
	
	// Création du panel d'Ajout d'étagère
	public void createPanelAjoutEtageres(){
		
		// On créé le panel panelAjoutEtageres qui contiendra tous les éléments de l'onglet AjoutEtageres de la FenetreAdmin
		panelAjoutEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelAjoutEtageres.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new GridLayout(2,0));
		
		// On créé le panel inner qui contiendra les champs pour ajouter une étagère
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
		
		
		// Création et ajout du champs position dans le panel innerNord
		final JTextField position = new JTextField();
		innerNord.add(new JLabel("Position de l'étagère dans la cave (sans ' ou \" ): "));
		innerNord.add(position);
		
		// On ajoute au panel nord le panel innerNord
		nord.add(innerNord);
		
		// On créé un bouton addEtagere
		JButton addEtagere = new JButton("Ajouter une étagère");
		
		// On lui attribut un écouteur
		addEtagere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'ajout
				String requete = "INSERT INTO etagère(positionEtagère, CaveAVin_nomCave, CaveAVin_Utilisateur_identifiantUtilisateur) VALUES ('";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = position.getText();
				
				// Si l'utilisateur n'a pas donné de position, nous lui affichons un message d'erreur et nous mettons le champ à vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					position.setText("");
				}
				else{
					// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
					requete += t[0] + "','"+ caveAVinCourante + "'," + idAdmin + ")";
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ à vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une étagère à la position " + t[0] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						position.setText("");
					}
					// Sinon on affiche un message de succès et nous remettons le champ à vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une étagère à la position " + t[0] + " a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						position.setText("");
					}
				}
				// On met à jour les listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton addEtagere
		nord.add(addEtagere);
		
		// On ajoute au panelAjoutEtageres le panel nord au nord
		panelAjoutEtageres.add(nord,BorderLayout.NORTH);
	}

	// Création du panel de modification d'étagère
	public void createPanelModifEtageres(){

		// On créé le panel panelModifEtageres qui contiendra tous les éléments de l'onglet ModifEtageres de la FenetreAdmin
		panelModifEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelModifEtageres.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour modifier une étagère
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On créé la liste des étagères.
		creerListeEtagere(etagereAModifier);
		
		// On ajoute le champs etagereAModifier au panel inner
		inner.add(new JLabel("La position à modifier: \n"));
		inner.add(etagereAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On créé le panel bottomEtageres qui contiendra les champs à remplir avant de modifier une étagère
		final JPanel bottomEtageres = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomEtageres.setLayout(new GridLayout(5,2));
		
		// On créé les JTextField pour ajouter les données de l'étagère à modifier dedans.
		final JTextField idEtagere = new JTextField();
		final JTextField positionEtagere = new JTextField();
		final JTextField idCave = new JTextField();
		final JTextField idUser = new JTextField();
		
		// On ajoute au panel bottomEtageres le champ identifiant Etagère
		bottomEtageres.add(new JLabel("Identifiant étagère : "));
		bottomEtageres.add(idEtagere);
		// On rend ce champ non modifiable
		idEtagere.setEditable(false);
		
		// On ajoute au panel bottomEtageres le champ position Etagère
		bottomEtageres.add(new JLabel("Position de l'étagère (sans ' ou \" ) : "));
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
		
		// On créé un nouveau bouton modifierEtageres
		JButton modifierEtageres = new JButton("Modifier l'étagère sélectionnée");
		
		// On lui attribue un écouteur
		modifierEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de selection
				String requete = "SELECT * FROM etagère WHERE positionEtagère='";
				// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
				String [] t = new String[1];
				t[0] = etagereAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une étagère à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0] + "'";
					// On récupère le resultat de la requête à l'aide de MyConnexionSelect(String requête)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions dû à  la requête.
					try {
						// Si la requête a renvoyé des données, nous mettons ces données dans les champs de modification de l'étagère 
						if(statut.first()){
							idEtagere.setText(statut.getString("identifiantEtagère"));
							positionEtagere.setText(statut.getString("positionEtagère"));
							idCave.setText(statut.getString("CaveAVin_nomCave"));
							idUser.setText(statut.getString("CaveAVin_Utilisateur_identifiantUtilisateur"));
						}
						// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier à vide.
						else
						{
							JOptionPane.showMessageDialog(null, "L'étagère à la position " + t[0] + " n'a pas été sélectionner dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
		
		// Nous crééons un bouton updateDataEtagere.
		JButton updateDataEtagere = new JButton("Valider les données\n");
		
		// On lui attribue un écouteur
		updateDataEtagere.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// On prépare la requête d'update
						String requete = "UPDATE etagère SET ";
						// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
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
							// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
							requete += "positionEtagère='"+ t[1] + "'";
							requete += " WHERE identifiantEtagère=" + t[0];
							// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
							// Si la requête à réussi, nous affichons un message pour dire que l'étagère a été modifié.
							// Et nous recachons le panel bottomEtageres.
							if(statut){
								JOptionPane.showMessageDialog(null, "L'étagère d'identifiant " + t[0] + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								bottomEtageres.setVisible(false);
							}
							// Sinon, nous affichons un message d'erreur
							else
							{
								JOptionPane.showMessageDialog(null, "L'étagère d'identifiant " + t[0] + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						// On effectue une mise à jour des listes etagereAModifier et etagereASupprimer
						MAJpanelEtageres(etagereAModifier);
						MAJpanelEtageres(etagereASupprimer);
						MAJpanelRangement(listeetagere, listebouteille, listevin);
					}
				});
		// On ajoute au panel bottomEtageres le bouton updateDataEtagere
		bottomEtageres.add(updateDataEtagere);
		
		// On ajoute au panelModifEtageres le panel nord à la position nord et le panel bottomEtageres à la position sud.
		panelModifEtageres.add(nord, BorderLayout.NORTH);
		panelModifEtageres.add(bottomEtageres, BorderLayout.SOUTH);
	}

	// Création du panel de suppression d'étagère
	public void createPanelSupprimerEtageres(){
		// On créé le panel panelSupprimerEtageres qui contiendra tous les éléments de l'onglet SupprimerEtageres de la FenetreAdmin
		panelSupprimerEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelSupprimerEtageres.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton supprimerEtageres
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On créé le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On créé la liste des étagères à supprimer
		creerListeEtagere(etagereASupprimer);
		
		// On ajoute au panel inner la liste d'étagères à supprimer
		inner.add(new JLabel("Etagère(s) à supprimer:"));
		inner.add(etagereASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);

		// On créé un bouton supprimerEtageres
		JButton supprimerEtageres = new JButton("Supprimer l'étagère sélectionnée.");
		
		// On lui attribue un écouteur
		supprimerEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de suppression
				String requete = "DELETE FROM etagère WHERE positionEtagère='";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = etagereASupprimer.getSelectedItem().toString();;
				
				// Si aucune étagère n'est sélectionnée, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une étagère à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0] + "'";
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean status = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ de suppression de l'étagère à vide.
					if(!(status)){
						JOptionPane.showMessageDialog(null, "L'étagère de la position " + t[0] + " n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succès et nous remettons le champ de suppression de l'étagère à vide.
					else
					{
						JOptionPane.showMessageDialog(null, "L'étagère de la position " + t[0] + " a pas été supprimé dans la base de données", "Information", JOptionPane.INFORMATION_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
				}
				// Mise à jour des listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerEtageres
		nord.add(supprimerEtageres);
		
		// On ajoute au panelSupprimerEtageres le panel nord au centre
		panelSupprimerEtageres.add(nord, BorderLayout.CENTER);
	}
	
	// Création du panel de Gestion des bouteilles
	public void createPanelBouteilles(){
		// On créé le panel panelBouteilles qui contiendra tous les éléments de l'onglet GestionBouteille de la FenetreAdmin
		panelBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelBouteilles.setLayout(new BorderLayout());

		// On créé les onglets de tabbedPaneModifBouteilles
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
	
	// Création du panel d'Ajout de bouteilles
	public void createPanelAjoutBouteilles(){
		// On créé le panel panelAjoutBouteilles qui contiendra tous les éléments de l'onglet AjoutBouteilles de la FenetreAdmin
		panelAjoutBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelAjoutBouteilles.setLayout(new BorderLayout());
	
		// On créé le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new GridLayout(2,1));
		
		// On créé le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
				
		// On ajoute le champ volume de la bouteille dans le panel innerNord
		innerNord.add(new JLabel("Volume de la bouteille (en L): "));
		final JTextField capacity = new JTextField();
		innerNord.add(capacity);
		nord.add(innerNord);
		
		// On créé un bouton addBouteille
		JButton addBouteille = new JButton("Ajouter une bouteille");
		
		// On lui attribut un écouteur
		addBouteille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'ajout
				String requete = "INSERT INTO bouteille(taille) VALUES (";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = capacity.getText();
				
				
				
				// Si l'utilisateur n'a pas donné de volume pour la bouteille, nous lui affichons un message d'erreur et nous mettons le champ à vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					capacity.setText("");
				}
				else{
					// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
					requete += t[0] + ")";
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ à vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litres n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						capacity.setText("");
					}
					// Sinon on affiche un message de succès et nous remettons le champ à vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						capacity.setText("");
					}
					// On met à jour les listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
					MAJpanelRangement(listeetagere, listebouteille, listevin);
				}
			}
		});
		
		// On ajoute au panel nord le bouton addBouteille 
		nord.add(addBouteille);
		
		// On ajoute au panelAjoutBouteilles le panel nord au nord 
		panelAjoutBouteilles.add(nord,BorderLayout.NORTH);
		
	}
	
	// Création du panel de modification de bouteille
	public void createPanelModifBouteilles(){
		// On créé le panel panelModifBouteilles qui contiendra tous les éléments de l'onglet ModifBouteilles de la FenetreAdmin
		panelModifBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelModifBouteilles.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton modifierBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour modifier une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On créé la liste des bouteilles.
		creerListeBouteille(bouteilleAModifier);
		
		// On ajoute le champ capacité au panel inner
		inner.add(new JLabel("Choisir la capacité à modifier (en Litre): \n"));
		inner.add(bouteilleAModifier);
		
		// On ajoute le panel inner au panel nord à la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		
		// On créé le panel bottomEtageres qui contiendra les champs à remplir avant de modifier une étagère
		final JPanel bottomBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomBouteilles.setLayout(new GridLayout(3,2));
		
		// On créé les JTextField pour ajouter les données de la bouteille à modifier dedans.
		final JTextField idBouteille = new JTextField();
		final JTextField capacityBottle = new JTextField();
		
		// On ajoute au panel bottomBouteilles le champ identifiant Bouteille
		bottomBouteilles.add(new JLabel("Id Bouteille : "));
		bottomBouteilles.add(idBouteille);
		// On rend ce champ nom éditable
		idBouteille.setEditable(false);
		
		// On ajoute au panel bottomBouteilles le champ capacité
		bottomBouteilles.add(new JLabel("Capacité en Litre(s) : "));
		bottomBouteilles.add(capacityBottle);
		
		// On cache le panel bottomBouteilles
		bottomBouteilles.setVisible(false);

		// On créé un bouton modifierBouteille
		JButton modifierBouteilles = new JButton("Modifier la bouteille sélectionnée");
		
		// On lui attribue un écouteur
		modifierBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de selection
				String requete = "SELECT * FROM bouteille WHERE taille=";
				// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
				String [] t = new String[1];
				t[0] = bouteilleAModifier.getSelectedItem().toString();;
				// FLOTTANT marche seulement avec 1 chiffre après la virgule.
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une bouteille à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0];
					// On récupère le resultat de la requête à l'aide de MyConnexionSelect(String requête)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions dû à  la requête.					
						try {
							// Si la requête a renvoyé des données, nous mettons ces données dans les champs de modification de la bouteille 
							if(statut.first()){
								idBouteille.setText(statut.getString("identifiantBouteille"));
								capacityBottle.setText(statut.getString("taille"));
							}
							// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas été sélectionnée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
		
		// On créé un bouton updateDataBottle
		JButton updateDataBottle = new JButton("Valider les données\n");
		
		// On lui attribue un écouteur
		updateDataBottle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'update
				String requete = "UPDATE bouteille SET ";
				// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
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
					// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
					requete += "taille=\'"+ t[1] + "\'";
					requete += " WHERE identifiantBouteille=" + t[0];
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à réussi, nous affichons un message pour dire que la bouteille a été modifié.
					// Et nous recachons le panel bottomBouteilles.
					if(statut){
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						bottomBouteilles.setVisible(false);
					}
					// Sinon, nous affichons un message d'erreur
					else
					{
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// On effectue une mise à jour des listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
					MAJpanelRangement(listeetagere, listebouteille, listevin);
				}
				
			}
		});
		// On ajoute au panel bottomBouteilles le bouton updateDataBottle
		bottomBouteilles.add(updateDataBottle);
	
		// On ajoute au panelModifBouteilles le panel nord à la position nord et le panel bottomBouteilles à la position sud.
		panelModifBouteilles.add(nord, BorderLayout.NORTH);
		panelModifBouteilles.add(bottomBouteilles, BorderLayout.SOUTH);
		
	}

	// Création du panel de suppression de bouteille
	public void createPanelSupprimerBouteilles(){
		// On créé le panel panelSupprimerBouteilles qui contiendra tous les éléments de l'onglet SupprimerBouteilles de la FenetreAdmin
		panelSupprimerBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelSupprimerBouteilles.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton supprimerBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On créé le panel inner qui contiendra les champs pour supprimer une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On créé la liste des bouteilles à supprimer
		creerListeBouteille(bouteilleASupprimer);
		
		// On ajoute au panel inner la liste des bouteilles à supprimer
		inner.add(new JLabel("Bouteille(s) à supprimer:"));
		inner.add(bouteilleASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On créé un bouton supprimerBouteilles
		JButton supprimerBouteilles = new JButton("Supprimer la bouteille sélectionnée.");
		
		// On lui attribue un écouteur
		supprimerBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de suppression
				String requete = "DELETE FROM bouteille WHERE taille=\'";
				// On prépare la requête pour la selection
				String requeteSelect = "SELECT * FROM bouteille WHERE taille=\'";
				String idBottle = null;
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = bouteilleASupprimer.getSelectedItem().toString();;
				
				// Si aucune bouteille n'est sélectionnée, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une bouteille à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la préparation de la requête de suppression en ajoutant le paramètre nécessaire à la requête
					requete += t[0] + "\'";
					// On fini la préparation de la requête de sélection en ajoutant le paramètre nécessaire à la requête
					requeteSelect += t[0] + "\'";
					// On récupère le resultat de la requête à l'aide de MyConnexionSelect(String requête)
					ResultSet statut = bdd.MyConnexionSelect(requeteSelect);
					// Bloc try catch pour la gestion des excetions dû à  la requête.					
						try {
							// Si la requête a renvoyé des données, nous récupérons l'identifiant de la bouteille à supprimer
							if(statut.first()){
								idBottle = statut.getString("identifiantBouteille");
							}
							// Sinon, nous affichons un message d'erreur et nous mettons la bouteille à supprimer à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Si nous avons changer la valeur de l'idBottle (nous avons sélectionné la bouteille dans la base)
						if(idBottle != null)
						{
							// On fini la préparation de la requête de suppression en ajoutant le paramètre nécessaire à la requête
							requete += " AND identifiantBouteille=" + idBottle;
							// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean status = bdd.MyConnexionInsertDeleteUpdate(requete);
							// Si la requête à réussi, nous affichons un message pour dire que la bouteille a été supprimé.
							// Et nous remettons le champ de suppression de la bouteille à vide.
							if((status)){
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a pas été supprimé dans la base de données", "Information", JOptionPane.INFORMATION_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
							// Sinon on affiche un message d'erreur et nous remettons le champ de suppression de la bouteille à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "La bouteille de " + t[0] + " litre n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						}
						// On met à jour les listes bouteilleAModifier et bouteilleASupprimer
						MAJpanelBouteilles(bouteilleAModifier);
						MAJpanelBouteilles(bouteilleASupprimer);
						MAJpanelRangement(listeetagere, listebouteille, listevin);
				}
			}
		});
		
		// On ajoute au panel nord le bouton supprimerBouteilles au sud
		nord.add(supprimerBouteilles, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerBouteilles le panel nord au centre
		panelSupprimerBouteilles.add(nord, BorderLayout.CENTER);
	}

	// Création du panel de Gestion des vins
	public void createPanelVins(){
		// On créé le panel panelVins qui contiendra tous les éléments de l'onglet GestionVin de la FenetreAdmin
		panelVins = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelVins.setLayout(new BorderLayout());

		// On créé les onglets de tabbedPaneModifBouteilles
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

	// Création du panel d'Ajout de vins
	public void createPanelAjoutVins(){
		// On créé le panel panelAjoutVins qui contiendra tous les éléments de l'onglet AjoutVins de la FenetreAdmin
		panelAjoutVins = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelAjoutVins.setLayout(new BorderLayout());
	
		// On créé le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(6,2));

		// On initialise les champs d'ajout du vin
		regionVins = new JTextField();
		domaineVins = new JTextField();
		châteauVins = new JTextField();
		couleurVins = new JComboBox<String>();
		cepageVins = new JTextField();
		dateVins = new JTextField();

		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		regionVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Region du vin dans le panel inner
		inner.add(new JLabel("Region du vin :"));
		inner.add(regionVins);
				
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		domaineVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Domaine du vin dans le panel inner
		inner.add(new JLabel("Domaine du vin :"));
		inner.add(domaineVins);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		châteauVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Château du vin dans le panel inner
		inner.add(new JLabel("Château du vin :"));
		inner.add(châteauVins);
		
		// On met la taille du JComboBox à 200px de long et 30 px de hauteur
		couleurVins.setPreferredSize(new Dimension(200,30));
		// On rajoute les couleurs de vins dans la JComboBox<String>
		couleurVins.addItem("");
		couleurVins.addItem("rouge");
		couleurVins.addItem("blanc");
		couleurVins.addItem("rosé");
		// On ajoute le champ Couleur du vin dans le panel inner
		inner.add(new JLabel("Couleur du vin :"));
		inner.add(couleurVins);
				
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		cepageVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Cépage du vin dans le panel inner
		inner.add(new JLabel("Cépage du vin :"));
		inner.add(cepageVins);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		dateVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Année du vin dans le panel inner
		inner.add(new JLabel("Année du vin :"));
		inner.add(dateVins);
				
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On créé un bouton ajouterVins
		JButton ajouterVins = new JButton("Ajouter le vins");
		
		// On lui attribut un écouteur
		ajouterVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'ajout
				String requete = "INSERT INTO vins(domaineVin, regionVin, châteauVin, couleur, cepageVin,dateVin) VALUES (";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[6];
				t[0] = domaineVins.getText();
				t[1] = regionVins.getText();
				t[2] = châteauVins.getText();
				t[3] = couleurVins.getSelectedItem().toString();
				t[4] = cepageVins.getText();
				t[5] = dateVins.getText();
				
				// Si un ou plusieurs arguments n'ont pas été renseignés, nous lui affichons un message d'erreur et nous mettons les champs à vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty() || t[5].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					domaineVins.setText("");
					regionVins.setText("");
					châteauVins.setText("");
					couleurVins.setSelectedItem("");;
					cepageVins.setText("");
					dateVins.setText("");
				}
				else{
					// Si la date possède plus de 4 caractères, nous affichons un message d'erreur et nous mettons le champ date à vide.
					if(t[5].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une date cohérante.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						dateVins.setText("");
					}
					else
					{
					
						// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
						requete += "'" + t[0] + "','"+ t[1] + "','" + t[2] + "','" + t[3] + "','" + t[4] + "'," + t[5] + ")";
						// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
						boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
						// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ à vide.
						if(!(statut)){
							JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							domaineVins.setText("");
							regionVins.setText("");
							châteauVins.setText("");
							couleurVins.setSelectedItem("");;
							cepageVins.setText("");
							dateVins.setText("");
						}
						// Sinon on affiche un message de succès et nous remettons le champ à vide.
						else
						{
							JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
							domaineVins.setText("");
							regionVins.setText("");
							châteauVins.setText("");
							couleurVins.setSelectedItem("");;
							cepageVins.setText("");
							dateVins.setText("");
						}
					}
					// On met à jour les listes vinAModifier et vinASupprimer
					MAJpanelVins(vinAModifier);
					MAJpanelVins(vinASupprimer);
					MAJpanelRangement(listeetagere, listebouteille, listevin);
				}
			}
		});
		
		// On ajoute au panel nord le bouton ajouterVins au sud
		nord.add(ajouterVins, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutVins le panel nord au centre
		panelAjoutVins.add(nord,BorderLayout.CENTER);
		
	}
	
	
	// Création du panel de modification de vins
	public void createPanelModifVins(){
		// On créé le panel panelModifVins qui contiendra tous les éléments de l'onglet ModifVins de la FenetreAdmin
		panelModifVins = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelModifVins.setLayout(new BorderLayout());
	
		// On créé le panel nord qui le panel inner et le bouton modifierVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,0));


		// On creer la liste de vin à modifier
		creerListeVin(vinAModifier);
		
		// On ajoute le champs vinAModifier dans le panel inner
		inner.add(new JLabel("Choisir le vin à modifier.\n"));
		inner.add(vinAModifier);
		
		// On ajoute le panel inner dans le panel nord à la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On créé le panel bottomEtageres qui contiendra les champs à remplir avant de modifier une étagère
		final JPanel bottomVins = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomVins.setLayout(new GridLayout(7,2));
				
		// On créé les JTextField pour ajouter les données de la bouteille à modifier dedans.
		final JTextField regionVinsM = new JTextField();
		final JTextField domaineVinsM = new JTextField();
		final JTextField châteauVinsM = new JTextField();
		final JTextField cepageVinsM = new JTextField();
		final JTextField dateVinsM = new JTextField();
		
		// On créé la JComboBox<String> pour la couleur du vin
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
		
		// On ajoute le champs Château au panel bottomVins
		bottomVins.add(new JLabel("Château : "));
		bottomVins.add(châteauVinsM);
		
		// On ajoute le champs Couleur au panel bottomVins
		bottomVins.add(new JLabel("Couleur : "));
		bottomVins.add(couleurVinsM);
		
		// On ajoute le champs Cépage au panel bottomVins
		bottomVins.add(new JLabel("Cépage : "));
		bottomVins.add(cepageVinsM);
		
		// On ajoute le champs Année au panel bottomVins
		bottomVins.add(new JLabel("Année : "));
		bottomVins.add(dateVinsM);
		
		// On cache le panel bottomVins
		bottomVins.setVisible(false);
	
		// On créé le bouton modifierVins
		JButton modifierVins = new JButton("Modifier le vin sélectionné.");
		
		// On lui attribue un écouteur
		modifierVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de selection
				String requete = "SELECT * FROM vins WHERE identifiantVin=";
				// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
				String [] t = new String[1];
				t[0] = vinAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un vin à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0];
					// On récupère le resultat de la requête à l'aide de MyConnexionSelect(String requête)
					ResultSet statut = bdd.MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions dû à  la requête.					
						try {
							// Si la requête a renvoyé des données, nous mettons ces données dans les champs de modification du vin 
							if(statut.first()){
								vinCourant = t[0];
								regionVinsM.setText(statut.getString("regionVin"));
								domaineVinsM.setText(statut.getString("domaineVin"));
								châteauVinsM.setText(statut.getString("châteauVin"));
								couleurVinsM.setSelectedItem(statut.getString("couleur"));
								cepageVinsM.setText(statut.getString("cepageVin"));
								dateVinsM.setText(statut.getString("dateVin"));
								
							}
							// Sinon, nous affichons un message d'erreur et nous mettons le vinAModifier à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " n'a pas été sélectionner dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								vinAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Nous affichons le bottomVins
						bottomVins.setVisible(true);;
				}
				// On met à jour les listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				
			}
		});
		
		// On ajoute au panel nord le bouton modifierVins au sud
		nord.add(modifierVins, BorderLayout.SOUTH);
		
		// On créé un bouton updateDataVins
		JButton updateDataVins = new JButton("Valider les données.\n");
		
		// On lui attribue un écouteur
		updateDataVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'update
				String requete = "UPDATE cave.vins SET ";
				// On créé un tableau de String pour récupérer le champ pour la modification de l'étagère
				String [] t = new String[6];
				t[0] = regionVinsM.getText();
				t[1] = domaineVinsM.getText();
				t[2] = châteauVinsM.getText();
				t[3] = couleurVinsM.getSelectedItem().toString();
				t[4] = cepageVinsM.getText();
				t[5] = dateVinsM.getText();
				
				// Si le champ de positionEtagere est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty() || t[5].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// Si l'année renseignée est différente de 4 caractères on affiche un message d'erreur
					if(t[5].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une année valide.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						// On fini la préparation de la requête en ajoutant les paramètres nécessaires à la requête
						requete += "regionVin='"+ t[0] + "', domaineVin='" + t[1] + "', châteauVin='" + t[2] + "', couleur='" + t[3] + "', cepageVin='" + t[4] + "', dateVin=" + t[5];
						requete += " WHERE identifiantVin=" + vinCourant;
						// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
						boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
						// Si la requête à réussi, nous affichons un message pour dire que la vin a été modifié.
						// Et nous recachons le panel bottomVins.
						if((statut)){
							JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
							bottomVins.setVisible(false);
						}
						// Sinon on affiche un message d'erreur.
						else
						{
							JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					}
					// On met à jour les listes vinAModifier et vinASupprimer
					MAJpanelVins(vinAModifier);
					MAJpanelVins(vinASupprimer);
					MAJpanelRangement(listeetagere, listebouteille, listevin);
				}
				
			}
		});
		
		// On ajoute au panel bottomVins le bouton updateDataVins
		bottomVins.add(updateDataVins);
		
		// On ajoute au panelModifVins le panel nord à la position nord et le panel bottomVins à la position sud.
		panelModifVins.add(nord, BorderLayout.NORTH);
		panelModifVins.add(bottomVins, BorderLayout.SOUTH);
		
	}

	// Création du panel de suppression de vin
	public void createPanelSupprimerVins(){
		// On créé le panel panelSupprimerVins qui contiendra tous les éléments de l'onglet SupprimerVins de la FenetreAdmin
		panelSupprimerVins = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelSupprimerVins.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton supprimerVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par région
		nord.setLayout(new BorderLayout());
		
		// On créé le panel inner qui contiendra les champs pour supprimer un vin
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On créé la liste des vins à supprimer
		creerListeVin(vinASupprimer);
		
		// On ajoute au panel inner la liste des vins à supprimer
		inner.add(new JLabel("Vins à supprimer:"));
		inner.add(vinASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On créé un bouton supprimerVins
		JButton supprimerVins = new JButton("Supprimer le vin sélectionné.");
		
		// On lui attribue un écouteur
		supprimerVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête de suppression
				String requete = "DELETE FROM vins WHERE identifiantVin=";
				// On créé un tableau de String pour récupérer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = vinASupprimer.getSelectedItem().toString();;
				
				// Si aucune étagère n'est sélectionnée, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un vin à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la préparation de la requête en ajoutant le paramètre nécessaire à la requête
					requete += t[0];
					// Nous effectuons la requête à l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = bdd.MyConnexionInsertDeleteUpdate(requete);
					// Si la requête à échouer, nous affichons un message d'erreur et nous remettons le champ de suppression de vin à vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + "n'a pas été supprimée de votre base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}
					// Sinon, on met un message de succès et on remet le champ de suppression de vin à vide
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " a été supprimée de votre base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}	
				}
				// Mise à jour des listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerVins au sud 
		nord.add(supprimerVins, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerVins le panel nord au centre
		panelSupprimerVins.add(nord, BorderLayout.CENTER);
	}

	// Création du panel de Rangement de vin/bouteille sur étagère
	public void createPanelRangement(){
		// On créé le panel panelRangement qui contiendra tous les éléments de l'onglet Rangement de la FenetreAdmin
		panelRangement = new JPanel();
		// On lui dit que l'affichage des champs se par région
		panelRangement.setLayout(new BorderLayout());

		// On créé le panel nord qui le panel inner et le bouton ajouterRangement
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		nord.setLayout(new GridLayout(2,0));
		
		// On créé le panel inner qui contiendra les champs pour ajouter un rangement de vin/bouteille dans la cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(4,2));
		
		// On créé la liste des étagères disponibles
		creerListeEtagere(listeetagere);
		
		// On ajoute le champ étagère au panel inner
		inner.add(new JLabel("Veuillez selectionner une étagère"));
		inner.add(listeetagere);
		
		// On créé la liste des bouteilles disponibles
		creerListeBouteilleRangement(listebouteille);
		
		// On ajoute le champ bouteille au panel inner
		inner.add(new JLabel("Veuillez selectionner une bouteille"));
		inner.add(listebouteille);
		
		
		// On créé la liste des vins disponibles
		creerListeVin(listevin);
		
		// On ajoute le champ vin au panel inner
		inner.add(new JLabel("Veuillez selectionner un vin"));
		inner.add(listevin);
		
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On créé un bouton ajouterRangement
		JButton ajouterRangement = new JButton("Ranger le vins");
		
		// On lui attribue un écouteur
		ajouterRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On prépare la requête d'insertion
				String requeteInsert = "INSERT INTO bouteille_has_vins(Bouteille_identifiantBouteille, Vins_identifiantVin, Etagère_identifiantEtagère) VALUES (";
				// On prépare la requête de sélection de bouteilles
				String requeteSelectBottle = "SELECT * FROM bouteille WHERE taille=";
				String idBottle = null;
				// On prépare la requête de sélection d'étagères
				String requeteSelectEtagere = "SELECT * FROM etagère WHERE positionEtagère='";
				String idEtagere = null;
				
				// On créé un tableau de String pour récupérer les champs pour le rangement du vin/bouteille
				String [] t = new String[3];
				t[0] = listeetagere.getSelectedItem().toString();
				t[1] = listebouteille.getSelectedItem().toString();
				t[2] = listevin.getSelectedItem().toString();
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					listeetagere.setSelectedItem("");
					listebouteille.setSelectedItem("");
					listevin.setSelectedItem("");
				}
				else{
					// On fini de préparer les requêtes de sélection avec les champs récupérés.
					requeteSelectBottle += t[1] + "";
					requeteSelectEtagere += t[0] + "\'";
					// On effectue les deux select
					ResultSet statutBottle = bdd.MyConnexionSelect(requeteSelectBottle);
					ResultSet statutEtagere = bdd.MyConnexionSelect(requeteSelectEtagere);
						try {
							// Si la sélection des bouteilles à donner un résultat on récupère l'identifiant du premier résultat
							if(statutBottle.first()){
								idBottle = statutBottle.getString("identifiantBouteille");
							}
							// Sinon, on affiche une erreur et on dit que l'on a pas sélectionné de bouteille. On met le champ à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[1] + " litre n'a pas été selectionné dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								listebouteille.setSelectedItem("");
							}
							// Si la sélection des étagères à donner un résultat, on récupère l'identifiant du premier résultat.
							if(statutEtagere.first()){
								idEtagere = statutEtagere.getString("identifiantEtagère");
							}
							// Sinon, message d'erreur et on met le champ à vide
							else
							{
								JOptionPane.showMessageDialog(null, "L'étagère à la position " + t[0] + " n'a pas été selectionné dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
							}
							
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Si on a récupéré une bouteille et une étagère
						if(idBottle != null && idEtagere != null)
						{
							// On fini de préparer la requête d'insert avec tous les champs nécessaires à la requête.
							requeteInsert += idBottle + "," + t[2] + "," + idEtagere + ")";
							// On effectue le insert
							boolean status = bdd.MyConnexionInsertDeleteUpdate(requeteInsert);
							// Si la requête n'a pas fonctionné, on aura une erreur. On met tous les champs à vide
							if(!(status)){
								JOptionPane.showMessageDialog(null, "Il y a eu une erreur", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
							// Sinon, on a un message de succès et on met tous les champs à vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin à été rangé", "Information", JOptionPane.INFORMATION_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
						}
				}
				// On met à jour les listes contenu dans ce panel : listeetagere, listebouteille et listevin
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton ajouterRangement
		nord.add(ajouterRangement, BorderLayout.SOUTH);
		
		// On ajoute au panelRangement le panel nord au centre
		panelRangement.add(nord,BorderLayout.CENTER);
	}
	
	// Fonction qui nous permet d'actualiser l'affichage du résultat de la requête dans onglet Recherche
	public void rafraichirDataRecherche(JPanel p, JTable o){
		// On enlève tous les éléments du panel bottom
		p.removeAll();
		if(o == null){
			p.setVisible(false);
		}
		else
		{
			// On lui ajoute au nord les entêtes du tableau
			p.add(o.getTableHeader(), BorderLayout.NORTH);
			// On lui ajoute au centre les données du résultat de la requête
			p.add(o, BorderLayout.CENTER);
				
		}
	}
	
	// Fonction qui permet de rafraichir les listes de caves
	public void MAJpanelCave(JComboBox<String> p){
		p.removeAllItems();
		creerListeCave(p);
	}
	
	// Fonction qui permet de récupérer la liste des cave à vin de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
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
	
	// Fonction qui permet de récupérer la liste des vins dans la base et de les organiser dans une JComboBox<String>
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
	
	// Fonction qui permet de récupérer la liste des bouteilles dans la base et de les organiser dans une JComboBox<String>
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
	
	// Fonction qui permet de rafraichir les listes d"étagères
	public void MAJpanelEtageres(JComboBox<String> p){
		p.removeAllItems();
		creerListeEtagere(p);
	}
	
	// Fonction qui permet de récupérer la liste des étagères de la cave courante et de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
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

	// Fonction qui permet de rafraichir les listes de contenu dans le panel Rangement
	public void MAJpanelRangement(JComboBox<String> le, JComboBox<String> lb, JComboBox<String> lv){
		le.removeAllItems();
		creerListeEtagere(le);
		lb.removeAllItems();
		creerListeBouteilleRangement(lb);
		lv.removeAllItems();
		creerListeVin(lv);
		
	}
	
	// Fonction qui permet de récupérer la liste des bouteilles qui ne sont pas déjà pleine de vin dans la base et de les organiser dans une JComboBox<String> (pour onglet Rangement)
	public void creerListeBouteilleRangement(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		// Requete pour ne selectionner que les bouteilles non utilisées.  
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

