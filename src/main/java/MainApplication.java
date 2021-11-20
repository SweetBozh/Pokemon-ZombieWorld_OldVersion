import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.sound.sampled.*; // for sounds
import java.util.*;
import java.util.Random;
import java.io.*;
import javax.swing.border.*;

public class MainApplication extends JFrame {

    private JPanel contentpane;
    private JLabel drawpane;
    private JComboBox combo;
    private JToggleButton[] tb;
    private JTextField scoreText;
    private JLabel Label;
    private MyImageIcon bgImg, in_gamebg1Img, in_gamebg2Img, in_gamebg3Img, in_gamebg4Img, in_gamebg5Img;
    private ButtonGroup bgroup;
    // private MySoundEffect hitSound, themeSound;

    private int frameWidth = 1366, frameHeight = 768;
    private int itemWidth = 40, itemHeight = 50;
    private int score = 0;
 
    Tutorial Tframe;

    private String []mode = {"Vocab/easy.txt","Vocab/beginner.txt"};
    ArrayList<Mode> modeList = new ArrayList<Mode>();  
    // main method
    public static void main(String[] args) {
        new MainApplication();
    }

    public MainApplication() {
        setTitle("PokemonGame!");
        setBounds(50, 50, frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentpane = (JPanel) getContentPane();
        contentpane.setLayout(new BorderLayout());

        AddComponents();

        //add Vocab 
        readFile(mode);
    }

    public void AddComponents() {
        bgImg = new MyImageIcon("pokemon/night_bg2.png").resize(frameWidth, frameHeight);
        in_gamebg1Img = new MyImageIcon("pokemon/bg2.jpg").resize(frameWidth, frameHeight);

        drawpane = new JLabel();
        drawpane.setIcon(bgImg);
        drawpane.setLayout(null);
        contentpane.add(drawpane, BorderLayout.CENTER);

        // sound fx and bg music
        // hitSound = new MySoundEffect("resources/beep.wav");
        // themeSound = new MySoundEffect("resources/theme.wav");
        // themeSound.playLoop();

        // main menu button
        JButton button1 = new JButton("Start");
        JButton button2 = new JButton("Credit");
        JButton button3 = new JButton("Tutorial");
        JButton button4 = new JButton("Exit");
        button1.setBounds((frameWidth / 2) - 50, (frameHeight / 2) - 100, 100, 50);
        button2.setBounds((frameWidth / 2) - 50, (frameHeight / 2) - 33, 100, 50);
        button3.setBounds((frameWidth / 2) - 50, (frameHeight / 2) + 33, 100, 50);
        button4.setBounds((frameWidth / 2) - 50, (frameHeight / 2) + 100, 100, 50);
        drawpane.add(button1);
        drawpane.add(button2);
        drawpane.add(button3);
        drawpane.add(button4);

        // Cbutton_main.add(button1);
        // Cbutton_main.add(button2);
        // Cbutton_main.add(button3);

        // mode button
        JButton button5 = new JButton("Beginner");
        JButton button6 = new JButton("Easy");
        JButton button7 = new JButton("Normal");
        JButton button8 = new JButton("Hard");
        JButton button9 = new JButton("Nightmare");



        button3.addActionListener(new ActionListener() {     //Tutorial button3
            public void actionPerformed(ActionEvent e){
                if(Tframe == null){
                    Tframe = new Tutorial();
                }
                else{
                    Tframe.setVisible(true);
                }
            }
        });
            
        button4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });


        validate();
    }

    //Add Vocab
    public void readFile(String []mode){
        for(int i =0;i<mode.length;i++){
          enforceFile(mode[i]);
        }
        //System.out.printf("---------------\n");
        //printReadFile();
      }
      public void enforceFile(String fname){
         String fileName = fname;
         int countLine = 0;
         boolean opensuccess = false;
         ArrayList<String> vList = new ArrayList<String> ();
         String name = "";
   
         while(!opensuccess){
           try(Scanner filescan = new Scanner(new File(fileName));){
             opensuccess = true;
               while(filescan.hasNext()){
                 String line = filescan.nextLine();
                 String []buf = line.split(",");
                 if(countLine ==0){
                   name = buf[0].trim();
                   countLine++;
                 }
                 else{              
                    vList.add(buf[0].trim());
                  }
             }
             Mode m = new Mode(name,vList); 
             modeList.add(m);
           }
   
           catch(FileNotFoundException e){
             System.out.println(e);
           }
         }//end while
      }
      public void printReadFile(){ //print read file 
        for(int i = 0 ; i<modeList.size();i++){
          modeList.get(i).printFileWord();
        }
        System.out.println("");
      }
}

class MyImageIcon extends ImageIcon {
    public MyImageIcon(String fname) {
        super(fname);
    }

    public MyImageIcon(Image image) {
        super(image);
    }

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new MyImageIcon(newimg);
    }
};

class Mode{
    private String mode;
    private int sizeList;
    private ArrayList<String> vocabList = new ArrayList<String>();
    private Random random = new Random();
  
    public Mode(){}
    public Mode(String m, ArrayList<String> li){
      mode = m;
      vocabList = li;
      sizeList = vocabList.size();
    }
    public String getMode(){
      return mode;
    }
    public String randomWord(){ //Random word
      int ran = random.nextInt(sizeList);
      return vocabList.get(ran);
    }
    public void printFileWord(){ //check Reading File
      System.out.printf("====== Mode %-9s reading... =====\n",mode);
      for(int i = 0 ;i<vocabList.size();i++){
        System.out.printf("\t [%8s] %-15s \n",mode,vocabList.get(i));
      }
      System.out.println("");
    }
  }