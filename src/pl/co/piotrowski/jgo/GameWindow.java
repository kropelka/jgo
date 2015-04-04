/**
 * @author Karol Piotrowski
 */
package pl.co.piotrowski.jgo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author Karol Piotrowski
 * Stan zajętości pola na planszy
 */
enum FieldState {
	EMPTY, WHITE, BLACK
}


/**
 * @author Karol Piotrowski
 * Rodzaj bitmapy na planszy
 */
enum FieldType {
	LEFT_UP_CORNER, LEFT_DOWN_CORNER, RIGHT_UP_CORNER, RIGHT_DOWN_CORNER, HOSHI, CROSSING, LEFT_EDGE, RIGHT_EDGE, UPPER_EDGE, LOWER_EDGE, WHITE_OCCUPIED,
	BLACK_OCCUPIED
}

/**
 * @author Karol Piotrowski
 * Ładowanie oraz udostępnianie plików graficznych niezbędnych dla klienta.
 */
class GobanGraphics {
	/**
	 * rozmiar "kafelka" na planszy
	 * TODO zrobić skalowalne przyciski
	 */
	static private int tileSize = 64;
	
	/**
	 * struktura danych przechowująca bitmapowe wersje "kafelków". 
	 */
	static EnumMap<FieldType, BufferedImage> fieldBitmap;
	
	static void init() {
		fieldBitmap = new EnumMap<FieldType, BufferedImage>(FieldType.class);
		for(FieldType fType : FieldType.values()) {
			try {
				fieldBitmap.put(fType, ImageIO.read(new File("img/" + fType.toString() + ".png")));
			} catch(IOException e) {
				System.err.println("Blad odczytu obrazka" + "img/" + fType.toString() + ".png");				
			}
		}
		
	}
	static ImageIcon getImageIcon(FieldType f) {
		return new ImageIcon(fieldBitmap.get(f).getScaledInstance(tileSize, tileSize, java.awt.Image.SCALE_SMOOTH));
	}
}


class GobanField {
	JButton fieldButton;
	FieldType type;
	int fieldNo;
	GobanField(int fieldNo, int boardSize) {
		this.fieldNo = fieldNo;
		type = this.calculateFieldType(fieldNo, boardSize);
		fieldButton = new JButton(GobanGraphics.getImageIcon(type));
		fieldButton.setDisabledIcon(fieldButton.getIcon());
		fieldButton.setBorder(BorderFactory.createEmptyBorder());
		
		// only for testing
		fieldButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println("Wcisnieto przycisk" + fieldNo);
				changeField(FieldState.BLACK);
			}
		});
		
	}
	private FieldType calculateFieldType(int fieldNo, int boardSize) {
		if(fieldNo == 0) {
			return FieldType.LEFT_UP_CORNER;
		};
		if(0 < fieldNo && fieldNo < boardSize - 1) {
			return FieldType.UPPER_EDGE;
		};
		if(boardSize*(boardSize - 1) < fieldNo && fieldNo < boardSize*boardSize - 1) {
			return FieldType.LOWER_EDGE;
		}
		if(fieldNo == boardSize - 1) {
			return FieldType.RIGHT_UP_CORNER;
		};
		if(fieldNo == boardSize*boardSize - 1) {
			return FieldType.RIGHT_DOWN_CORNER;
		};
		if(fieldNo == boardSize*(boardSize - 1)) {
			return FieldType.LEFT_DOWN_CORNER;
		};
		if(fieldNo % boardSize == 0) {
			return FieldType.LEFT_EDGE;
		};
		if(fieldNo % boardSize == boardSize - 1) {
			return FieldType.RIGHT_EDGE;
		};
		return FieldType.CROSSING;
	}
	JButton getFieldButton() {
		return fieldButton;
	}
	void changeField(FieldState state) {
		switch(state) {
		case EMPTY: 
			fieldButton.setIcon(GobanGraphics.getImageIcon(type));
			fieldButton.setEnabled(true);
			break;
		case BLACK:
			fieldButton.setIcon(GobanGraphics.getImageIcon(FieldType.BLACK_OCCUPIED));
			fieldButton.setEnabled(false);
			break;
		case WHITE:
			fieldButton.setIcon(GobanGraphics.getImageIcon(FieldType.WHITE_OCCUPIED));
			fieldButton.setEnabled(false);
		};					
		fieldButton.setDisabledIcon(fieldButton.getIcon());
	}
}


class Goban extends JPanel {
	public void disableInput() {
		this.setEnabled(false);
	}
	
	public void enableInput() {
		this.setEnabled(true);
	}
	
	public void putStone(FieldState state, int fieldNo) {
		fields[fieldNo].changeField(state);
	}
	
	public void removeStone(int fieldNo) {
		fields[fieldNo].changeField(FieldState.EMPTY);
	}
	
	GobanField[] fields;
	
	Goban(int boardSize) {
		super();
		this.setLayout(new GridLayout(boardSize, boardSize, 0, 0));
		fields = new GobanField[boardSize*boardSize];
		for(int j = 0; j < boardSize*boardSize; ++j) {
			fields[j] = new GobanField(j, boardSize);
			this.add(fields[j].getFieldButton());
		}
		
	}
}

class MovePane extends JPanel {
	JList movesBox;
	MovePane() {
		movesBox = new JList();
	} 
	
}


class ControlsPane extends JPanel {
	JLabel player1Label;
	JLabel player2Label;
	String player1Name;
	String player2Name;
	ControlsPane() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		player1Label = new JLabel("Gracz 1: " + player1Name);
		player2Label = new JLabel("Gracz 2: " + player2Name);
		this.add(player1Label);
		this.add(player2Label);
	}
}

class PlayerChat extends JPanel {
	JTextArea chatBox;
	JScrollPane scroll;
	JPanel panel;
	PlayerChat() {
		super();
		this.setLayout(new BorderLayout());
		chatBox = new JTextArea();
		chatBox.setRows(10);
		chatBox.setEditable(false);
		scroll = new JScrollPane(chatBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroll, BorderLayout.CENTER);
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
		chatBox.append("Litwo, ojczyzno moja, Ty jests jak zdrowie.\n Lubie placki\n");
	}
	
}

/**
 * @author karoru
 *
 */
/**
 * @author karoru
 *
 */
public class GameWindow extends JFrame {
	JPanel board;
	int boardSize;
	JButton[] gobanFields;
	GameWindow(int boardSize) {
		super();
		this.boardSize = boardSize;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new JPanel();
		board.setLayout(new GridLayout(boardSize, boardSize, 0, 0));
		board.setPreferredSize(new Dimension(300, 300));
		
		this.getContentPane().setLayout(new BorderLayout());
		
		ControlsPane controls = new ControlsPane();
		Goban goban = new Goban(boardSize);
		PlayerChat chat = new PlayerChat();
		
		this.getContentPane().add(goban, BorderLayout.LINE_START);
		this.getContentPane().add(controls, BorderLayout.LINE_END);
		this.getContentPane().add(chat, BorderLayout.PAGE_END);
	}
	
	public static void main(String[] args) {
		GobanGraphics.init();
		GameWindow window = new GameWindow(9);
		window.pack();
		window.setVisible(true);
	}

}

