public class LogicielCaveAVin{
	// Class main qui cr�� et affiche la fen�tre principale de notre application
			public static void main(String[] args) {
				// On cr�� une instance de FenetreClient qui est la fen�tre des clients.
				FenetreClient fc = new FenetreClient();
				fc.pack();
				// On place la fen�tre au centre de l'�cran
				fc.setLocationRelativeTo(null);
				// On la rend visible
				fc.setVisible(true);
						
			}
}