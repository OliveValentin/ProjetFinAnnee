// Import pour l'affichage du JFrame et des JPanel
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

//Import pour les �couteurs des boutons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Import pour les requ�te faites � la base de donn�es et pour la gestion des Exceptions
import java.sql.ResultSet;

//Import pour les �l�ments du JFrame
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

	// Champs n�cessaire pour la recherche de vins
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Ann�e");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	
	private InteractionBDD bdd = new InteractionBDD();
	
	// Champ pour le r�sultat de la recherche
	JTable tableau;
		
    // Constructeur par d�faut de la fen�tre de Client
   	public FenetreClient() {
   		// On appel le constructeur de JFrame avec un param�tre qui est le nom de la fen�tre
   		super("La cave � vin - Section clients");
   		// Ensuite on initialise cette fen�tre
   		init();
   	}
	
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// On instancie les champs de notre fenetre (JTextField pour lla recherche par ann�e et pour celle par nom et JComboBox<String> pour la recherche par couleur)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On cr�� le panel principal qui contiendra tous ce qui constitue notre fen�tre
		JPanel panel = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		panel.setLayout(new BorderLayout());
		// On met la dimension du panel �la taille de l'�cran
		panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

		
		// On cr�� le panel toptop qui contiendra le bouton se connecter
		JPanel toptop = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		toptop.setLayout(new BorderLayout());
				
		// On cr�� un bouton Se connecter que l'on ajoute � l'est du panel toptop
		JButton connexion = new JButton("Se connecter");
		toptop.add(connexion, BorderLayout.EAST);
		
		// On lui attribue un �couteur
		connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// On cr�� une instance de FenetreConnexion qui est la fen�tre de connexion.
				FenetreConnexion c = new FenetreConnexion();
				c.pack();
				// On place la fen�tre au centre de l'�cran
			    c.setLocationRelativeTo(null);
			    // On la rend visible
			    c.setVisible(true);
			}
		});
		
		// On cr�� le panel top qui contiendra le panel toptop et le panel innerTop
		JPanel top = new JPanel();
		// On lui dit que l'affichage des champs se par r�gion
		top.setLayout(new BorderLayout());
		
		// On lui ajoute le panel toptop au nord
		top.add(toptop, BorderLayout.NORTH);
		
		// On cr�� le panel innerTop qui contiendra les champs de recherche de vins
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par ann�e mais vous n'avez pas renseign� de valeur\nVeuillez en renseigner une", "Erreur", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par nom mais vous n'avez pas renseign� de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Vous avez cocher la recherche par couleur mais vous n'avez pas renseign� de valeur. Veuillez en renseigner une.\n", "Erreur", JOptionPane.ERROR_MESSAGE);
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
				ResultSet resultRequete = bdd.MyConnexionSelect(requete);
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
		
		// On ajoute le panel principal � la fen�tre
		getContentPane().add(panel);
	}

	// Fonction qui nous permet d'actualiser l'affichage du r�sultat de la requ�te
	public void rafraichirData(JPanel p, JTable o){
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
}
