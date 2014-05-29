public class LogicielCaveAVin{
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