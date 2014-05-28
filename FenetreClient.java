import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;



public class FenetreClient extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField rechercherannee;
	private JTextField recherchernom;
	private JComboBox<String> recherchercouleur;
	private JCheckBox checkannee = new JCheckBox("Année");
	private JCheckBox checknom = new JCheckBox("Nom");
	private JCheckBox checkcouleur = new JCheckBox("Couleur");
	
	JTable tableau;
	
	private static Connection conn;
    public static String url = "jdbc:mysql://localhost/cave";
    public static String pwd="";
    public static String log="root";
    
	
	public FenetreClient() {
		super("La cave à vin");
		init();
	}
	
	
	private void init(){
		// On dit que lorsque l'on clique sur la croix rouge on quitte
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// On instancie les champs de notre fenetre (JTextField pour le login et JPasswordField pour le password)
		rechercherannee = new JTextField();
		recherchernom = new JTextField();
		recherchercouleur = new JComboBox<String>();
		
		// On créé le panel principal celui qui contiendra tous ce qui constitue notre fenetre
		JPanel panel = new JPanel();
		// On lui dit que c'est une grille avec 0 ligne et 2 colonnes
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

		
		JPanel toptop = new JPanel();
		toptop.setLayout(new BorderLayout());
		
		// On cree un bouton Valider
		JButton connexion = new JButton("Se connecter");
		toptop.add(connexion, BorderLayout.EAST);
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si c'est un client ou un marchand de vin)
		connexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FenetreConnexion c = new FenetreConnexion();
				c.pack();
			    c.setLocationRelativeTo(null);
			    c.setVisible(true);
			}
		});
		
		
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(toptop, BorderLayout.NORTH);
		
		JPanel innerTop = new JPanel();
		innerTop.setLayout(new GridLayout(3,3));
		
		
		rechercherannee.setPreferredSize(new Dimension(200,30));
		innerTop.add(new JLabel("Année à rechercher :"));
		innerTop.add(rechercherannee);
		innerTop.add(checkannee);
		
		recherchernom.setPreferredSize(new Dimension(200,30));
		innerTop.add(new JLabel("Nom à rechercher :"));
		innerTop.add(recherchernom);
		innerTop.add(checknom);
		
		recherchercouleur.setPreferredSize(new Dimension(200,30));
		recherchercouleur.addItem("");
		recherchercouleur.addItem("blanc");
		recherchercouleur.addItem("rose");
		recherchercouleur.addItem("rouge");
		innerTop.add(new JLabel("Couleur à rechercher :"));
		innerTop.add(recherchercouleur);
		innerTop.add(checkcouleur);
		
		
		top.add(innerTop, BorderLayout.CENTER);
		
		
		final JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setVisible(false);
		
		// On cree un bouton Valider
		JButton searchButton = new JButton("Rechercher");
		
		// On lui attribue un écouteur (c'est ici que l'on gérera si c'est un client ou un marchand de vin)
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String [] t = new String[3];
				String requete = "SELECT * FROM `vins` WHERE";
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
				ResultSet resultRequete = MyConnexionRecherche(requete);
				try {
					bottom.setVisible(false);
					if(!(resultRequete.isBeforeFirst())){
						bottom.add(new JLabel("Aucun vin ne correspond a votre recherche.\n"), BorderLayout.NORTH);
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
						// problème synchronisation de l'affichage
					}
					rafraichirData(bottom, tableau);
					bottom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// On ajoute le bouton Search au panel principal
		top.add(searchButton, BorderLayout.SOUTH);
		
		panel.add(top, BorderLayout.NORTH);

		panel.add(bottom, BorderLayout.CENTER);
		// On ajoute à la fenetre le panel principal
		getContentPane().add(panel);
	}
	
private ResultSet MyConnexionRecherche(String requete) {
    	
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

	public void rafraichirData(JPanel p, JTable o){
		p.removeAll();
		p.add(o.getTableHeader(), BorderLayout.NORTH);
		p.add(o, BorderLayout.CENTER);
		
	}

		// Class main qui affiche la fenetre
		public static void main(String[] args) {
			FenetreClient demo = new FenetreClient();
			demo.pack();
		    demo.setLocationRelativeTo(null);
		    demo.setVisible(true);	
		}
}