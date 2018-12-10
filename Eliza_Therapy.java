package finalProj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Eliza_Therapy extends JFrame implements ActionListener{

	private TextFileHandler tfHandler = new TextFileHandler();
	private JButton jbStartSession, jbNextQuestion, jbFinishSession, jbNewSession, jbViewTxtFile, jbDeleteFile;
	private JTextField answersArea;
	private JTextArea jta;
	private static JLabel readMe;
	private int questionCounter = 0;
	private Font font;
	private Font font2;
	private QuestionBank qb;
	private static int sessionNum = 1;
	private String shortest = "";
	private String largest = "";
	
	public Eliza_Therapy() {
		qb = new QuestionBank();
		this.setTitle("Eliza Therapy Session");
		font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		font2 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		JPanel mainP = new JPanel();
		mainP.setBackground(Color.LIGHT_GRAY);
		readMe = new JLabel("WELCOME TO ELIZA SESSION " + sessionNum);
		readMe.setFont(font);
		answersArea = new JTextField("Type answer here after reading a question. Click Start Session ->");
		answersArea.setFont(font);
		answersArea.setLayout(new GridLayout(1,1));
		answersArea.setSize(640,108);
		jta = new JTextArea();
		jta.setFont(font2);
		jta.setText("Results appear here");
		jta.setEditable(false);
		answersArea.setEnabled(true);
		
		jbStartSession = new JButton("Start Session");
		jbStartSession.addActionListener(this);
		jbStartSession.setEnabled(true);
		
		jbNextQuestion = new JButton("Next Question");
		jbNextQuestion.addActionListener(this);
		jbNextQuestion.setEnabled(false);
		
		jbFinishSession = new JButton("Finish Session");
		jbFinishSession.addActionListener(this);
		jbFinishSession.setEnabled(false);
		
		jbNewSession = new JButton("New Session");
		jbNewSession.addActionListener(this);
		jbNewSession.setEnabled(false);
		
		jbViewTxtFile = new JButton("View Results File");
		jbViewTxtFile.addActionListener(this);
		jbViewTxtFile.setEnabled(false);
		
		jbDeleteFile = new JButton("Delete file");
		jbDeleteFile.addActionListener(this);
		jbDeleteFile.setEnabled(false);
		
		
		mainP.add(BorderLayout.NORTH, readMe);
		mainP.add(BorderLayout.CENTER, answersArea);
		mainP.add(BorderLayout.NORTH, jbStartSession);
		mainP.add(BorderLayout.NORTH, jbNextQuestion);
		mainP.add(BorderLayout.NORTH, jbFinishSession);
		mainP.add(BorderLayout.NORTH, jbNewSession);
		mainP.add(BorderLayout.NORTH, jbViewTxtFile);
		mainP.add(BorderLayout.NORTH, jbDeleteFile);
		mainP.add(BorderLayout.SOUTH, jta);
		add(mainP);
		mainP.setSize(1440,900);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent brain) {
		JButton clicked = (JButton) brain.getSource();
		if(clicked.equals(jbStartSession)) {
			jbStartSession.setEnabled(false);
			jbNextQuestion.setEnabled(true);
			questionCounter++;
			readMe.setText("Question " + questionCounter + ": " + qb.getNextQuestion());
			tfHandler.createNewFile("Elizalog.txt");
		}
		else if(clicked.equals(jbNextQuestion)) {
			tfHandler.appendToFile("Elizalog.txt", readMe.getText());
			tfHandler.appendToFile("Elizalog.txt", answersArea.getText());
			String answer = "";
			String[] answerArray = {""};
			answer = answersArea.getText();
			answerArray = answer.split(" ");
			tfHandler.sort(answerArray);
			//use length compare to find smaller and bigger
			if(questionCounter < 11) {
				if(answerArray[0].length() < shortest.length() || shortest == "") {
				shortest = answerArray[0];
				}
				if(answerArray[answerArray.length - 1].length() > largest.length()) {
				largest = answerArray[answerArray.length - 1];
				}
			}
			tfHandler.appendToFile("Elizalog.txt", ("Longest Word in this answer: " + answerArray[answerArray.length - 1]));
			questionCounter++;
			if(questionCounter < 11) {
			readMe.setText("Question " + questionCounter + ": " + qb.getNextQuestion());
			}
			else if(questionCounter >= 11) {
				readMe.setText("All done!");
				tfHandler.appendToFile("Elizalog.txt", "Analysis for session " + sessionNum + ": It seems that " + shortest + " and " + largest + " are important to you.");
				jbNextQuestion.setEnabled(false);
				jbViewTxtFile.setEnabled(true);
			}
		}
		else if(clicked.equals(jbFinishSession)) {
			System.exit(0);
		}
		else if(clicked.equals(jbNewSession)) {
			tfHandler.createNewFile("Elizalog.txt");
			readMe.setText("ELIZA SESSION " + (sessionNum + 1));
			tfHandler.appendToFile("Elizalog.txt", readMe.getText());
			jbStartSession.setEnabled(true);
			jbNextQuestion.setEnabled(false);
			jbFinishSession.setEnabled(false);
			jbNewSession.setEnabled(false);
			jbViewTxtFile.setEnabled(false);
			jbDeleteFile.setEnabled(false);
			jta.setText("");
			shortest = "";
			largest = "";
			questionCounter = 0;
			for(int i = 0; i < 11; i++) {
				qb.taken[i] = 0;
			}
		}
		else if(clicked.equals(jbViewTxtFile)) {
			jta.setText(tfHandler.readFile("Elizalog.txt"));
			jbViewTxtFile.setEnabled(false);
			jbNewSession.setEnabled(true);
			jbFinishSession.setEnabled(true);
			jbDeleteFile.setEnabled(true);
		}
		else if(clicked.equals(jbDeleteFile)) {
			tfHandler.deleteFile("Elizalog.txt");
		}
	}
	public class QuestionBank {
		private String [] questions;
	    private int questionGenerate;
	    private  int[] taken;
	    public static final int NUM_QUESTIONS = 11; 
	    
	    public QuestionBank(){
	        questions = new String [NUM_QUESTIONS];
	        questionGenerate = 0;
	        taken = new int[11];
	        populateQuestionArray();
	    }
	    public void populateQuestionArray() {
	    	   	questions[0] = "";
		        questions[1] = "Which is your best feature?";
		        questions[2] = "Which common saying or phrase describes you?";
		        questions[3] = "What’s the best thing that’s happened to you this week?";
		        questions[4] = "Who was your role model when you were a child?";
		        questions[5] = "Who was your favorite teacher and why?";
		        questions[6] = "What was your favorite subject at school?";
		        questions[7] = "What did you want to be when you grew up?";
		        questions[8] = "If you could have one wish come true what would it be?";
		        questions[9] = "Which would you prefer — three wishes over five years or one wish right now?";
		        questions[10] = "Which three words describe you best?";
		        taken[0] = 0;
		        taken[1] = 0;
		        taken[2] = 0;
		        taken[3] = 0;
		        taken[4] = 0;
		        taken[5] = 0;
		        taken[6] = 0;
		        taken[7] = 0;
		        taken[8] = 0;
		        taken[9] = 0;
		        taken[10] = 0;
		        
	    }
	    

	    public String getNextQuestion() {
	        questionGenerate = (int)(Math.random() * 11);
	        	while(questionGenerate == taken[questionGenerate]) {
	        		questionGenerate = (int)(Math.random() * (questions.length));
	        		if(questionGenerate != taken[questionGenerate]) {
	        			break;
	        		}
	        	}
	        	taken[questionGenerate] = questionGenerate;
	        	return questions[questionGenerate];
	    }

	}
	

}
