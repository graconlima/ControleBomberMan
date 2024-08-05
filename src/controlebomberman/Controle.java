package controlebomberman;

import jakarta.xml.ws.Service;
//import rmi.Movimento;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.xml.namespace.QName;
import soap.MovimentoListener;

public class Controle extends JFrame implements KeyListener, MovimentoListener{

    final static int BOMBA = 32;//esp
    final static int ACIMA = 38;
    final static int ABAIXO = 40;
    final static int ESQUERDA = 37;
    final static int DIREITA = 39;
    final static int DIMENSAO = 50;
    final static Color CORES[] = {Color.CYAN, Color.BLUE};
    
    int l = 3, c = 7, id = 0;

    soap.Movimento stub;
    JLabel[] rotulos = new JLabel[l*c];

    String nome = "nome_padrao";
    
    void iniciaGUI(){
        
        
        setLayout(new GridLayout(l,c));
        for(int i = 0; i < l*c;i++){
            
            String r = "";//+i;
            
            if(i == 7) r = "<";
            if(i == 1) r = "^";
            if(i == 15) r = "v";
            if(i == 9) r = ">";

            if(i == 11) r = "Y";
            if(i == 5) r = "X";
            if(i == 19) r = "A";
            if(i == 13) r = "B";
            
            rotulos[i] = new JLabel(r);
            rotulos[i].setOpaque(true);
            
            final int x = i;
            rotulos[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("MCli");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //System.out.println("MPress");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //System.out.println("MSout");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    rotulos[x].setBackground(Color.CYAN);
                    rotulos[x].setOpaque(true);
                    //System.out.println("MEnt");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    rotulos[x].setBackground(Color.BLUE);
                    rotulos[x].setOpaque(false);
                    //System.out.println("MSai");
                }
            });

            add(rotulos[i]);
        }
        
        
        //UIManager.getIcon("")
        
        setSize(c*DIMENSAO, l*DIMENSAO);
        setLocation(c*DIMENSAO/2, l*DIMENSAO/100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
    }
    
    public Controle() throws RemoteException, NotBoundException, URISyntaxException, MalformedURLException {

        iniciaGUI();
        
        String e = "localhost";

        URL url = new URI("http://127.0.0.1:9876/bomberman?wsdl").toURL();
        QName qname = new QName("http://controller/","ControllerSOAPService");
        Service s = Service.create(url, qname);
        stub = s.getPort(soap.Movimento.class);
        /*int p = 2000;
        Registry r = LocateRegistry.getRegistry(e, p);
        stub = (Movimento) r.lookup("controller_rmi");
        */
        nome = JOptionPane.showInputDialog("Informe seu nome - 7 chars");
        nome  = (nome == null || nome.isEmpty()) ? "Nick" : nome;
        id = stub.adicionarJogador(nome);
        //stub.addMovimentoListener(this);
    }
    
    
    public void keyPressed(KeyEvent ke) {
//        try{
            stub.moverPeca(ke.getKeyCode(), id);
            System.out.println("Movimento...");

            SwingWorker sw = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    switch(ke.getKeyCode()){
                        case DIREITA:
                            rotulos[9].setBackground(CORES[0]);
                            rotulos[9].setOpaque(true);
                            Thread.sleep(100);
                            rotulos[9].setBackground(CORES[1]);
                            rotulos[9].setOpaque(false);
                        break;
                        case ESQUERDA:
                            rotulos[7].setBackground(CORES[0]);
                            rotulos[7].setOpaque(true);
                            Thread.sleep(100);
                            rotulos[7].setBackground(CORES[1]);
                            rotulos[7].setOpaque(false);
                        break;
                        case ACIMA:
                            rotulos[1].setBackground(CORES[0]);
                            rotulos[1].setOpaque(true);
                            Thread.sleep(100);
                            rotulos[1].setBackground(CORES[1]);
                            rotulos[1].setOpaque(false);
                        break;
                        case ABAIXO:
                            rotulos[15].setBackground(CORES[0]);
                            rotulos[15].setOpaque(true);
                            Thread.sleep(100);
                            rotulos[15].setBackground(CORES[1]);
                            rotulos[15].setOpaque(false);
                        break;
                        case BOMBA:
                            rotulos[19].setBackground(CORES[0]);
                            rotulos[19].setOpaque(true);
                            Thread.sleep(100);
                            rotulos[19].setBackground(CORES[1]);
                            rotulos[19].setOpaque(false);
                        break;
                    }        
                    return null;
                }
            };
            
            sw.execute();

//        }catch(RemoteException re){
//            re.printStackTrace();
//        }
    }

    public void keyTyped(KeyEvent ke) {}

    public void keyReleased(KeyEvent ke) {}

    public void movimentouPeca(String movimento) {
        System.out.println("Um Movimento: "+movimento);
    }
      
    public static void main(String[] args) throws RemoteException, NotBoundException, URISyntaxException, MalformedURLException {
        new Controle();
    }
}
