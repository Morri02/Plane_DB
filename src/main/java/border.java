import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class border extends JFrame {
    private JPanel panel1;
    private JButton btPrevious;
    private JButton btNext;
    private JButton btInsert;
    private JButton btRemove;
    private JTextField tfWidth;
    private JTextField tfHeight;
    private JTextField tfWingspan;
    private JTextField tfCode;
    private int selectedObject;

    private List<Plane> planes;

    public border() {

        super();

        initData();
        buttonBehaviors();
        generateMenu();
        showItem();

        setTitle("YAPlaneManager");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 250);
        setResizable(false);
        setVisible(true);
    }

    void buttonBehaviors(){
        btNext.addActionListener(e -> {
            selectedObject = Math.min(planes.size() - 1, selectedObject + 1);
            showItem();
        });

        btPrevious.addActionListener(e -> {
            selectedObject = Math.max(0, selectedObject - 1);
            showItem();
        });

        btRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Plane p = planes.get(selectedObject);
                planes.remove(p);
                selectedObject--;
                showItem();
            }
        });

        btInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] p =
                        JOptionPane.showInputDialog("Insert new plane (double;double;double;string)").split(";");
                planes.add(new Plane(Double.parseDouble(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]),
                        p[3]));
            }
        });

    }

    void initData(){
        planes = new ArrayList<>();
        planes.add(new Plane(10, 10, 20, "HHFD"));
        planes.add(new Plane(5, 6, 68, "FEDR"));
        planes.add(new Plane(10, 10, 20, "ABCD"));

        selectedObject = 0;
    }

    void showItem(){
        try{
            Plane p = planes.get(selectedObject);
            tfWidth.setText(Double.toString(p.getWidth()));
            tfHeight.setText(Double.toString(p.getHeight()));
            tfWingspan.setText(Double.toString(p.getWingSpan()));
            tfCode.setText(p.getCode());
        } catch (IndexOutOfBoundsException e){
            tfWidth.setText("");
            tfHeight.setText("");
            tfWingspan.setText("");
            tfCode.setText("");
        }


    }

    void generateMenu(){
        JMenuItem open = new JMenuItem("Open...");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem save = new JMenuItem("Save...");

        open.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                try{
                    planes = PlaneStorage.load(chooser.getSelectedFile().toPath());
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });

        close.addActionListener(e -> dispose());
        JMenu file = new JMenu("File");
        file.add(open);
        file.add(save);
        file.add(close);

        JMenuBar bar = new JMenuBar();
        bar.add(file);

        setJMenuBar(bar);
    }
}
