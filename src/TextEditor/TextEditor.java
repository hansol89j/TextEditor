package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.Font.PLAIN;

/**
 * Created by hansoljeong on 2016. 8. 11..
 */
public class TextEditor extends JFrame {

    //Components
    private JTextArea editArea;
    private JFileChooser fileChooser = new JFileChooser();

    //Create Actions for menu items, buttons...etc
    private Action open = new OpenAction();
    private Action save = new SaveAction();
    private Action exit = new ExitAction();

    public static void main(String[] args){
        new TextEditor();
    }

    public TextEditor(){
        prepareGUI();

    }

    private void prepareGUI(){
        editArea = new JTextArea(100, 100);
        editArea.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        editArea.setFont(new Font("monospaced", PLAIN, 12));
        JScrollPane scrollingText = new JScrollPane(editArea);

        //Create content pane
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingText, BorderLayout.CENTER);

        /**
         * Create menubar
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = menuBar.add(new JMenu("File"));
        menu.setMnemonic('F');
        menu.add(open);
        menu.add(save);
        menu.add(exit);

        //Window content and menu
        setContentPane(content);
        setJMenuBar(menuBar);

        //Window characteristics
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("IdiotiJ");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class OpenAction extends AbstractAction{
        public OpenAction(){
            super("Open...");
            putValue(MNEMONIC_KEY, new Integer('o'));
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(TextEditor.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    // What to do with the file, e.g. display it in a TextArea
                    editArea.read( new FileReader( file.getAbsolutePath() ), null );
                } catch (IOException ex) {
                    System.out.println("problem accessing file"+file.getAbsolutePath());
                }
            } else {
                System.out.println("File access cancelled by user.");
            }
        }
    }

    class SaveAction extends AbstractAction{
        SaveAction(){
            super("Save...");
            putValue(MNEMONIC_KEY, new Integer('s'));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int retval = fileChooser.showSaveDialog(TextEditor.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(f);
                    editArea.write(writer);  // Use TextComponent write
                } catch (IOException ioex) {
                    JOptionPane.showMessageDialog(TextEditor.this, ioex);
                    System.exit(1);
                }
            }
        }
    }

    class ExitAction extends  AbstractAction{
        public ExitAction(){
            super("Exit...");
            putValue(MNEMONIC_KEY, new Integer('E'));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
