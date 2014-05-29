// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

//Import pour les �couteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requ�te faites � la base de donn�es et pour la gestion des Exceptions
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



//Import pour les �l�ments du JFrame
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



//Import pour le driver jdbc pour la connexion � la BDD
import com.mysql.jdbc.Statement;



public class FenetreAdmin extends JFrame{

	private static final long serialVersionUID = -122199509843219096L;
	// Champs pour la connexion � la base de donn�es avec le driver jdbc.
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
	    
	// Champs nec�ssaire pour garder l'identifiant de l'administrateur, le nom de la cave courante et le vin courant (pour les modifications de ces deux derniers)
	private String idAdmin;
	private String caveAVinCourante;
	private String vinCourant;
	
	// La fen�tre principale contient 5 onglets principals
	private		JTabbedPane tabbedPane = new JTabbedPane();;
	private		JPanel		panelRecherche;
	private		JPanel		panelAjoutCave;
	private		JPanel		panelModificationCave;
	private		JPanel		panelSuppressionCave;
	private		JPanel		panelModificationMDP;
	
	// Champs n�cessaire pour la recherche de vins (Onglet Recherche)
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Ann�e");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	// Champ pour le r�sultat de la recherche (Onglet Recherche)
	JTable tableau;
	
	// Champs n�cessaire pour l'ajout d'une cave (Onglet AjoutCave)
	private JTextField nomCaveAAjouter = new JTextField();
	private JTextArea commentaireCave = new JTextArea();
	private JButton ajouterCave = new JButton("Ajouter Cave");
	
	// Champs n�cessaire pour la modification d'une cave (Onglet ModificationCave)
	private JComboBox<String> caveAModifier = new JComboBox<String>();
	private JButton modifierCave = new JButton("Modifier Cave");
	
	// Dans le panel de l'onglet de modification de cave, nous avons 4 onglets
	private		JTabbedPane tabbedPaneModif;
	private		JPanel		panelEtageres;
	private		JPanel		panelBouteilles;
	private		JPanel		panelVins;
	private		JPanel		panelRangement;
	
	// Dans le panel de l'onglet de modification d'�tag�res, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifEtageres;
	private		JPanel		panelAjoutEtageres;
	private		JPanel		panelModifEtageres;
	private		JPanel		panelSupprimerEtageres;
	
	// Champs n�cessaire pour la modification d'une �tag�re (Onglet ModifEtag�re)
	private JComboBox<String> etagereAModifier = new JComboBox<String>();
	
	// Champs n�cessaire pour la suppression d'une �tag�re (Onglet SupprimerEtag�re)
	private JComboBox<String> etagereASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de bouteilles, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifBouteilles;
	private		JPanel		panelAjoutBouteilles;
	private		JPanel		panelModifBouteilles;
	private		JPanel		panelSupprimerBouteilles;
		
	// Champs n�cessaire pour la modification d'une bouteille (Onglet ModifBouteilles)
	private JComboBox<String> bouteilleAModifier = new JComboBox<String>();
	
	// Champs n�cessaire pour la suppression d'une bouteille (Onglet SupprimerBouteilles)
	private JComboBox<String> bouteilleASupprimer = new JComboBox<String>();

	// Dans le panel de l'onglet de modification de vins, nous avons 3 onglets 
	private		JTabbedPane tabbedPaneModifVins;
	private		JPanel		panelAjoutVins;
	private		JPanel		panelModifVins;
	private		JPanel		panelSupprimerVins;
	
					
	// Champs n�cessaire pour l'ajout d'un vin (Onglet AjoutVin)
	private		JTextField regionVins;
	private		JTextField domaineVins;
	private		JTextField ch�teauVins;
	private		JComboBox<String> couleurVins;
	private		JTextField cepageVins;
	private		JTextField dateVins;
	
	// Champs n�cessaire pour la modification d'un vin (Onglet ModifVins)
	private JComboBox<String> vinAModifier = new JComboBox<String>();
	
	// Champs n�cessaire pour la suppression d'un vin (Onglet SupprimerVins)
	private JComboBox<String> vinASupprimer = new JComboBox<String>();
	
	// Champs n�cessaire pour le rangement des vins/bouteilles sur les �tag�res (Onglet Rangement)
	private JComboBox<String> listeetagere = new JComboBox<String>();
	private JComboBox<String> listebouteille = new JComboBox<String>();
	private JComboBox<String> listevin = new JComboBox<String>();
		
	// Champs n�cessaire pour la suppression d'une cave (Onglet SuppressionCave)
	private JComboBox<String> caveASupprimer = new JComboBox<String>();
	private JButton supprimerCave = new JButton("Supprimer Cave");
	
	// Champs n�cessaire pour la modification du mot de passe d'une cave (Onglet ModificationMDP)
	private JTextField oldMDP;
	private JTextField newMDP;
	private JTextField confirmationMDP;
	
	// Constructeur avec comme param�tre l'identifiant du marchand de vin de la fen�tre de Admin
   	public FenetreAdmin(String id) {
   		// On appel le constructeur de JFrame avec un param�tre qui est le nom de la fen�tre
   		super("La cave � vin - Gestion administrateur");
   		// On enregistre dans la variable globale l'identifiant du marchand de vin
		this.idAdmin = id;
		// Ensuite on initialise cette fen�tre
		init();
	}
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// On met la dimension du panel principal �la taille de l'�cran
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		

		// On cr�� le panel topPanel qui contiendra tous les �l�ments de la FenetreAdmin
		JPanel topPanel = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		topPanel.setLayout(new BorderLayout());
					
		// On ajoute le topPanel � la fen�tre
		getContentPane().add(topPanel);
		
		// On cr�� les diff�rents onglets principaux
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
	
	// Cr�ation du panel de Recherche
	public void createRecherche()
	{
		// On cr�� le panel panelRecherche qui contiendra tous les �l�ments de l'onglet Recherche de la FenetreAdmin
		panelRecherche = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelRecherche.setLayout(new BorderLayout());
	
		// On instancie les champs de notre fenetre (JTextField pour lla recherche par ann�e et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On cr�� le panel top qui contiendra le bouton de recherche et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		top.setLayout(new BorderLayout());
		
		// On cr�� le panel toptop qui contiendra les champs de recherche de vins
		JPanel innerTop = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerTop.setLayout(new GridLayout(3,3));
		
		// On met la dimension du JTextField associ� � recherche par ann�e � 200px de large et 30px de hauteur
		rechercherannee.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par ann�e
		innerTop.add(new JLabel("Ann�e � rechercher :"));
		innerTop.add(rechercherannee);
		innerTop.add(checkannee);
		
		// On met la dimension du JTextField associ� � recherche par nom � 200px de large et 30px de hauteur
		recherchernom.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel innerTop le champ de recherche par nom
		innerTop.add(new JLabel("Nom � rechercher :"));
		innerTop.add(recherchernom);
		innerTop.add(checknom);
		
		// On met la dimension du JComboxBox<String> associ� � recherche par couleur � 200px de large et 30px de hauteur
		recherchercouleur.setPreferredSize(new Dimension(200,30));
		// On ajoute au JComboBox<String> les diff�rentes couleur du vin
		recherchercouleur.addItem("");
		recherchercouleur.addItem("blanc");
		recherchercouleur.addItem("rose");
		recherchercouleur.addItem("rouge");
		// On ajoute au panel innerTop le champ de recherche par couleur
		innerTop.add(new JLabel("Couleur � rechercher :"));
		innerTop.add(recherchercouleur);
		innerTop.add(checkcouleur);
		
		// On ajoute au panel top le panel innerTop au centre
		top.add(innerTop, BorderLayout.CENTER);
		
		// On cr�� le panel bottom qui contiendra les r�sultats de la recherche. on lui donne le mot cl� final pour pouvoir l'utiliser dans l'�couteur du bouton Rechercher
		final JPanel bottom = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		bottom.setLayout(new BorderLayout()); 
		// On chache ce panel
		bottom.setVisible(false);
		
		// On cree un bouton Rechercher
		JButton searchButton = new JButton("Rechercher");
		
		// On lui attribue un �couteur (c'est ici que l'on g�rera si la recherche se fait par nom,couleur, ou ann�e.)
		
		// On lui attribue un �couteur
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cr�� un tableau de String pour r�cup�rer les �l�ments s�lectionn�s
				String [] t = new String[3];
				// On pr�pare notre requ�te de recherche
				String requete = "SELECT * FROM `vins` WHERE";
				
				// Si aucune case n'est coch�e c'est que l'utilisateur n'a pas s�lectionn� d'option de recherche donc on lui affiche un message d'erreur
				if(!(checkannee.isSelected()) && !(checknom.isSelected()) && !(checkcouleur.isSelected())){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une option de recherche.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
				// Si l'option recherche par ann�e est coch�e, on regarde si le champs de recherche n'est pas vide.
				if(checkannee.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(rechercherannee.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par ann�e mais vous n'avez pas renseigner de valeur\nVeuillez en renseigner une", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon
					else
					{
						// Si le champs ne contient pas exactement 4 caract�res, on affiche une erreur
						if(rechercherannee.getText().length() != 4){
							JOptionPane.showMessageDialog(null, "Veuillez en renseigner une ann�e valide", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						// Sinon on r�cup�re le contenu du champs rechercherann�e
						else
						{
							t[0] = rechercherannee.getText();
						}
					}
				}
				
				// Si l'option recherche par nom est coch�e, on regarde si le champs de recherche n'est pas vide.
				if(checknom.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchernom.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on r�cup�re le contenu du champs recherchernom
					else
					{
						t[1] = recherchernom.getText();
					}
				}
				
				// Si l'option recherche par couleur est coch�e, on regarde si le champs de recherche n'est pas vide.
				if(checkcouleur.isSelected()){
					// S'il l'est on affiche un message d'erreur
					if(recherchercouleur.getSelectedItem().toString().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// Sinon on r�cup�re le contenu du champs recherchercouleur
					else
					{
						t[2] = recherchercouleur.getSelectedItem().toString();
					}
				}
				
				// Si les trois options de recherche sont coch�es, on pr�pare la requ�te avec les trois param�tres.
				// Si deux des trois options de recherche sont coch�es, on pr�pare la requ�te avec les deux param�tres coch�s.
				// Si l'une des trois options de recherche est coch�e, pr�pare la requ�te avec le param�tre coch�.
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
				
				// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionRecherche(String requ�te)
				ResultSet resultRequete = MyConnexionSelect(requete);
				// Bloc try catch pour la gestion des excetions d� � la requ�te.
				try {
					// On laisse le panel bottom cach�
					bottom.setVisible(false);
					// Si le r�sultat de la requ�te contient une ou des donn�es
					if((resultRequete.isBeforeFirst())){
						int i = 0;
						// On cr�� un tableau donnees qui r�cup�rera toutes les donn�es du r�sultat de la requ�te.
						// Pour cela on cr�� un tableau de String � deux dimensions de 25 lignes et de 6 colonnes.
						String[][] 	donnees = new String[25][6];
						// Tant qu'il y a une donn�e, on enregistre ses champs.
						while(resultRequete.next()) {
							donnees[i][0] = resultRequete.getString("regionVin");
							donnees[i][1] = resultRequete.getString("domaineVin");
							donnees[i][2] = resultRequete.getString("ch�teauVin");
							donnees[i][3] = resultRequete.getString("couleur");
							donnees[i][4] = resultRequete.getString("cepageVin");
							donnees[i][5] = resultRequete.getString("dateVin") + "\n";
							i++;
						}
						// On cr�� un tableau entetes qui contient toutes les ent�tes du r�sultat de la requ�te.
						String[] entetes = {"Region", "Domaine", "Ch�teau", "Couleur", "C�page", "Ann�e"};
						// On cr�� un nouveau JTable avec les donn�es r�cup�r�es.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant les r�sultats de la recherche.
						tableau.setEnabled(false);;
					}
					else
					{
						// On cr�� le tableau de string � deux dimensions qui contient Nous n'avons trouv� aucun vin correspondant � votre recherche
						String[][] 	donnees = new String[1][1];
						donnees[0][0] = "Nous n'avons trouv� aucun vin correspondant � votre recherche";
						// On cr�� un tableau entetes qui contient NoResultFound.
						String[] entetes = {"NoResultFound"};
						// On cr�� un nouveau JTable avec ces donn�es.
						tableau = new JTable(donnees,entetes);
						// On rend le contenu non editable du tableau contenant ces donn�es.
						tableau.setEnabled(false);;
					}
					// On fait appel � rafraichirData(JPanel bottom, JTable tableau) pour rafra�chir les donn�es d'une recherche � l'autre.
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

	// Cr�ation du panel d'Ajout d'une cave
	public void createAjout()
	{
		// On cr�� le panel panelAjoutCave qui contiendra tous les �l�ments de l'onglet AjoutCave de la FenetreAdmin
		panelAjoutCave = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelAjoutCave.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(2,2));
		
		// On met la dimension du JTextField associ� au nom de la cave � 200px de large et 30px de hauteur
		nomCaveAAjouter.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Nom de la cave :"));
		inner.add(nomCaveAAjouter);
				
		// On met la dimension du JTextField associ� au commentaire de la cave � 200px de large et 30px de hauteur
		commentaireCave.setPreferredSize(new Dimension(200,30));
		// On ajoute au panel inner le champ de nom de la cave
		inner.add(new JLabel("Commentaire � faire :"));
		inner.add(commentaireCave);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un �couteur au bouton aujouterCave
		ajouterCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'ajout
				String requete = "INSERT INTO caveavin(nomCave, commentaire, Utilisateur_identifiantUtilisateur) VALUES (";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[2];
				t[0] = nomCaveAAjouter.getText();
				t[1] = commentaireCave.getText();
				
				// Si au moins un argument n'est pas renseign� par l'utilisateur, nous lui affichons un message d'erreur.
				if(t[0].isEmpty() || t[1].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les trois champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
					requete += "'" + t[0]+ "','" + t[1] + "'," + idAdmin + ")";
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons les champs d'ajout de la cave � vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " n'a pas �t� ajout�e dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// Sinon on affiche un message de succ�s et nous remettons les champs d'ajout de la cave � vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a �t� ajout�e dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					// On fait appel � MAJpanelModificationCave(JComboBox<String> caveAModifier) pour rafra�chir les donn�es de la liste de caveAModifier.
					MAJpanelCave(caveAModifier);
					// On fait appel � MAJpanelSuppressionCave(JComboBox<String> caveASupprimer) pour rafra�chir les donn�es de la liste de caveASupprimer.
					MAJpanelCave(caveASupprimer);
				}
			}
		});
		
		// On au panel nord le bouton ajouterCave au sud
		nord.add(ajouterCave, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutCave le panel nord au centre
		panelAjoutCave.add(nord, BorderLayout.CENTER);
	}

	// Cr�ation du panel de modification d'une cave
	public void createModification()
	{
		// On cr�� le panel panelModificationCave qui contiendra tous les �l�ments de l'onglet ModifCave de la FenetreAdmin
		panelModificationCave = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelModificationCave.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton modifierCave
		final JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());

		// On cr�� le panel inner qui contiendra les champs pour modifier une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cr�� la liste de cave � modifier
		creerListeCave(caveAModifier);

		// On ajoute au panel inner la liste de cave � modifier
		inner.add(new JLabel("Nom de la cave � modifier:"));
		inner.add(caveAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un �couteur au bouton modifierCave
		modifierCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cr�� un tableau de String pour r�cup�rer les champs pour la modification de la cave
				String [] t = new String[1];
				t[0] = caveAModifier.getSelectedItem().toString();;
				
				// Si aucune cave n'est s�lectionn�e, on affiche un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une cave � modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On met dans une variable globale le nom de la cave s�lectionn�e
					caveAVinCourante = t[0];
					// On cache le panel nord.
					nord.setVisible(false);
					// On affiche la table d'onglet tabbedPaneModif qui permet la gestion de la cave � vin.
					tabbedPaneModif.setVisible(true);
					// On ajoute au panelModificationCave la barre d'onglet tabbedPaneModif au centre
					panelModificationCave.add(tabbedPaneModif, BorderLayout.CENTER);
					
				}
				// On met � jour les listes de vins, de bouteilles et d'�tag�res contenu dans les onglets de tabbedPaneModif
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
		
		// On cr�� les onglets de tabbedPaneModif
		createPanelEtageres();
		createPanelBouteilles();
		createPanelVins();
		createPanelRangement();
		
		// On les ajoute dans la barre d'onglet tabbedPaneModif qui est la barre d'onglet pour la modification d'une cave.
		tabbedPaneModif = new JTabbedPane();
		tabbedPaneModif.addTab("Gestion �tag�res", panelEtageres);
		tabbedPaneModif.addTab("Gestion bouteilles", panelBouteilles);
		tabbedPaneModif.addTab("Gestion vins", panelVins);
		tabbedPaneModif.addTab("Rangement des vins", panelRangement);
		// On cache la tabbedPaneModif
		tabbedPaneModif.setVisible(false);
		
	}
	
	// Cr�ation du panel de suppression d'une cave
	public void createSuppression()
	{
		// On cr�� le panel panelSuppressionCave qui contiendra tous les �l�ments de l'onglet SuppressionCave de la FenetreAdmin
		panelSuppressionCave = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelSuppressionCave.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cr�� la liste de cave � supprimer
		creerListeCave(caveASupprimer);
		
		// On ajoute au panel inner la liste de cave � supprimer
		inner.add(new JLabel("Nom de la cave � supprimer:"));
		inner.add(caveASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On attribue un �couteur au bouton supprimerCave
		supprimerCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de suppression
				String requete = "DELETE FROM caveavin WHERE nomCave='";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = caveASupprimer.getSelectedItem().toString();;
				
				// Si aucune cave n'est s�lectionn�e, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une cave � supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else
				{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0] + "'";
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ de suppression de la cave � vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + "n'a pas �t� supprim�e de votre base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succ�s et nous remettons le champ de suppression de la cave � vide.
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a �t� supprim�e de votre base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					// On met � jour les listes de caveAModifier et de caveASupprimer
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
	
	// Cr�ation du panel de modification du motdepasse
	public void createMDP(){
		// On cr�� le panel panelModificationMDP qui contiendra tous les �l�ments de l'onglet ModificationMDP de la FenetreAdmin
		panelModificationMDP = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelModificationMDP.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton ajouterCave
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour modifier son mot de passe dans la base de donn�es
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
		
		// On cr�� un bouton Changer le mot de passe
		JButton changerMDP = new JButton("Changer le mot de passe.\n");
		
		// On lui attribut un �couteur
		changerMDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te pour r�cup�rer l'ancien mot de passe de l'utilisateur
				String select = "SELECT * FROM cave.utilisateur WHERE identifiantUtilisateur=";
				// On pr�pare la requ�te mettre � jour le mot de passe de l'utilisateur avec le nouveau mot de passe
				String update = "UPDATE cave.utilisateur SET motDePasse = MD5('";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[3];
				t[0] = oldMDP.getText();
				t[1] = newMDP.getText();
				t[2] = confirmationMDP.getText();
				
				// Si au moins un argument n'est pas renseign� par l'utilisateur, nous lui affichons un message d'erreur et on met tous les champs � vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					oldMDP.setText("");
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Si le nouveau mot de passe et la confirmation de celui-ci ne sont pas �gals.
				// On affiche un message d'erreur et nous mettons tous les champs � vide.
				if(!t[1].equals(t[2])){
					JOptionPane.showMessageDialog(null, "Le nouveau mot de passe et la confirmation ne sont pas identique.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				// Sinon
				else
				{
					// On fini la pr�paration de la requ�te de r�cup�ration de l'ancien mot de passe en ajoutant le param�tre n�cessaire � la requ�te.
					select += idAdmin + " AND motDePasse=MD5('" + t[0] + "')";
					// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionSelect(String requ�te)
					ResultSet changeMDP = MyConnexionSelect(select);
					try {
						// Si le r�sultat de la requ�te select contient une ou des donn�es
						if(changeMDP.next()){
							// On fini la pr�paration de la requ�te de mise � jour du en ajoutant les param�tres n�cessaires � la requ�te.
							update += t[2] + "') WHERE utilisateur.identifiantUtilisateur = " + idAdmin;
							// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = MyConnexionInsertDeleteUpdate(update);
							// Si la requ�te � r�ussi, nous affichons un message pour dire que le mot de passe a �t� chang�.
							// Et nous remettons tous les champs � vide.
							if(statut){
								JOptionPane.showMessageDialog(null, "Votre mot de passe a �t� chang�.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
							// Sinon, nous affichons un message d'erreur pour dire que le mot de passe n'a pas �t� chang�.
							// Et nous remettons tous les champs � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Votre mot de passe n'a pas �t� chang�.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
								
						}
						// Si le r�sultat de la requ�te select ne contient pas de donn�es, on affiche un message d'erreur et nous mettons tous les champs � vide.
						else
						{
							JOptionPane.showMessageDialog(null, "Votre mot de passe est erron�.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
	
	// Cr�ation du panel de Gestion des �tag�res
	public void createPanelEtageres(){
		// On cr�� le panel panelEtageres qui contiendra tous les �l�ments de l'onglet GestionEtag�re de la FenetreAdmin
		panelEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelEtageres.setLayout(new BorderLayout());

		// On cr�� les onglets de tabbedPaneModif
		createPanelAjoutEtageres();
		createPanelModifEtageres();
		createPanelSupprimerEtageres();
				
		// On les ajoute dans la barre d'onglet tabbedPaneModifEtageres qui est la barre d'onglet pour la modification d'une �tag�re.
		tabbedPaneModifEtageres = new JTabbedPane();
		tabbedPaneModifEtageres.addTab("Ajouter une �tag�re", panelAjoutEtageres);
		tabbedPaneModifEtageres.addTab("Modifier une �tag�re", panelModifEtageres);
		tabbedPaneModifEtageres.addTab("Supprimer une �tag�re", panelSupprimerEtageres);
		
		// On ajoute au panelEtageres la barre d'onglet tabbedPaneModifEtageres au centre 
		panelEtageres.add(tabbedPaneModifEtageres, BorderLayout.CENTER);		
		
	}
	
	// Cr�ation du panel d'Ajout d'�tag�re
	public void createPanelAjoutEtageres(){
		
		// On cr�� le panel panelAjoutEtageres qui contiendra tous les �l�ments de l'onglet AjoutEtageres de la FenetreAdmin
		panelAjoutEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelAjoutEtageres.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new GridLayout(2,0));
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter une �tag�re
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
		
		
		// Cr�ation et ajout du champs position dans le panel innerNord
		final JTextField position = new JTextField();
		innerNord.add(new JLabel("Position de l'�tag�re dans la cave (sans ' ou \" ): "));
		innerNord.add(position);
		
		// On ajoute au panel nord le panel innerNord
		nord.add(innerNord);
		
		// On cr�� un bouton addEtagere
		JButton addEtagere = new JButton("Ajouter une �tag�re");
		
		// On lui attribut un �couteur
		addEtagere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'ajout
				String requete = "INSERT INTO etag�re(positionEtag�re, CaveAVin_nomCave, CaveAVin_Utilisateur_identifiantUtilisateur) VALUES ('";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = position.getText();
				
				// Si l'utilisateur n'a pas donn� de position, nous lui affichons un message d'erreur et nous mettons le champ � vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					position.setText("");
				}
				else{
					// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
					requete += t[0] + "','"+ caveAVinCourante + "'," + idAdmin + ")";
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ � vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une �tag�re � la position " + t[0] + " n'a pas �t� ajout�e dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						position.setText("");
					}
					// Sinon on affiche un message de succ�s et nous remettons le champ � vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une �tag�re � la position " + t[0] + " a �t� ajout�e dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						position.setText("");
					}
				}
				// On met � jour les listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
			}
		});
		
		// On ajoute au panel nord le bouton addEtagere
		nord.add(addEtagere);
		
		// On ajoute au panelAjoutEtageres le panel nord au nord
		panelAjoutEtageres.add(nord,BorderLayout.NORTH);
	}

	// Cr�ation du panel de modification d'�tag�re
	public void createPanelModifEtageres(){

		// On cr�� le panel panelModifEtageres qui contiendra tous les �l�ments de l'onglet ModifEtageres de la FenetreAdmin
		panelModifEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelModifEtageres.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel innerNord et le bouton addEtagere
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour modifier une �tag�re
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On cr�� la liste des �tag�res.
		creerListeEtagere(etagereAModifier);
		
		// On ajoute le champs etagereAModifier au panel inner
		inner.add(new JLabel("La position � modifier: \n"));
		inner.add(etagereAModifier);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cr�� le panel bottomEtageres qui contiendra les champs � remplir avant de modifier une �tag�re
		final JPanel bottomEtageres = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomEtageres.setLayout(new GridLayout(5,2));
		
		// On cr�� les JTextField pour ajouter les donn�es de l'�tag�re � modifier dedans.
		final JTextField idEtagere = new JTextField();
		final JTextField positionEtagere = new JTextField();
		final JTextField idCave = new JTextField();
		final JTextField idUser = new JTextField();
		
		// On ajoute au panel bottomEtageres le champ identifiant Etag�re
		bottomEtageres.add(new JLabel("Identifiant �tag�re : "));
		bottomEtageres.add(idEtagere);
		// On rend ce champ non modifiable
		idEtagere.setEditable(false);
		
		// On ajoute au panel bottomEtageres le champ position Etag�re
		bottomEtageres.add(new JLabel("Position de l'�tag�re (sans ' ou \" ) : "));
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
		
		// On cr�� un nouveau bouton modifierEtageres
		JButton modifierEtageres = new JButton("Modifier l'�tag�re s�lectionn�e");
		
		// On lui attribue un �couteur
		modifierEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de selection
				String requete = "SELECT * FROM etag�re WHERE positionEtag�re='";
				// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
				String [] t = new String[1];
				t[0] = etagereAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une �tag�re � modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0] + "'";
					// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionSelect(String requ�te)
					ResultSet statut = MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions d� � la requ�te.
					try {
						// Si la requ�te a renvoy� des donn�es, nous mettons ces donn�es dans les champs de modification de l'�tag�re 
						if(statut.first()){
							idEtagere.setText(statut.getString("identifiantEtag�re"));
							positionEtagere.setText(statut.getString("positionEtag�re"));
							idCave.setText(statut.getString("CaveAVin_nomCave"));
							idUser.setText(statut.getString("CaveAVin_Utilisateur_identifiantUtilisateur"));
						}
						// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier � vide.
						else
						{
							JOptionPane.showMessageDialog(null, "L'�tag�re � la position " + t[0] + " n'a pas �t� s�lectionner dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
		
		// Nous cr��ons un bouton updateDataEtagere.
		JButton updateDataEtagere = new JButton("Valider les donn�es\n");
		
		// On lui attribue un �couteur
		updateDataEtagere.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						// On pr�pare la requ�te d'update
						String requete = "UPDATE etag�re SET ";
						// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
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
							// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
							requete += "positionEtag�re='"+ t[1] + "'";
							requete += " WHERE identifiantEtag�re=" + t[0];
							// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean statut = MyConnexionInsertDeleteUpdate(requete);
							// Si la requ�te � r�ussi, nous affichons un message pour dire que l'�tag�re a �t� modifi�.
							// Et nous recachons le panel bottomEtageres.
							if(statut){
								JOptionPane.showMessageDialog(null, "L'�tag�re d'identifiant " + t[0] + " a �t� modifi� dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								bottomEtageres.setVisible(false);
							}
							// Sinon, nous affichons un message d'erreur
							else
							{
								JOptionPane.showMessageDialog(null, "L'�tag�re d'identifiant " + t[0] + " n'a pas �t� modifi� dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						// On effectue une mise � jour des listes etagereAModifier et etagereASupprimer
						MAJpanelEtageres(etagereAModifier);
						MAJpanelEtageres(etagereASupprimer);
					}
				});
		// On ajoute au panel bottomEtageres le bouton updateDataEtagere
		bottomEtageres.add(updateDataEtagere);
		
		// On ajoute au panelModifEtageres le panel nord � la position nord et le panel bottomEtageres � la position sud.
		panelModifEtageres.add(nord, BorderLayout.NORTH);
		panelModifEtageres.add(bottomEtageres, BorderLayout.SOUTH);
	}

	// Cr�ation du panel de suppression d'�tag�re
	public void createPanelSupprimerEtageres(){
		// On cr�� le panel panelSupprimerEtageres qui contiendra tous les �l�ments de l'onglet SupprimerEtageres de la FenetreAdmin
		panelSupprimerEtageres = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelSupprimerEtageres.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton supprimerEtageres
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On cr�� le panel inner qui contiendra les champs pour supprimer une cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cr�� la liste des �tag�res � supprimer
		creerListeEtagere(etagereASupprimer);
		
		// On ajoute au panel inner la liste d'�tag�res � supprimer
		inner.add(new JLabel("Etag�re(s) � supprimer:"));
		inner.add(etagereASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);

		// On cr�� un bouton supprimerEtageres
		JButton supprimerEtageres = new JButton("Supprimer l'�tag�re s�lectionn�e.");
		
		// On lui attribue un �couteur
		supprimerEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de suppression
				String requete = "DELETE FROM etag�re WHERE positionEtag�re='";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = etagereASupprimer.getSelectedItem().toString();;
				
				// Si aucune �tag�re n'est s�lectionn�e, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une �tag�re � supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0] + "'";
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean status = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ de suppression de l'�tag�re � vide.
					if(!(status)){
						JOptionPane.showMessageDialog(null, "L'�tag�re de la position " + t[0] + " n'a pas �t� supprim� dans la base de donn�es", "Erreur", JOptionPane.ERROR_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
					// Sinon on affiche un message de succ�s et nous remettons le champ de suppression de l'�tag�re � vide.
					else
					{
						JOptionPane.showMessageDialog(null, "L'�tag�re de la position " + t[0] + " a pas �t� supprim� dans la base de donn�es", "Information", JOptionPane.INFORMATION_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
				}
				// Mise � jour des listes etagereAModifier et etagereASupprimer
				MAJpanelEtageres(etagereAModifier);
				MAJpanelEtageres(etagereASupprimer);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerEtageres
		nord.add(supprimerEtageres);
		
		// On ajoute au panelSupprimerEtageres le panel nord au centre
		panelSupprimerEtageres.add(nord, BorderLayout.CENTER);
	}
	
	// Cr�ation du panel de Gestion des bouteilles
	public void createPanelBouteilles(){
		// On cr�� le panel panelBouteilles qui contiendra tous les �l�ments de l'onglet GestionBouteille de la FenetreAdmin
		panelBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelBouteilles.setLayout(new BorderLayout());

		// On cr�� les onglets de tabbedPaneModifBouteilles
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
	
	// Cr�ation du panel d'Ajout de bouteilles
	public void createPanelAjoutBouteilles(){
		// On cr�� le panel panelAjoutBouteilles qui contiendra tous les �l�ments de l'onglet AjoutBouteilles de la FenetreAdmin
		panelAjoutBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelAjoutBouteilles.setLayout(new BorderLayout());
	
		// On cr�� le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new GridLayout(2,1));
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel innerNord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 3 ligne et 3 colonnes
		innerNord.setLayout(new GridLayout(1,2));
				
		// On ajoute le champ volume de la bouteille dans le panel innerNord
		innerNord.add(new JLabel("Volume de la bouteille (en L): "));
		final JTextField capacity = new JTextField();
		innerNord.add(capacity);
		nord.add(innerNord);
		
		// On cr�� un bouton addBouteille
		JButton addBouteille = new JButton("Ajouter une bouteille");
		
		// On lui attribut un �couteur
		addBouteille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'ajout
				String requete = "INSERT INTO bouteille(taille) VALUES (";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = capacity.getText();
				
				
				
				// Si l'utilisateur n'a pas donn� de volume pour la bouteille, nous lui affichons un message d'erreur et nous mettons le champ � vide.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					capacity.setText("");
				}
				else{
					// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
					requete += t[0] + ")";
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ � vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litres n'a pas �t� ajout�e dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						capacity.setText("");
					}
					// Sinon on affiche un message de succ�s et nous remettons le champ � vide.
					else
					{
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a �t� ajout�e dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						capacity.setText("");
					}
					// On met � jour les listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
				}
			}
		});
		
		// On ajoute au panel nord le bouton addBouteille 
		nord.add(addBouteille);
		
		// On ajoute au panelAjoutBouteilles le panel nord au nord 
		panelAjoutBouteilles.add(nord,BorderLayout.NORTH);
		
	}
	
	// Cr�ation du panel de modification de bouteille
	public void createPanelModifBouteilles(){
		// On cr�� le panel panelModifBouteilles qui contiendra tous les �l�ments de l'onglet ModifBouteilles de la FenetreAdmin
		panelModifBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelModifBouteilles.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton modifierBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour modifier une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 0 colonnes
		inner.setLayout(new GridLayout(1,0));
		
		// On cr�� la liste des bouteilles.
		creerListeBouteille(bouteilleAModifier);
		
		// On ajoute le champ capacit� au panel inner
		inner.add(new JLabel("Choisir la capacit� � modifier (en Litre): \n"));
		inner.add(bouteilleAModifier);
		
		// On ajoute le panel inner au panel nord � la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		
		// On cr�� le panel bottomEtageres qui contiendra les champs � remplir avant de modifier une �tag�re
		final JPanel bottomBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomBouteilles.setLayout(new GridLayout(3,2));
		
		// On cr�� les JTextField pour ajouter les donn�es de la bouteille � modifier dedans.
		final JTextField idBouteille = new JTextField();
		final JTextField capacityBottle = new JTextField();
		
		// On ajoute au panel bottomBouteilles le champ identifiant Bouteille
		bottomBouteilles.add(new JLabel("Id Bouteille : "));
		bottomBouteilles.add(idBouteille);
		// On rend ce champ nom �ditable
		idBouteille.setEditable(false);
		
		// On ajoute au panel bottomBouteilles le champ capacit�
		bottomBouteilles.add(new JLabel("Capacit� en Litre(s) : "));
		bottomBouteilles.add(capacityBottle);
		
		// On cache le panel bottomBouteilles
		bottomBouteilles.setVisible(false);

		// On cr�� un bouton modifierBouteille
		JButton modifierBouteilles = new JButton("Modifier la bouteille s�lectionn�e");
		
		// On lui attribue un �couteur
		modifierBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de selection
				String requete = "SELECT * FROM bouteille WHERE taille=";
				// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
				String [] t = new String[1];
				t[0] = bouteilleAModifier.getSelectedItem().toString();;
				// FLOTTANT marche seulement avec 1 chiffre apr�s la virgule.
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une bouteille � modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0];
					// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionSelect(String requ�te)
					ResultSet statut = MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions d� � la requ�te.					
						try {
							// Si la requ�te a renvoy� des donn�es, nous mettons ces donn�es dans les champs de modification de la bouteille 
							if(statut.first()){
								idBouteille.setText(statut.getString("identifiantBouteille"));
								capacityBottle.setText(statut.getString("taille"));
							}
							// Sinon, nous affichons un message d'erreur et nous mettons l'etagereAModifier � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas �t� s�lectionn�e dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
		
		// On cr�� un bouton updateDataBottle
		JButton updateDataBottle = new JButton("Valider les donn�es\n");
		
		// On lui attribue un �couteur
		updateDataBottle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'update
				String requete = "UPDATE bouteille SET ";
				// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
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
					// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
					requete += "taille=\'"+ t[1] + "\'";
					requete += " WHERE identifiantBouteille=" + t[0];
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � r�ussi, nous affichons un message pour dire que la bouteille a �t� modifi�.
					// Et nous recachons le panel bottomBouteilles.
					if(statut){
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " a �t� modifi� dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						bottomBouteilles.setVisible(false);
					}
					// Sinon, nous affichons un message d'erreur
					else
					{
						JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " n'a pas �t� modifi� dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					// On effectue une mise � jour des listes bouteilleAModifier et bouteilleASupprimer
					MAJpanelBouteilles(bouteilleAModifier);
					MAJpanelBouteilles(bouteilleASupprimer);
				}
				
			}
		});
		// On ajoute au panel bottomBouteilles le bouton updateDataBottle
		bottomBouteilles.add(updateDataBottle);
	
		// On ajoute au panelModifBouteilles le panel nord � la position nord et le panel bottomBouteilles � la position sud.
		panelModifBouteilles.add(nord, BorderLayout.NORTH);
		panelModifBouteilles.add(bottomBouteilles, BorderLayout.SOUTH);
		
	}

	// Cr�ation du panel de suppression de bouteille
	public void createPanelSupprimerBouteilles(){
		// On cr�� le panel panelSupprimerBouteilles qui contiendra tous les �l�ments de l'onglet SupprimerBouteilles de la FenetreAdmin
		panelSupprimerBouteilles = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelSupprimerBouteilles.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton supprimerBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 2 lignes et 0 colonne
		nord.setLayout(new GridLayout(2,0));
		
		// On cr�� le panel inner qui contiendra les champs pour supprimer une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cr�� la liste des bouteilles � supprimer
		creerListeBouteille(bouteilleASupprimer);
		
		// On ajoute au panel inner la liste des bouteilles � supprimer
		inner.add(new JLabel("Bouteille(s) � supprimer:"));
		inner.add(bouteilleASupprimer);
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On cr�� un bouton supprimerBouteilles
		JButton supprimerBouteilles = new JButton("Supprimer la bouteille s�lectionn�e.");
		
		// On lui attribue un �couteur
		supprimerBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de suppression
				String requete = "DELETE FROM bouteille WHERE taille=\'";
				// On pr�pare la requ�te pour la selection
				String requeteSelect = "SELECT * FROM bouteille WHERE taille=\'";
				String idBottle = null;
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = bouteilleASupprimer.getSelectedItem().toString();;
				
				// Si aucune bouteille n'est s�lectionn�e, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner une bouteille � supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la pr�paration de la requ�te de suppression en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0] + "\'";
					// On fini la pr�paration de la requ�te de s�lection en ajoutant le param�tre n�cessaire � la requ�te
					requeteSelect += t[0] + "\'";
					// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionSelect(String requ�te)
					ResultSet statut = MyConnexionSelect(requeteSelect);
					// Bloc try catch pour la gestion des excetions d� � la requ�te.					
						try {
							// Si la requ�te a renvoy� des donn�es, nous r�cup�rons l'identifiant de la bouteille � supprimer
							if(statut.first()){
								idBottle = statut.getString("identifiantBouteille");
							}
							// Sinon, nous affichons un message d'erreur et nous mettons la bouteille � supprimer � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas �t� supprim� dans la base de donn�es", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Si nous avons changer la valeur de l'idBottle (nous avons s�lectionn� la bouteille dans la base)
						if(idBottle != null)
						{
							// On fini la pr�paration de la requ�te de suppression en ajoutant le param�tre n�cessaire � la requ�te
							requete += " AND identifiantBouteille=" + idBottle;
							// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
							boolean status = MyConnexionInsertDeleteUpdate(requete);
							// Si la requ�te � r�ussi, nous affichons un message pour dire que la bouteille a �t� supprim�.
							// Et nous remettons le champ de suppression de la bouteille � vide.
							if((status)){
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a pas �t� supprim� dans la base de donn�es", "Information", JOptionPane.INFORMATION_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
							// Sinon on affiche un message d'erreur et nous remettons le champ de suppression de la bouteille � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "La bouteille de " + t[0] + " litre n'a pas �t� supprim� dans la base de donn�es", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						}
						// On met � jour les listes bouteilleAModifier et bouteilleASupprimer
						MAJpanelBouteilles(bouteilleAModifier);
						MAJpanelBouteilles(bouteilleASupprimer);
				}
			}
		});
		
		// On ajoute au panel nord le bouton supprimerBouteilles au sud
		nord.add(supprimerBouteilles, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerBouteilles le panel nord au centre
		panelSupprimerBouteilles.add(nord, BorderLayout.CENTER);
	}

	// Cr�ation du panel de Gestion des vins
	public void createPanelVins(){
		// On cr�� le panel panelVins qui contiendra tous les �l�ments de l'onglet GestionVin de la FenetreAdmin
		panelVins = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelVins.setLayout(new BorderLayout());

		// On cr�� les onglets de tabbedPaneModifBouteilles
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

	// Cr�ation du panel d'Ajout de vins
	public void createPanelAjoutVins(){
		// On cr�� le panel panelAjoutVins qui contiendra tous les �l�ments de l'onglet AjoutVins de la FenetreAdmin
		panelAjoutVins = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelAjoutVins.setLayout(new BorderLayout());
	
		// On cr�� le panel nord qui le panel innerNord et le bouton addBouteille
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(6,2));

		// On initialise les champs d'ajout du vin
		regionVins = new JTextField();
		domaineVins = new JTextField();
		ch�teauVins = new JTextField();
		couleurVins = new JComboBox<String>();
		cepageVins = new JTextField();
		dateVins = new JTextField();

		
		// On met la taille du JTextField � 200px de long et 30 px de hauteur
		regionVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Region du vin dans le panel inner
		inner.add(new JLabel("Region du vin :"));
		inner.add(regionVins);
				
		// On met la taille du JTextField � 200px de long et 30 px de hauteur
		domaineVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Domaine du vin dans le panel inner
		inner.add(new JLabel("Domaine du vin :"));
		inner.add(domaineVins);
		
		// On met la taille du JTextField � 200px de long et 30 px de hauteur
		ch�teauVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le champ Ch�teau du vin dans le panel inner
		inner.add(new JLabel("Ch�teau du vin :"));
		inner.add(ch�teauVins);
		
		// On met la taille du JComboBox � 200px de long et 30 px de hauteur
		couleurVins.setPreferredSize(new Dimension(200,30));
		// On rajoute les couleurs de vins dans la JComboBox<String>
		couleurVins.addItem("");
		couleurVins.addItem("rouge");
		couleurVins.addItem("blanc");
		couleurVins.addItem("ros�");
		// On ajoute le champ Couleur du vin dans le panel inner
		inner.add(new JLabel("Couleur du vin :"));
		inner.add(couleurVins);
				
		// On met la taille du JTextField � 200px de long et 30 px de hauteur
		cepageVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ C�page du vin dans le panel inner
		inner.add(new JLabel("C�page du vin :"));
		inner.add(cepageVins);
		
		// On met la taille du JTextField � 200px de long et 30 px de hauteur
		dateVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le champ Ann�e du vin dans le panel inner
		inner.add(new JLabel("Ann�e du vin :"));
		inner.add(dateVins);
				
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cr�� un bouton ajouterVins
		JButton ajouterVins = new JButton("Ajouter le vins");
		
		// On lui attribut un �couteur
		ajouterVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'ajout
				String requete = "INSERT INTO vins(domaineVin, regionVin, ch�teauVin, couleur, cepageVin,dateVin) VALUES (";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[6];
				t[0] = domaineVins.getText();
				t[1] = regionVins.getText();
				t[2] = ch�teauVins.getText();
				t[3] = couleurVins.getSelectedItem().toString();
				t[4] = cepageVins.getText();
				t[5] = dateVins.getText();
				
				// Si un ou plusieurs arguments n'ont pas �t� renseign�s, nous lui affichons un message d'erreur et nous mettons les champs � vide.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty() || t[5].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					domaineVins.setText("");
					regionVins.setText("");
					ch�teauVins.setText("");
					couleurVins.setSelectedItem("");;
					cepageVins.setText("");
					dateVins.setText("");
				}
				else{
					// Si la date poss�de plus de 4 caract�res, nous affichons un message d'erreur et nous mettons le champ date � vide.
					if(t[5].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une date coh�rante.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						dateVins.setText("");
					}
					else
					{
					
						// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
						requete += "'" + t[0] + "','"+ t[1] + "','" + t[2] + "','" + t[3] + "','" + t[4] + "'," + t[5] + ")";
						// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
						boolean statut = MyConnexionInsertDeleteUpdate(requete);
						// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ � vide.
						if(!(statut)){
							JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " n'a pas �t� ajout�e dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							domaineVins.setText("");
							regionVins.setText("");
							ch�teauVins.setText("");
							couleurVins.setSelectedItem("");;
							cepageVins.setText("");
							dateVins.setText("");
						}
						// Sinon on affiche un message de succ�s et nous remettons le champ � vide.
						else
						{
							JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " a �t� ajout�e dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
							domaineVins.setText("");
							regionVins.setText("");
							ch�teauVins.setText("");
							couleurVins.setSelectedItem("");;
							cepageVins.setText("");
							dateVins.setText("");
						}
					}
					// On met � jour les listes vinAModifier et vinASupprimer
					MAJpanelVins(vinAModifier);
					MAJpanelVins(vinASupprimer);
				}
			}
		});
		
		// On ajoute au panel nord le bouton ajouterVins au sud
		nord.add(ajouterVins, BorderLayout.SOUTH);
		
		// On ajoute au panelAjoutVins le panel nord au centre
		panelAjoutVins.add(nord,BorderLayout.CENTER);
		
	}
	
	
	// Cr�ation du panel de modification de vins
	public void createPanelModifVins(){
		// On cr�� le panel panelModifVins qui contiendra tous les �l�ments de l'onglet ModifVins de la FenetreAdmin
		panelModifVins = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelModifVins.setLayout(new BorderLayout());
	
		// On cr�� le panel nord qui le panel inner et le bouton modifierVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter une bouteille
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 6 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,0));


		// On creer la liste de vin � modifier
		creerListeVin(vinAModifier);
		
		// On ajoute le champs vinAModifier dans le panel inner
		inner.add(new JLabel("Choisir le vin � modifier.\n"));
		inner.add(vinAModifier);
		
		// On ajoute le panel inner dans le panel nord � la position nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cr�� le panel bottomEtageres qui contiendra les champs � remplir avant de modifier une �tag�re
		final JPanel bottomVins = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 5 ligne et 2 colonnes
		bottomVins.setLayout(new GridLayout(7,2));
				
		// On cr�� les JTextField pour ajouter les donn�es de la bouteille � modifier dedans.
		final JTextField regionVinsM = new JTextField();
		final JTextField domaineVinsM = new JTextField();
		final JTextField ch�teauVinsM = new JTextField();
		final JTextField cepageVinsM = new JTextField();
		final JTextField dateVinsM = new JTextField();
		
		// On cr�� la JComboBox<String> pour la couleur du vin
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
		
		// On ajoute le champs Ch�teau au panel bottomVins
		bottomVins.add(new JLabel("Ch�teau : "));
		bottomVins.add(ch�teauVinsM);
		
		// On ajoute le champs Couleur au panel bottomVins
		bottomVins.add(new JLabel("Couleur : "));
		bottomVins.add(couleurVinsM);
		
		// On ajoute le champs C�page au panel bottomVins
		bottomVins.add(new JLabel("C�page : "));
		bottomVins.add(cepageVinsM);
		
		// On ajoute le champs Ann�e au panel bottomVins
		bottomVins.add(new JLabel("Ann�e : "));
		bottomVins.add(dateVinsM);
		
		// On cache le panel bottomVins
		bottomVins.setVisible(false);
	
		// On cr�� le bouton modifierVins
		JButton modifierVins = new JButton("Modifier le vin s�lectionn�.");
		
		// On lui attribue un �couteur
		modifierVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de selection
				String requete = "SELECT * FROM vins WHERE identifiantVin=";
				// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
				String [] t = new String[1];
				t[0] = vinAModifier.getSelectedItem().toString();;
				
				// Si l'argument est vide, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner un vin � modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				//Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0];
					// On r�cup�re le resultat de la requ�te � l'aide de MyConnexionSelect(String requ�te)
					ResultSet statut = MyConnexionSelect(requete);
					// Bloc try catch pour la gestion des excetions d� � la requ�te.					
						try {
							// Si la requ�te a renvoy� des donn�es, nous mettons ces donn�es dans les champs de modification du vin 
							if(statut.first()){
								vinCourant = t[0];
								regionVinsM.setText(statut.getString("regionVin"));
								domaineVinsM.setText(statut.getString("domaineVin"));
								ch�teauVinsM.setText(statut.getString("ch�teauVin"));
								couleurVinsM.setSelectedItem(statut.getString("couleur"));
								cepageVinsM.setText(statut.getString("cepageVin"));
								dateVinsM.setText(statut.getString("dateVin"));
								
							}
							// Sinon, nous affichons un message d'erreur et nous mettons le vinAModifier � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " n'a pas �t� s�lectionner dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								vinAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Nous affichons le bottomVins
						bottomVins.setVisible(true);;
				}
				// On met � jour les listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
				
			}
		});
		
		// On ajoute au panel nord le bouton modifierVins au sud
		nord.add(modifierVins, BorderLayout.SOUTH);
		
		// On cr�� un bouton updateDataVins
		JButton updateDataVins = new JButton("Valider les donn�es.\n");
		
		// On lui attribue un �couteur
		updateDataVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'update
				String requete = "UPDATE cave.vins SET ";
				// On cr�� un tableau de String pour r�cup�rer le champ pour la modification de l'�tag�re
				String [] t = new String[6];
				t[0] = regionVinsM.getText();
				t[1] = domaineVinsM.getText();
				t[2] = ch�teauVinsM.getText();
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
					// Si l'ann�e renseign�e est diff�rente de 4 caract�res on affiche un message d'erreur
					if(t[5].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une ann�e valide.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						// On fini la pr�paration de la requ�te en ajoutant les param�tres n�cessaires � la requ�te
						requete += "regionVin='"+ t[0] + "', domaineVin='" + t[1] + "', ch�teauVin='" + t[2] + "', couleur='" + t[3] + "', cepageVin='" + t[4] + "', dateVin=" + t[5];
						requete += " WHERE identifiantVin=" + vinCourant;
						// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
						boolean statut = MyConnexionInsertDeleteUpdate(requete);
						// Si la requ�te � r�ussi, nous affichons un message pour dire que la vin a �t� modifi�.
						// Et nous recachons le panel bottomVins.
						if((statut)){
							JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " a �t� modifi� dans la base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
							bottomVins.setVisible(false);
						}
						// Sinon on affiche un message d'erreur.
						else
						{
							JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " n'a pas �t� modifi� dans la base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					}
					// On met � jour les listes vinAModifier et vinASupprimer
					MAJpanelVins(vinAModifier);
					MAJpanelVins(vinASupprimer);
				}
				
			}
		});
		
		// On ajoute au panel bottomVins le bouton updateDataVins
		bottomVins.add(updateDataVins);
		
		// On ajoute au panelModifVins le panel nord � la position nord et le panel bottomVins � la position sud.
		panelModifVins.add(nord, BorderLayout.NORTH);
		panelModifVins.add(bottomVins, BorderLayout.SOUTH);
		
	}

	// Cr�ation du panel de suppression de vin
	public void createPanelSupprimerVins(){
		// On cr�� le panel panelSupprimerVins qui contiendra tous les �l�ments de l'onglet SupprimerVins de la FenetreAdmin
		panelSupprimerVins = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelSupprimerVins.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton supprimerVins
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		nord.setLayout(new BorderLayout());
		
		// On cr�� le panel inner qui contiendra les champs pour supprimer un vin
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(1,2));
		
		// On cr�� la liste des vins � supprimer
		creerListeVin(vinASupprimer);
		
		// On ajoute au panel inner la liste des vins � supprimer
		inner.add(new JLabel("Vins � supprimer:"));
		inner.add(vinASupprimer);
		
		// On ajoute au panel nord le panel inner au nord
		nord.add(inner, BorderLayout.NORTH);
		
		// On cr�� un bouton supprimerVins
		JButton supprimerVins = new JButton("Supprimer le vin s�lectionn�.");
		
		// On lui attribue un �couteur
		supprimerVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te de suppression
				String requete = "DELETE FROM vins WHERE identifiantVin=";
				// On cr�� un tableau de String pour r�cup�rer les champs pour l'ajout de la cave
				String [] t = new String[1];
				t[0] = vinASupprimer.getSelectedItem().toString();;
				
				// Si aucune �tag�re n'est s�lectionn�e, nous affichons un message d'erreur.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez s�lectionner un vin � supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				// Sinon
				else{
					// On fini la pr�paration de la requ�te en ajoutant le param�tre n�cessaire � la requ�te
					requete += t[0];
					// Nous effectuons la requ�te � l'aide de la fonction MyConnexionInsertDeleteUpdate(String requete).
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					// Si la requ�te � �chouer, nous affichons un message d'erreur et nous remettons le champ de suppression de vin � vide.
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + "n'a pas �t� supprim�e de votre base de donn�es.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}
					// Sinon, on met un message de succ�s et on remet le champ de suppression de vin � vide
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " a �t� supprim�e de votre base de donn�es.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}	
				}
				// Mise � jour des listes vinAModifier et vinASupprimer
				MAJpanelVins(vinAModifier);
				MAJpanelVins(vinASupprimer);
			}
		});
		
		// On ajoute au panel nord le bouton supprimerVins au sud 
		nord.add(supprimerVins, BorderLayout.SOUTH);
		
		// On ajoute au panelSupprimerVins le panel nord au centre
		panelSupprimerVins.add(nord, BorderLayout.CENTER);
	}

	// Cr�ation du panel de Rangement de vin/bouteille sur �tag�re
	public void createPanelRangement(){
		// On cr�� le panel panelRangement qui contiendra tous les �l�ments de l'onglet Rangement de la FenetreAdmin
		panelRangement = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panelRangement.setLayout(new BorderLayout());

		// On cr�� le panel nord qui le panel inner et le bouton ajouterRangement
		JPanel nord = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		nord.setLayout(new GridLayout(2,0));
		
		// On cr�� le panel inner qui contiendra les champs pour ajouter un rangement de vin/bouteille dans la cave
		JPanel inner = new JPanel();
		// On lui dit que l'affichage des champs se fera sous forme de grille avec 1 ligne et 2 colonnes
		inner.setLayout(new GridLayout(4,2));
		
		// On cr�� la liste des �tag�res disponibles
		creerListeEtagere(listeetagere);
		
		// On ajoute le champ �tag�re au panel inner
		inner.add(new JLabel("Veuillez selectionner une �tag�re"));
		inner.add(listeetagere);
		
		// On cr�� la liste des bouteilles disponibles
		creerListeBouteilleRangement(listebouteille);
		
		// On ajoute le champ bouteille au panel inner
		inner.add(new JLabel("Veuillez selectionner une bouteille"));
		inner.add(listebouteille);
		
		
		// On cr�� la liste des vins disponibles
		creerListeVin(listevin);
		
		// On ajoute le champ vin au panel inner
		inner.add(new JLabel("Veuillez selectionner un vin"));
		inner.add(listevin);
		
		
		// On ajoute au panel nord le panel inner
		nord.add(inner);
		
		// On cr�� un bouton ajouterRangement
		JButton ajouterRangement = new JButton("Ranger le vins");
		
		// On lui attribue un �couteur
		ajouterRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On pr�pare la requ�te d'insertion
				String requeteInsert = "INSERT INTO bouteille_has_vins(Bouteille_identifiantBouteille, Vins_identifiantVin, Etag�re_identifiantEtag�re) VALUES (";
				// On pr�pare la requ�te de s�lection de bouteilles
				String requeteSelectBottle = "SELECT * FROM bouteille WHERE taille=";
				String idBottle = null;
				// On pr�pare la requ�te de s�lection d'�tag�res
				String requeteSelectEtagere = "SELECT * FROM etag�re WHERE positionEtag�re='";
				String idEtagere = null;
				
				// On cr�� un tableau de String pour r�cup�rer les champs pour le rangement du vin/bouteille
				String [] t = new String[3];
				t[0] = listeetagere.getSelectedItem().toString();
				t[1] = listebouteille.getSelectedItem().toString();
				t[2] = listevin.getSelectedItem().toString();
				
				// Si c'est une recherche par ann�e on fait la requ�te et on affiche le panel bottom des r�sultats.
				if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demand�s.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					listeetagere.setSelectedItem("");
					listebouteille.setSelectedItem("");
					listevin.setSelectedItem("");
				}
				else{
					// On fini de pr�parer les requ�tes de s�lection avec les champs r�cup�r�s.
					requeteSelectBottle += t[1] + "";
					requeteSelectEtagere += t[0] + "\'";
					// On effectue les deux select
					ResultSet statutBottle = MyConnexionSelect(requeteSelectBottle);
					ResultSet statutEtagere = MyConnexionSelect(requeteSelectEtagere);
						try {
							// Si la s�lection des bouteilles � donner un r�sultat on r�cup�re l'identifiant du premier r�sultat
							if(statutBottle.first()){
								idBottle = statutBottle.getString("identifiantBouteille");
							}
							// Sinon, on affiche une erreur et on dit que l'on a pas s�lectionn� de bouteille. On met le champ � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[1] + " litre n'a pas �t� selectionn� dans la base de donn�es", "Erreur", JOptionPane.ERROR_MESSAGE);
								listebouteille.setSelectedItem("");
							}
							// Si la s�lection des �tag�res � donner un r�sultat, on r�cup�re l'identifiant du premier r�sultat.
							if(statutEtagere.first()){
								idEtagere = statutEtagere.getString("identifiantEtag�re");
							}
							// Sinon, message d'erreur et on met le champ � vide
							else
							{
								JOptionPane.showMessageDialog(null, "L'�tag�re � la position " + t[0] + " n'a pas �t� selectionn� dans la base de donn�es", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
							}
							
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						// Si on a r�cup�r� une bouteille et une �tag�re
						if(idBottle != null && idEtagere != null)
						{
							// On fini de pr�parer la requ�te d'insert avec tous les champs n�cessaires � la requ�te.
							requeteInsert += idBottle + "," + t[2] + "," + idEtagere + ")";
							// On effectue le insert
							boolean status = MyConnexionInsertDeleteUpdate(requeteInsert);
							// Si la requ�te n'a pas fonctionn�, on aura une erreur. On met tous les champs � vide
							if(!(status)){
								JOptionPane.showMessageDialog(null, "Il y a eu une erreur", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
							// Sinon, on a un message de succ�s et on met tous les champs � vide.
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin � �t� rang�", "Information", JOptionPane.INFORMATION_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
						}
				}
				// On met � jour les listes contenu dans ce panel : listeetagere, listebouteille et listevin
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute au panel nord le bouton ajouterRangement
		nord.add(ajouterRangement, BorderLayout.SOUTH);
		
		// On ajoute au panelRangement le panel nord au centre
		panelRangement.add(nord,BorderLayout.CENTER);
	}
	
	// Fonction qui nous permet de recup�rer dans un ResultSet le resultat de la requ�te pass�e en param�tre.
	private ResultSet MyConnexionSelect(String requete) {
    	
    	// Resultat de la requ�te.
        ResultSet s = null;
        
        // Mise en forme de la requ�te vue que nous enregistrons les mots de passe en encodant en MD5
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Connexion � la base de donn�es
            conn = DriverManager.getConnection(url, log, pwd);
           
            // Cr�ation et ex�cution de la requ�te.
            Statement statement = (Statement) conn.createStatement();
            s = statement.executeQuery(requete);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    	// On retourne le r�sultat de la requ�te pour pouvoir faire le traitement en cons�quence.
        return s;
    }

	// Fonction qui renvoie true si la requete s'est ex�cut�e correctement, false sinon.
	private boolean MyConnexionInsertDeleteUpdate(String requete) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Connexion � la base de donn�es
            conn = DriverManager.getConnection(url, log, pwd);
           
            // Cr�ation et ex�cution de la requ�te.
            Statement statement = (Statement) conn.createStatement();
            statement.executeUpdate(requete);
         // On retourne true si la requ�te a fonctionn�.
           return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    	// On retourne false si la requ�te n'a pas fonctionn�.
        return false;
	}
	
	// Fonction qui nous permet d'actualiser l'affichage du r�sultat de la requ�te dans onglet Recherche
	public void rafraichirDataRecherche(JPanel p, JTable o){
		// On enl�ve tous les �l�ments du panel bottom
		p.removeAll();
		if(o == null){
			p.setVisible(false);
		}
		else
		{
			// On lui ajoute au nord les ent�tes du tableau
			p.add(o.getTableHeader(), BorderLayout.NORTH);
			// On lui ajoute au centre les donn�es du r�sultat de la requ�te
			p.add(o, BorderLayout.CENTER);
				
		}
	}
	
	// Fonction qui permet de rafraichir les listes de caves
	public void MAJpanelCave(JComboBox<String> p){
		p.removeAllItems();
		creerListeCave(p);
	}
	
	// Fonction qui permet de r�cup�rer la liste des cave � vin de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
	public void creerListeCave(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		ResultSet statement = MyConnexionSelect("SELECT * FROM  caveavin WHERE Utilisateur_identifiantUtilisateur=" + idAdmin);
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
	
	// Fonction qui permet de r�cup�rer la liste des vins dans la base et de les organiser dans une JComboBox<String>
	public void creerListeVin(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM vins";
		ResultSet statement = MyConnexionSelect(req);
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
	
	// Fonction qui permet de r�cup�rer la liste des bouteilles dans la base et de les organiser dans une JComboBox<String>
	public void creerListeBouteille(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM bouteille";
		ResultSet statement = MyConnexionSelect(req);
		try {
			while(statement.next()){
				String tailleBouteilleRes = statement.getString("taille");
				c.addItem(tailleBouteilleRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Fonction qui permet de rafraichir les listes d"�tag�res
	public void MAJpanelEtageres(JComboBox<String> p){
		p.removeAllItems();
		creerListeEtagere(p);
	}
	
	// Fonction qui permet de r�cup�rer la liste des �tag�res de la cave courante et de l'utilisateur dans la base et de les organiser dans une JComboBox<String>
	public void creerListeEtagere(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM etag�re WHERE CaveAVin_Utilisateur_identifiantUtilisateur = " + idAdmin + " AND CaveAVin_nomCave = '" + caveAVinCourante + "'";
		ResultSet statement = MyConnexionSelect(req);
		try {
			while(statement.next()){
				String posEtagereRes =statement.getString("positionEtag�re");
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
	
	// Fonction qui permet de r�cup�rer la liste des bouteilles qui ne sont pas d�j� pleine de vin dans la base et de les organiser dans une JComboBox<String> (pour onglet Rangement)
	public void creerListeBouteilleRangement(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		// Requete pour ne selectionner que les bouteilles non utilis�es.  
		String req = "SELECT * FROM bouteille LEFT JOIN bouteille_has_vins ON bouteille.identifiantBouteille = bouteille_has_vins.Bouteille_identifiantBouteille WHERE bouteille_has_vins.Bouteille_identifiantBouteille IS NULL";
		ResultSet statement = MyConnexionSelect(req);
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

