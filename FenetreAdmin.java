import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.mysql.jdbc.Statement;



public class FenetreAdmin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idAdmin;
	private String caveAVinCourante;
	private String vinCourant;
	
	// Onglet principal
	private		JTabbedPane tabbedPane = new JTabbedPane();;
	private		JPanel		panelRecherche;
	private		JPanel		panelAjoutCave;
	private		JPanel		panelModificationCave;
	private		JPanel		panelSuppressionCave;
	private		JPanel		panelModificationMDP;
	
	// Onglet secondaire
	private		JTabbedPane tabbedPaneModif;
	private		JPanel		panelEtageres;
	private		JPanel		panelBouteilles;
	private		JPanel		panelVins;
	private		JPanel		panelRangement;
	
	
	// Onglet tertiaire
	private		JTabbedPane tabbedPaneModifVins;
	private		JPanel		panelAjoutVins;
	private		JPanel		panelModifVins;
	private		JPanel		panelSupprimerVins;

	private		JTabbedPane tabbedPaneModifBouteilles;
	private		JPanel		panelAjoutBouteilles;
	private		JPanel		panelModifBouteilles;
	private		JPanel		panelSupprimerBouteilles;
	
	private		JTabbedPane tabbedPaneModifEtageres;
	private		JPanel		panelAjoutEtageres;
	private		JPanel		panelModifEtageres;
	private		JPanel		panelSupprimerEtageres;
	
	// Objets pour l'onglet ModifBouteilles
	private JComboBox<String> bouteilleAModifier = new JComboBox<String>();
	
	// Objets pour l'onglet SupprimerBouteilles
	private JComboBox<String> bouteilleASupprimer = new JComboBox<String>();
		
	// Objets pour l'onglet ModifBouteilles
	private JComboBox<String> etagereAModifier = new JComboBox<String>();
	
	// Objets pour l'onglet SupprimerBouteilles
	private JComboBox<String> etagereASupprimer = new JComboBox<String>();
			
	// Objets pour l'onglet AjoutVins
	private		JTextField regionVins;
	private		JTextField domaineVins;
	private		JTextField châteauVins;
	private		JComboBox<String> couleurVins;
	private		JTextField cepageVins;
	private		JTextField dateVins;
	
	// Objets pour l'onglet ModifVins
	private JComboBox<String> vinAModifier = new JComboBox<String>();
	
	// Objets pour l'onglet SupprimerVins
	private JComboBox<String> vinASupprimer = new JComboBox<String>();
	
	// Objets pour onglet Rangement
	private JComboBox<String> listeetagere = new JComboBox<String>();
	private JComboBox<String> listebouteille = new JComboBox<String>();
	private JComboBox<String> listevin = new JComboBox<String>();
	
	// Objets pour onglet Recherche
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Année");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	// Objets pour onglet AjoutCave
	private JTextField nomCaveAAjouter = new JTextField();
	private JTextArea commentaireCave = new JTextArea();
	private JButton ajouterCave = new JButton("Ajouter Cave");
	
	// Objets pour onglet ModificationCave
	private JComboBox<String> caveAModifier = new JComboBox<String>();
	private JButton modifierCave = new JButton("Modifier Cave");
		
	// Objets pour onglet SuppressionCave
	private JComboBox<String> caveASupprimer = new JComboBox<String>();
	private JButton supprimerCave = new JButton("Supprimer Cave");
	
	// Objets pour onglet ModificationMDP
	private JTextField oldMDP;
	private JTextField newMDP;
	private JTextField confirmationMDP;
	
	JTable tableau;
	
	
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
    	
	public FenetreAdmin(String id) {
		super("La cave à vin - Gestion administrateur");
		this.idAdmin = id;
		init();
	}
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );
		
		createRecherche();
		createAjout();
		createModification();
		createSuppression();
		createMDP();
		
		tabbedPane.addTab( "Rechercher Vins", panelRecherche );
		tabbedPane.addTab( "Ajouter une cave", panelAjoutCave );
		tabbedPane.addTab( "Modifier une cave", panelModificationCave );
		tabbedPane.addTab( "Supprimer une cave", panelSuppressionCave );
		tabbedPane.addTab( "Modifier votre mot de passe", panelModificationMDP );
		
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}
	
	// Création du panel de Recherche on fait la même chose que pour la vue Client sans le bouton connexion
	public void createRecherche()
	{
		panelRecherche = new JPanel();
		panelRecherche.setLayout(new BorderLayout());
	
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On créé un panel top qui contiendra les éléments de recherche et le bouton Rechercher.
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		
		// On créé un panel innerTop qui contiendra les éléments de recherche.
		JPanel innerTop = new JPanel();
		innerTop.setLayout(new GridLayout(3,3));
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		rechercherannee.setPreferredSize(new Dimension(200,30));
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		innerTop.add(new JLabel("Année à rechercher :"));
		innerTop.add(rechercherannee);
		innerTop.add(checkannee);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		recherchernom.setPreferredSize(new Dimension(200,30));
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		innerTop.add(new JLabel("Nom à rechercher :"));
		innerTop.add(recherchernom);
		innerTop.add(checknom);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		recherchercouleur.setPreferredSize(new Dimension(200,30));
		recherchercouleur.addItem("");
		recherchercouleur.addItem("blanc");
		recherchercouleur.addItem("rose");
		recherchercouleur.addItem("rouge");
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		innerTop.add(new JLabel("Couleur à rechercher :"));
		innerTop.add(recherchercouleur);
		innerTop.add(checkcouleur);
		// On ajoute le panel innerTop au panel top à la position NORD.
		top.add(innerTop, BorderLayout.CENTER);
		
		// On créé un panel bottom (celui contiendra les éléments de la requête).
		final JPanel bottom = new JPanel();
		// On ne le montre pas tout de suite (on le montera uniquement lorsque le marchand fera une recherche)
		bottom.setLayout(new BorderLayout());
		bottom.setVisible(true);
		// On cree un bouton Rechercher
		JButton searchButton = new JButton("Rechercher");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String [] t = new String[3];
				String requete = "SELECT * FROM vins WHERE";
				if(!(checkannee.isSelected()) && !(checknom.isSelected()) && !(checkcouleur.isSelected())){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une option de recherche.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				
				if(checkannee.isSelected()){
					if(rechercherannee.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par année mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						t[0] = rechercherannee.getText();
					}
				}
				if(checknom.isSelected()){
					if(recherchernom.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						t[1] = recherchernom.getText();
					}
				}
				if(checkcouleur.isSelected()){
					if(recherchercouleur.getSelectedItem().toString().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseigner de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						t[2] = recherchercouleur.getSelectedItem().toString();
					}
				}
				
				if(checknom.isSelected()){
					requete += " cepageVin='" + t[1] + "'";
					if(checkannee.isSelected()){
						requete += " AND dateVin=" +  t[0];
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
						requete += " dateVin=" +  t[0];
						if(checkcouleur.isSelected()){
							requete += " AND couleur='" + t[2] + "'";
						}
					}
					else
					{
						if(checkcouleur.isSelected()){
							requete += " couleur='" + t[2] + "'";
						}
					}
				}
				ResultSet resultRequete = MyConnexionSelect(requete);
				try {
					bottom.setVisible(false);
					if(!(resultRequete.isBeforeFirst())){
						bottom.add(new JLabel("Aucun vin ne correspond a votre recherche.\n"), BorderLayout.NORTH);
						rafraichirDataRecherche(bottom, null);
					}
					else
					{
						int i = 0;
						String[][] 	donnees = new String[25][6];
						while(resultRequete.next()) {
							donnees[i][0] = resultRequete.getString("regionVin");
							donnees[i][1] = resultRequete.getString("domaineVin");
							donnees[i][2] = resultRequete.getString("châteauVin");
							donnees[i][3] = resultRequete.getString("couleur");
							donnees[i][4] = resultRequete.getString("cepageVin");
							donnees[i][5] = resultRequete.getString("dateVin") + "\n";
							i++;
						}
						String[] entetes = {"Region", "Domaine", "Château", "Couleur", "Cépage", "Année"};
						tableau = new JTable(donnees,entetes);
						tableau.setEnabled(false);;
						rafraichirDataRecherche(bottom, tableau);
						// problème synchronisation de l'affichage
					}
					bottom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
top.add(searchButton, BorderLayout.SOUTH);
		
		// On ajoute le panel top qui contient les éléments de recherche ainsi que le bouton à la position NORD
		panelRecherche.add(top, BorderLayout.NORTH);

		// On ajoute à la position SUD le panel bottom qui contient les résultats de la requête.
		panelRecherche.add(bottom, BorderLayout.CENTER);	
	}

	
	public void createAjout()
	{
		panelAjoutCave = new JPanel();
		panelAjoutCave.setLayout(new BorderLayout());

		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(2,2));
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		nomCaveAAjouter.setPreferredSize(new Dimension(200,30));
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Nom de la cave :"));
		inner.add(nomCaveAAjouter);
				
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		commentaireCave.setPreferredSize(new Dimension(200,100));
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Commentaire à faire :"));
		inner.add(commentaireCave);
		
		nord.add(inner, BorderLayout.NORTH);
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		ajouterCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "INSERT INTO caveavin(nomCave, commentaire, Utilisateur_identifiantUtilisateur) VALUES (";
				// On récupère les arguments.
				String [] t = new String[2];
				t[0] = nomCaveAAjouter.getText();
				t[1] = commentaireCave.getText();
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("") || t[1].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner les trois champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += "'" + t[0]+ "','" + t[1] + "'," + idAdmin + ")";
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						nomCaveAAjouter.setText("");
						commentaireCave.setText("");
					}
					MAJpanelModificationCave(caveAModifier);
					MAJpanelSuppressionCave(caveASupprimer);
				}
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(ajouterCave, BorderLayout.SOUTH);
		
		panelAjoutCave.add(nord, BorderLayout.CENTER);
	}

	public void createModification()
	{
		
		panelModificationCave = new JPanel();
		panelModificationCave.setLayout( new BorderLayout() );

		final JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,2));
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		creerListeCave(caveAModifier);
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Nom de la cave à modifier:"));
		inner.add(caveAModifier);
		
		nord.add(inner, BorderLayout.NORTH);
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		modifierCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = caveAModifier.getSelectedItem().toString();;
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une cave à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					caveAVinCourante = t[0];
					nord.setVisible(false);
					tabbedPaneModif.setVisible(true);
					panelModificationCave.add(tabbedPaneModif, BorderLayout.CENTER);
					
				}
				MAJpanelModifierVins(vinAModifier);
				MAJpanelSupprimerVins(vinASupprimer);
				MAJpanelModifierBouteilles(bouteilleAModifier);
				MAJpanelSupprimerBouteilles(bouteilleASupprimer);
				MAJpanelModifierEtageres(etagereAModifier);
				MAJpanelSupprimerEtageres(etagereASupprimer);
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(modifierCave, BorderLayout.SOUTH);
		
		panelModificationCave.add(nord, BorderLayout.CENTER);
		createPanelEtageres();
		createPanelBouteilles();
		createPanelVins();
		createPanelRangement();
		
		tabbedPaneModif = new JTabbedPane();
		tabbedPaneModif.addTab("Gestion étagères", panelEtageres);
		tabbedPaneModif.addTab("Gestion bouteilles", panelBouteilles);
		tabbedPaneModif.addTab("Gestion vins", panelVins);
		tabbedPaneModif.addTab("Rangement des vins", panelRangement);
		tabbedPaneModif.setVisible(false);
		
	}
	
	public void createSuppression()
	{
		panelSuppressionCave = new JPanel();
		panelSuppressionCave.setLayout( new BorderLayout() );

		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,2));
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		creerListeCave(caveASupprimer);
		
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Nom de la cave à supprimer:"));
		inner.add(caveASupprimer);
		
		nord.add(inner, BorderLayout.NORTH);
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		supprimerCave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "DELETE FROM caveavin WHERE nomCave='";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = caveASupprimer.getSelectedItem().toString();;
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une cave à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "'";
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + "n'a pas été supprimée de votre base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "La cave " + t[0] + " a été supprimée de votre base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						caveASupprimer.setSelectedItem("");
					}
					MAJpanelModificationCave(caveAModifier);
					MAJpanelSuppressionCave(caveASupprimer);
				}
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(supprimerCave, BorderLayout.SOUTH);
		
		panelSuppressionCave.add(nord, BorderLayout.CENTER);
	}
	
	public void createMDP(){
		panelModificationMDP = new JPanel();
		panelModificationMDP.setLayout(new BorderLayout());
		
		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(3,2));
		
		inner.add(new JLabel("Veuillez renseigner votre mot de passe : "));
		oldMDP = new JPasswordField();
		inner.add(oldMDP);
		
		inner.add(new JLabel("Veuillez renseigner votre nouveau mot de passe : "));
		newMDP = new JPasswordField();
		inner.add(newMDP);
		
		inner.add(new JLabel("Veuillez confirmer votre nouveau mot de passe : "));
		confirmationMDP = new JPasswordField();
		inner.add(confirmationMDP);
		
		nord.add(inner, BorderLayout.NORTH);
		
		JButton changerMDP = new JButton("Changer le mot de passe.\n");
		
		
		
		changerMDP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String select = "SELECT * FROM cave.utilisateur WHERE identifiantUtilisateur=";
				String update = "UPDATE cave.utilisateur SET motDePasse = MD5('";
				// On récupère les arguments.
				String [] t = new String[3];
				t[0] = oldMDP.getText();
				t[1] = newMDP.getText();
				t[2] = confirmationMDP.getText();
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("") || t[1].equals("") || t[2].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					oldMDP.setText("");
					newMDP.setText("");
					confirmationMDP.setText("");
				}
				else{
					select += idAdmin + " AND motDePasse=MD5('" + t[0] + "')";
					ResultSet changeMDP = MyConnexionSelect(select);
					try {
						if(changeMDP.next()){
							if(t[1].equals(t[2])){
								update += t[2] + "') WHERE utilisateur.identifiantUtilisateur = " + idAdmin;
								boolean statut = MyConnexionInsertDeleteUpdate(update);
								if(statut){
									JOptionPane.showMessageDialog(null, "Votre mot de passe a été changé.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
									oldMDP.setText("");
									newMDP.setText("");
									confirmationMDP.setText("");
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Votre mot de passe n'a pas été changé.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
									oldMDP.setText("");
									newMDP.setText("");
									confirmationMDP.setText("");
								}
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Le nouveau mot de passe et la confirmation ne sont pas identique.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								oldMDP.setText("");
								newMDP.setText("");
								confirmationMDP.setText("");
							}
						}
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

		nord.add(changerMDP, BorderLayout.SOUTH);
		
		panelModificationMDP.add(nord, BorderLayout.CENTER);
		
		
	}
	
	public void createPanelEtageres(){
		panelEtageres = new JPanel();
		panelEtageres.setLayout(new BorderLayout());
		
		createPanelAjoutEtageres();
		createPanelModifEtageres();
		createPanelSupprimerEtageres();
		
		tabbedPaneModifEtageres = new JTabbedPane();
		tabbedPaneModifEtageres.addTab("Ajouter une étagère", panelAjoutEtageres);
		tabbedPaneModifEtageres.addTab("Modifier une étagère", panelModifEtageres);
		tabbedPaneModifEtageres.addTab("Supprimer une étagère", panelSupprimerEtageres);
		
		panelEtageres.add(tabbedPaneModifEtageres, BorderLayout.CENTER);		
		
	}
	
	public void createPanelAjoutEtageres(){
		panelAjoutEtageres = new JPanel();
		panelAjoutEtageres.setLayout(new BorderLayout());

		JPanel nord = new JPanel();
		nord.setLayout(new GridLayout(2,1));
		
		JPanel innerNord = new JPanel();
		innerNord.setLayout(new GridLayout(1,2));
		
		innerNord.add(new JLabel("Position de l'étagère dans la cave (sans ' ou \" ): "));
		final JTextField position = new JTextField();
		innerNord.add(position);
		nord.add(innerNord);
		
		JButton addEtagere = new JButton("Ajouter une étagère");
		
		addEtagere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "INSERT INTO etagère(positionEtagère, CaveAVin_nomCave, CaveAVin_Utilisateur_identifiantUtilisateur) VALUES ('";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = position.getText();
				
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					position.setText("");
				}
				else{
					requete += t[0] + "','"+ caveAVinCourante + "'," + idAdmin + ")";
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une étagère à la position " + t[0] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						position.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une étagère à la position " + t[0] + " litre a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						position.setText("");
					}
				}
				MAJpanelModifierEtageres(etagereAModifier);
				MAJpanelSupprimerEtageres(etagereASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(addEtagere);
		
		panelAjoutEtageres.add(nord,BorderLayout.NORTH);
	}
	
	public void createPanelModifEtageres(){
		panelModifEtageres = new JPanel();
		panelModifEtageres.setLayout(new BorderLayout());
		
		
		final JPanel bottomEtageres = new JPanel();
		bottomEtageres.setLayout(new GridLayout(5,2));
		
		final JTextField idEtagere = new JTextField();
		final JTextField positionEtagere = new JTextField();
		final JTextField idCave = new JTextField();
		final JTextField idUser = new JTextField();
		
		bottomEtageres.add(new JLabel("Identifiant étagère : "));
		bottomEtageres.add(idEtagere);
		idEtagere.setEditable(false);
		
		bottomEtageres.add(new JLabel("Position de l'étagère (sans ' ou \" ) : "));
		bottomEtageres.add(positionEtagere);
		
		bottomEtageres.add(new JLabel("Identifiant cave : "));
		bottomEtageres.add(idCave);
		idCave.setEditable(false);
		
		bottomEtageres.add(new JLabel("Identifiant utilisateur : "));
		bottomEtageres.add(idUser);
		idUser.setEditable(false);
		
		JButton updateDataEtagere = new JButton("Valider les données\n");
		bottomEtageres.add(updateDataEtagere);
		
		bottomEtageres.setVisible(false);
		
		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,0));
		
		creerListeEtagere(etagereAModifier);
		
		inner.add(new JLabel("La position à modifier: \n"));
		inner.add(etagereAModifier);
		nord.add(inner, BorderLayout.NORTH);
		
		JButton modifierEtageres = new JButton("Modifier l'étagère sélectionnée");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		modifierEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "SELECT * FROM etagère WHERE positionEtagère='";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = etagereAModifier.getSelectedItem().toString();;
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une étagère à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "'";
					ResultSet statut = MyConnexionSelect(requete);
						try {
							if(statut.first()){
								idEtagere.setText(statut.getString("identifiantEtagère"));
								positionEtagere.setText(statut.getString("positionEtagère"));
								idCave.setText(statut.getString("CaveAVin_nomCave"));
								idUser.setText(statut.getString("CaveAVin_Utilisateur_identifiantUtilisateur"));
							}
							else
							{
								JOptionPane.showMessageDialog(null, "L'étagère à la position " + t[0] + " n'a pas été sélectionner dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								etagereAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						bottomEtageres.setVisible(true);;
				}
			}
		});
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		updateDataEtagere.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						String requete = "UPDATE etagère SET ";
						// On récupère les arguments.
						String [] t = new String[4];
						t[0] = idEtagere.getText();
						t[1] = positionEtagere.getText();
						t[2] = idCave.getText();
						t[3] = idUser.getText();
						
						// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
						if(t[1].isEmpty()){
							JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							requete += "positionEtagère='"+ t[1] + "'";
							requete += " WHERE identifiantEtagère=" + t[0];
							
							boolean statut = MyConnexionInsertDeleteUpdate(requete);
							if(!statut){
								JOptionPane.showMessageDialog(null, "L'étagère d'identifiant " + t[0] + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "L'étagère d'identifiant " + t[0] + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								bottomEtageres.setVisible(false);
							}
						}
						MAJpanelModifierEtageres(etagereAModifier);
						MAJpanelSupprimerEtageres(etagereASupprimer);
					}
				});
		nord.add(modifierEtageres, BorderLayout.SOUTH);
		
		panelModifEtageres.add(nord, BorderLayout.NORTH);
		panelModifEtageres.add(bottomEtageres, BorderLayout.SOUTH);
	}
	
	public void createPanelSupprimerEtageres(){
		panelSupprimerEtageres = new JPanel();
		panelSupprimerEtageres.setLayout(new BorderLayout());
		
		JPanel nord = new JPanel();
		nord.setLayout(new GridLayout(2,0));
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,2));
		
		creerListeEtagere(etagereASupprimer);
		
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Etagère(s) à supprimer:"));
		inner.add(etagereASupprimer);
		
		nord.add(inner);
		
		JButton supprimerEtageres = new JButton("Supprimer l'étagère sélectionnée.");
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		supprimerEtageres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "DELETE FROM etagère WHERE positionEtagère='";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = etagereASupprimer.getSelectedItem().toString();;
				
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une bouteille à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "'";
					
					boolean status = MyConnexionInsertDeleteUpdate(requete);
					if(!(status)){
						JOptionPane.showMessageDialog(null, "L'étagère de la position " + t[0] + " n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "L'étagère de la position " + t[0] + " a pas été supprimé dans la base de données", "Information", JOptionPane.INFORMATION_MESSAGE);
						etagereASupprimer.setSelectedItem("");
					}
				}
				MAJpanelModifierEtageres(etagereAModifier);
				MAJpanelSupprimerEtageres(etagereASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(supprimerEtageres, BorderLayout.SOUTH);
		
		panelSupprimerEtageres.add(nord, BorderLayout.CENTER);
	}
	
	public void createPanelBouteilles(){
		panelBouteilles = new JPanel();
		panelBouteilles.setLayout(new BorderLayout());
		
		
		createPanelAjoutBouteilles();
		createPanelModifBouteilles();
		createPanelSupprimerBouteilles();
		
		tabbedPaneModifBouteilles = new JTabbedPane();
		tabbedPaneModifBouteilles.addTab("Ajouter une bouteille", panelAjoutBouteilles);
		tabbedPaneModifBouteilles.addTab("Modifier une bouteille", panelModifBouteilles);
		tabbedPaneModifBouteilles.addTab("Supprimer une bouteille", panelSupprimerBouteilles);
		
		panelBouteilles.add(tabbedPaneModifBouteilles, BorderLayout.CENTER);	
	}
	
	public void createPanelAjoutBouteilles(){
		panelAjoutBouteilles = new JPanel();
		panelAjoutBouteilles.setLayout(new BorderLayout());

		JPanel nord = new JPanel();
		nord.setLayout(new GridLayout(2,1));
		
		JPanel innerNord = new JPanel();
		innerNord.setLayout(new GridLayout(1,2));
		
		innerNord.add(new JLabel("Volume de la bouteille (en L): "));
		final JTextField capacity = new JTextField();
		innerNord.add(capacity);
		nord.add(innerNord);
		
		JButton addBouteille = new JButton("Ajouter une bouteille");
		
		addBouteille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "INSERT INTO bouteille(taille) VALUES (\'";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = capacity.getText();
				
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					capacity.setText("");
				}
				else{
					requete += t[0] + "\')";
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litres n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						capacity.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a été ajoutée dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						capacity.setText("");
					}
				}
				MAJpanelModifierBouteilles(bouteilleAModifier);
				MAJpanelSupprimerBouteilles(bouteilleASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(addBouteille);
		
		panelAjoutBouteilles.add(nord,BorderLayout.NORTH);
		
	}
	
	public void createPanelModifBouteilles(){
		
		panelModifBouteilles = new JPanel();
		panelModifBouteilles.setLayout(new BorderLayout());
		
		
		final JPanel bottomBouteilles = new JPanel();
		bottomBouteilles.setLayout(new GridLayout(3,2));
		
		final JTextField idBouteille = new JTextField();
		final JTextField capacityBottle = new JTextField();
		
		bottomBouteilles.add(new JLabel("Id Bouteille : "));
		bottomBouteilles.add(idBouteille);
		idBouteille.setEditable(false);
		
		bottomBouteilles.add(new JLabel("Capacité en Litre(s) : "));
		bottomBouteilles.add(capacityBottle);
		
		JButton updateDataBottle = new JButton("Valider les données\n");
		bottomBouteilles.add(updateDataBottle);
		
		bottomBouteilles.setVisible(false);
		
		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,0));
		
		creerListeBouteille(bouteilleAModifier);
		inner.add(new JLabel("Choisir la capacité à modifier (en Litre): \n"));
		inner.add(bouteilleAModifier);
		nord.add(inner, BorderLayout.NORTH);
		
		JButton modifierBouteilles = new JButton("Modifier la bouteille sélectionnée");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		modifierBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "SELECT * FROM bouteille WHERE taille=\'";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = bouteilleAModifier.getSelectedItem().toString();;
				// FLOTTANT marche seulement avec 1 chiffre après la virgule.
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une bouteille à modifier.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "\'";
					ResultSet statut = MyConnexionSelect(requete);
					
						try {
							if(statut.first()){
								idBouteille.setText(statut.getString("identifiantBouteille"));
								capacityBottle.setText(statut.getString("taille"));
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas été sélectionner dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleAModifier.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
				}
				bottomBouteilles.setVisible(true);;
			}
		});
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
				updateDataBottle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						String requete = "UPDATE bouteille SET ";
						// On récupère les arguments.
						String [] t = new String[2];
						t[0] = idBouteille.getText();
						t[1] = capacityBottle.getText();
						
						// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
						if(t[1].isEmpty()){
							JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							requete += "taille=\'"+ t[1] + "\'";
							requete += " WHERE identifiantBouteille=" + t[0];
							
							boolean statut = MyConnexionInsertDeleteUpdate(requete);
							if(!statut){
								JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "La bouteille d'identifiant " + t[0] + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
								bottomBouteilles.setVisible(false);
							}
						}
						MAJpanelModifierBouteilles(bouteilleAModifier);
						MAJpanelSupprimerBouteilles(bouteilleASupprimer);
					}
				});
		nord.add(modifierBouteilles, BorderLayout.SOUTH);
		
		panelModifBouteilles.add(nord, BorderLayout.NORTH);
		panelModifBouteilles.add(bottomBouteilles, BorderLayout.SOUTH);
		
	}

	public void createPanelSupprimerBouteilles(){
		panelSupprimerBouteilles = new JPanel();
		
		panelSupprimerBouteilles.setLayout( new BorderLayout() );

		JPanel nord = new JPanel();
		nord.setLayout(new GridLayout(2,0));
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,2));
		
		creerListeBouteille(bouteilleASupprimer);
		
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Bouteille(s) à supprimer:"));
		inner.add(bouteilleASupprimer);
		
		nord.add(inner);
		
		JButton supprimerBouteilles = new JButton("Supprimer la bouteille sélectionnée.");
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		supprimerBouteilles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "DELETE FROM bouteille WHERE taille=\'";
				String requeteSelect = "SELECT * FROM bouteille WHERE taille=\'";
				String idBottle = null;
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = bouteilleASupprimer.getSelectedItem().toString();;
				
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner une bouteille à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "\'";
					requeteSelect += t[0] + "\'";
					
					ResultSet statut = MyConnexionSelect(requeteSelect);
						try {
							if(statut.first()){
								idBottle = statut.getString("identifiantBouteille");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						if(idBottle != null)
						{
							requete += " AND identifiantBouteille=" + idBottle;
							boolean status = MyConnexionInsertDeleteUpdate(requete);
							if(!(status)){
								JOptionPane.showMessageDialog(null, "La bouteille de " + t[0] + " litre n'a pas été supprimé dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[0] + " litre a pas été supprimé dans la base de données", "Information", JOptionPane.INFORMATION_MESSAGE);
								bouteilleASupprimer.setSelectedItem("");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Aucune bouteille n'a pas été supprimé dans la base de données", "Information", JOptionPane.INFORMATION_MESSAGE);
							bouteilleASupprimer.setSelectedItem("");
						}
				}
				MAJpanelModifierBouteilles(bouteilleAModifier);
				MAJpanelSupprimerBouteilles(bouteilleASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(supprimerBouteilles, BorderLayout.SOUTH);
		
		panelSupprimerBouteilles.add(nord, BorderLayout.CENTER);
	}

	
	public void createPanelVins(){
		panelVins = new JPanel();
		panelVins.setLayout(new BorderLayout());
		
		createPanelAjoutVins();
		createPanelModifVins();
		createPanelSupprimerVins();
		
		tabbedPaneModifVins = new JTabbedPane();
		tabbedPaneModifVins.addTab("Ajouter un vins", panelAjoutVins);
		tabbedPaneModifVins.addTab("Modifier un vins", panelModifVins);
		tabbedPaneModifVins.addTab("Supprimer un vins", panelSupprimerVins);
		
		panelVins.add(tabbedPaneModifVins, BorderLayout.CENTER);
	}
	
	public void createPanelAjoutVins(){
		panelAjoutVins = new JPanel();
		panelAjoutVins.setLayout(new BorderLayout());

		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(6,2));
		
		regionVins = new JTextField();
		domaineVins = new JTextField();
		châteauVins = new JTextField();
		couleurVins = new JComboBox<String>();
		cepageVins = new JTextField();
		dateVins = new JTextField();
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		regionVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le label et le JTextFiel au panel innerTop (2ème ligne)
		inner.add(new JLabel("Region du vin :"));
		inner.add(regionVins);
				
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		domaineVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le label et le JTextFiel au panel innerTop (3ème ligne)
		inner.add(new JLabel("Domaine du vin :"));
		inner.add(domaineVins);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		châteauVins.setPreferredSize(new Dimension(200,30));
		// On ajoute le label et le JTextFiel au panel innerTop (4ème ligne)
		inner.add(new JLabel("Château du vin :"));
		inner.add(châteauVins);
		
		// On met la taille du JComboBox à 200px de long et 30 px de hauteur
		couleurVins.setPreferredSize(new Dimension(200,30));
		couleurVins.addItem("");
		couleurVins.addItem("rouge");
		couleurVins.addItem("blanc");
		couleurVins.addItem("rosé");
		// On ajoute le label et le JComboBox au panel innerTop (5ème ligne)
		inner.add(new JLabel("Couleur du vin :"));
		inner.add(couleurVins);
				
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		cepageVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le label et le JTextFiel au panel innerTop (6ème ligne)
		inner.add(new JLabel("Cépage du vin :"));
		inner.add(cepageVins);
		
		// On met la taille du JTextField à 200px de long et 30 px de hauteur
		dateVins.setPreferredSize(new Dimension(200,100));
		// On ajoute le label et le JTextFiel au panel innerTop (3ème ligne)
		inner.add(new JLabel("Année du vin :"));
		inner.add(dateVins);
				
		nord.add(inner, BorderLayout.NORTH);
		
		JButton ajouterVins = new JButton("Ajouter le vins");
		
		ajouterVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "INSERT INTO vins(domaineVin, regionVin, châteauVin, couleur, cepageVin,dateVin) VALUES (";
				// On récupère les arguments.
				String [] t = new String[6];
				t[0] = domaineVins.getText();
				t[1] = regionVins.getText();
				t[2] = châteauVins.getText();
				t[3] = couleurVins.getSelectedItem().toString();
				t[4] = cepageVins.getText();
				t[5] = dateVins.getText();
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("") || t[1].equals("") || t[2].equals("") || t[3].equals("") || t[4].equals("") || t[5].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs demandés.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					dateVins.setText("");
				}
				else{
					if(t[5].length() != 4){
						JOptionPane.showMessageDialog(null, "Veuillez renseigner une date cohérante.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						requete += "'" + t[0] + "','"+ t[1] + "','" + t[2] + "','" + t[3] + "','" + t[4] + "'," + t[5] + ")";
						boolean statut = MyConnexionInsertDeleteUpdate(requete);
						if(!(statut)){
							JOptionPane.showMessageDialog(null, "Le vin " + t[4] + " n'a pas été ajoutée dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							domaineVins.setText("");
							regionVins.setText("");
							châteauVins.setText("");
							couleurVins.setSelectedItem("");;
							cepageVins.setText("");
							dateVins.setText("");
						}
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
				}
				MAJpanelModifierVins(vinAModifier);
				MAJpanelSupprimerVins(vinASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(ajouterVins, BorderLayout.SOUTH);
		
		panelAjoutVins.add(nord,BorderLayout.CENTER);
		
	}
	
	public void createPanelModifVins(){
		panelModifVins = new JPanel();
		panelModifVins.setLayout(new BorderLayout());
		
		
		final JPanel bottomVins = new JPanel();
		bottomVins.setLayout(new GridLayout(7,2));
		
		final JTextField regionVinsM = new JTextField();
		final JTextField domaineVinsM = new JTextField();
		final JTextField châteauVinsM = new JTextField();
		final JComboBox<String> couleurVinsM = new JComboBox<String>();
		couleurVinsM.addItem("blanc");
		couleurVinsM.addItem("rose");
		couleurVinsM.addItem("rouge");
		final JTextField cepageVinsM = new JTextField();
		final JTextField dateVinsM = new JTextField();
		
		bottomVins.add(new JLabel("Region : "));
		bottomVins.add(regionVinsM);
		bottomVins.add(new JLabel("Domaine : "));
		bottomVins.add(domaineVinsM);
		bottomVins.add(new JLabel("Château : "));
		bottomVins.add(châteauVinsM);
		bottomVins.add(new JLabel("Couleur : "));
		bottomVins.add(couleurVinsM);
		bottomVins.add(new JLabel("Cépage : "));
		bottomVins.add(cepageVinsM);
		bottomVins.add(new JLabel("Année : "));
		bottomVins.add(dateVinsM);
		
		JButton updateDataVins = new JButton("Valider les données.\n");
		bottomVins.add(updateDataVins);
		
		bottomVins.setVisible(false);
		
		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,0));
		
		creerListeVin(vinAModifier);
		inner.add(new JLabel("Choisir le vin à modifier.\n"));
		inner.add(vinAModifier);
		nord.add(inner, BorderLayout.NORTH);
		
		JButton modifierVins = new JButton("Modifier le vin sélectionné.");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		modifierVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "SELECT * FROM vins WHERE identifiantVin=";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = vinAModifier.getSelectedItem().toString();;
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].isEmpty()){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un vin à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0];
					
					ResultSet statut = MyConnexionSelect(requete);
					try {
						if(!statut.isBeforeFirst()){
							JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " n'a pas été sélectionner dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							vinAModifier.setSelectedItem("");
						}
						else
						{
							while(statut.next()){
								vinCourant = t[0];
								regionVinsM.setText(statut.getString("regionVin"));
								domaineVinsM.setText(statut.getString("domaineVin"));
								châteauVinsM.setText(statut.getString("châteauVin"));
								couleurVinsM.setSelectedItem(statut.getString("couleur"));
								cepageVinsM.setText(statut.getString("cepageVin"));
								dateVinsM.setText(statut.getString("dateVin"));
							}
						}
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				MAJpanelModifierVins(vinAModifier);
				MAJpanelSupprimerVins(vinASupprimer);
				bottomVins.setVisible(true);;
			}
		});
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
				updateDataVins.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						String requete = "UPDATE cave.vins SET ";
						// On récupère les arguments.
						String [] t = new String[6];
						t[0] = regionVinsM.getText();
						t[1] = domaineVinsM.getText();
						t[2] = châteauVinsM.getText();
						t[3] = couleurVinsM.getSelectedItem().toString();
						t[4] = cepageVinsM.getText();
						t[5] = dateVinsM.getText();
						
						// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
						if(t[0].isEmpty() || t[1].isEmpty() || t[2].isEmpty() || t[3].isEmpty() || t[4].isEmpty() || t[5].isEmpty()){
							JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les arguments.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						else{
							if(t[5].length() != 4){
								JOptionPane.showMessageDialog(null, "Veuillez renseigner une année valide.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								requete += "regionVin='"+ t[0] + "', domaineVin='" + t[1] + "', châteauVin='" + t[2] + "', couleur='" + t[3] + "', cepageVin='" + t[4] + "', dateVin=" + t[5];
								requete += " WHERE identifiantVin=" + vinCourant;
								
								boolean statut = MyConnexionInsertDeleteUpdate(requete);
								if(!statut){
									JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " n'a pas été modifié dans la base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + vinCourant + " a été modifié dans la base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
									bottomVins.setVisible(false);
								}
							}
						}
						MAJpanelModifierVins(vinAModifier);
						MAJpanelSupprimerVins(vinASupprimer);
					}
				});
		nord.add(modifierVins, BorderLayout.SOUTH);
		
		panelModifVins.add(nord, BorderLayout.NORTH);
		panelModifVins.add(bottomVins, BorderLayout.SOUTH);
		
	}

	public void createPanelSupprimerVins(){
		panelSupprimerVins = new JPanel();
		
		panelSupprimerVins.setLayout( new BorderLayout() );

		JPanel nord = new JPanel();
		nord.setLayout(new BorderLayout());
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(1,2));
		
		vinASupprimer = new JComboBox<String>();
		
		creerListeVin(vinASupprimer);
		
		// On ajoute le label et le JTextFiel au panel innerTop (1ère ligne)
		inner.add(new JLabel("Vin à supprimer:"));
		inner.add(vinASupprimer);
		
		nord.add(inner, BorderLayout.NORTH);
		
		JButton supprimerVins = new JButton("Supprimer le vin sélectionné.");
		// On lui attribue un écouteur (c'est ici que l'on gérera si la recherche se fait par nom,couleur, ou année.)
		
		supprimerVins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requete = "DELETE FROM vins WHERE identifiantVin='";
				// On récupère les arguments.
				String [] t = new String[1];
				t[0] = vinASupprimer.getSelectedItem().toString();;
				
				// Si c'est une recherche par année on fait la requête et on affiche le panel bottom des résultats.
				if(t[0].equals("")){
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un vin à supprimer.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else{
					requete += t[0] + "'";
					boolean statut = MyConnexionInsertDeleteUpdate(requete);
					if(!(statut)){
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + "n'a pas été supprimée de votre base de données.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Le vin d'identifiant " + t[0] + " a été supprimée de votre base de données.\n", "Information", JOptionPane.INFORMATION_MESSAGE);
						vinASupprimer.setSelectedItem("");
					}
				}
				MAJpanelModifierVins(vinAModifier);
				MAJpanelSupprimerVins(vinASupprimer);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(supprimerVins, BorderLayout.SOUTH);
		
		panelSupprimerVins.add(nord, BorderLayout.CENTER);
	}

	public void createPanelRangement(){
		panelRangement = new JPanel();
		panelRangement.setLayout(new BorderLayout());

		JPanel nord = new JPanel();
		nord.setLayout(new GridLayout(2,0));
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(4,2));
		
		creerListeEtagere(listeetagere);
		inner.add(new JLabel("Veuillez selectionner une étagère"));
		inner.add(listeetagere);
		
		creerListeBouteilleRangement(listebouteille);
		inner.add(new JLabel("Veuillez selectionner une bouteille"));
		inner.add(listebouteille);
		
		creerListeVin(listevin);
		inner.add(new JLabel("Veuillez selectionner un vin"));
		inner.add(listevin);
		
		
		nord.add(inner);
		
		JButton ajouterRangement = new JButton("Ranger le vins");
		
		ajouterRangement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String requeteInsert = "INSERT INTO bouteille_has_vins(Bouteille_identifiantBouteille, Vins_identifiantVin, Etagère_identifiantEtagère) VALUES (";
				String requeteSelectBottle = "SELECT * FROM bouteille WHERE taille=\'";
				String requeteSelectEtagere = "SELECT * FROM etagère WHERE positionEtagère='";
				String idBottle = null;
				String idEtagere = null;
				
				// On récupère les arguments.
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
					requeteSelectBottle += t[1] + "\'";
					requeteSelectEtagere += t[0] + "\'";
					
					ResultSet statutBottle = MyConnexionSelect(requeteSelectBottle);
					ResultSet statutEtagere = MyConnexionSelect(requeteSelectEtagere);
						try {
							if(statutBottle.first()){
								idBottle = statutBottle.getString("identifiantBouteille");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Une bouteille de " + t[1] + " litre n'a pas été selectionné dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								listebouteille.setSelectedItem("");
							}
							if(statutEtagere.first()){
								idEtagere = statutEtagere.getString("identifiantEtagère");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "L'étagère à la position " + t[0] + " n'a pas été selectionné dans la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
							}
							
						} catch (HeadlessException | SQLException e) {
							e.printStackTrace();
						}
						
						if(idBottle != null && idEtagere != null)
						{
							requeteInsert += idBottle + "," + t[2] + "," + idEtagere + ")";
							boolean status = MyConnexionInsertDeleteUpdate(requeteInsert);
							if(!(status)){
								JOptionPane.showMessageDialog(null, "Il y a eu une erreur", "Erreur", JOptionPane.ERROR_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Le vin à été rangé", "Information", JOptionPane.INFORMATION_MESSAGE);
								listeetagere.setSelectedItem("");
								listebouteille.setSelectedItem("");
								listevin.setSelectedItem("");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "L'étagère ou la bouteille n'a pas pu être sélectionnée", "Information", JOptionPane.INFORMATION_MESSAGE);
							listeetagere.setSelectedItem("");
							listebouteille.setSelectedItem("");
							listevin.setSelectedItem("");
						}
				}
				MAJpanelRangement(listeetagere, listebouteille, listevin);
			}
		});
		
		// On ajoute le bouton Search au panel top à la position SUD pour avoir les éléments de recherche et le bouton rechercher en dessous 
		nord.add(ajouterRangement, BorderLayout.SOUTH);
		
		panelRangement.add(nord,BorderLayout.CENTER);
	}
	
	private ResultSet MyConnexionSelect(String requete) {
    	
    	// Resultat de la requête.
        ResultSet s = null;
        
        // Mise en forme de la requête vue que nous enregistrons les mots de passe en encodant en MD5
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

	private boolean MyConnexionInsertDeleteUpdate(String requete) {
    	// Mise en forme de la requête vue que nous enregistrons les mots de passe en encodant en MD5
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Connexion à la base de données
            conn = DriverManager.getConnection(url, log, pwd);
           
            // Création et exécution de la requête.
            Statement statement = (Statement) conn.createStatement();
            statement.executeUpdate(requete);
           return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    	// On retourne le résultat de la requête pour pouvoir faire le traitement en conséquence.
        return false;
	}
	
	public void rafraichirDataRecherche(JPanel p, JTable o){
		p.removeAll();
		if(o == null){
			p.setVisible(false);
		}
		else
		{
			p.add(o.getTableHeader(), BorderLayout.NORTH);
			p.add(o, BorderLayout.CENTER);
		}
	}
	
	public void MAJpanelModificationCave(JComboBox<String> p){
		p.removeAllItems();
		creerListeCave(p);
	}
	
	public void MAJpanelSuppressionCave(JComboBox<String> p){
		p.removeAllItems();
		creerListeCave(p);
	}
	
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
	
	public void MAJpanelModifierVins(JComboBox<String> p){
		p.removeAllItems();
		creerListeVin(p);
	}
	
	public void MAJpanelSupprimerVins(JComboBox<String> p){
		p.removeAllItems();
		creerListeVin(p);
	}
	
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
	
	
	public void MAJpanelModifierBouteilles(JComboBox<String> p){
		p.removeAllItems();
		creerListeBouteille(p);
	}
	
	public void MAJpanelSupprimerBouteilles(JComboBox<String> p){
		p.removeAllItems();
		creerListeBouteille(p);
	}
	
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
	
	public void MAJpanelModifierEtageres(JComboBox<String> p){
		p.removeAllItems();
		creerListeEtagere(p);
	}
	
	public void MAJpanelSupprimerEtageres(JComboBox<String> p){
		p.removeAllItems();
		creerListeEtagere(p);
	}
	
	public void creerListeEtagere(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		String req = "SELECT * FROM etagère WHERE CaveAVin_Utilisateur_identifiantUtilisateur = " + idAdmin + " AND CaveAVin_nomCave = '" + caveAVinCourante + "'";
		ResultSet statement = MyConnexionSelect(req);
		try {
			while(statement.next()){
				String posEtagereRes =statement.getString("positionEtagère");
				c.addItem(posEtagereRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void MAJpanelRangement(JComboBox<String> le, JComboBox<String> lb, JComboBox<String> lv){
		le.removeAllItems();
		creerListeEtagere(le);
		lb.removeAllItems();
		creerListeBouteilleRangement(lb);
		lv.removeAllItems();
		creerListeVin(lv);
		
	}
	
	public void creerListeBouteilleRangement(JComboBox<String> c){
		c.setPreferredSize(new Dimension(200,30));
		c.addItem("");
		// Requete pour ne selectionner que les bouteilles non utilisées.  
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
		
	public static void main(String[] args) {
		FenetreAdmin fa = new FenetreAdmin("1");
		fa.pack();
		fa.setLocationRelativeTo(null);
		fa.setVisible(true);
	}
}

