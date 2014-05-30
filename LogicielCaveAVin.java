public class LogicielCaveAVin{
	// Class main qui cree et affiche la fenetre principale de notre application
			public static void main(String[] args) {
				// On cree une instance de FenetreClient qui est la fenetre des clients.
				FenetreClient fc = new FenetreClient();
				fc.pack();
				// On place la fenetre au centre de l'ecran
				fc.setLocationRelativeTo(null);
				// On la rend visible
				fc.setVisible(true);
						
			}
}