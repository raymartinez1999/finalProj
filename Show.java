package finalProj;

public class Show {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		
			@Override
			public void run() {
				Eliza_Therapy ET_GUI = new Eliza_Therapy();
			}
			
		});
	}
}
