package BuscaminasHalloween.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class HalloweenMinesweeper extends JFrame {
    private static final int GRID_SIZE = 10;
    private static final int NUM_MINES = 15;

    private JButton[][] botones;
    private boolean[][] minas;

    private ImageIcon calabazaIcon;
    private ImageIcon calaveraIcon;

    private int partidasGanadas = 0;
    private int partidasPerdidas = 0;
    private JLabel contadorLabel;

    public HalloweenMinesweeper() {
        super();

        setTitle("Halloween Minesweeper");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        File archivoCalabaza = new File("src/resources/calabaza.png");
        if(archivoCalabaza.exists())
        {
            calabazaIcon = new ImageIcon(archivoCalabaza.getAbsolutePath());
        }

        File archivoCalavera = new File("src/resources/calavera.png");
        if(archivoCalavera.exists())
        {
            calaveraIcon = new ImageIcon(archivoCalavera.getAbsolutePath());
        }

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        JPanel panelJuego = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        panelPrincipal.add(panelJuego, BorderLayout.CENTER);

        botones = new JButton[GRID_SIZE][GRID_SIZE];
        minas = new boolean[GRID_SIZE][GRID_SIZE];

        for (int fila = 0; fila < GRID_SIZE; fila++)
        {
            for (int columna = 0; columna < GRID_SIZE; columna++){

                botones[fila][columna] = new JButton();
                if(calabazaIcon != null)
                {
                    botones[fila][columna].setIcon(calabazaIcon);
                }
                botones[fila][columna].setPreferredSize(new Dimension(40, 40));
                panelJuego.add(botones[fila][columna]);

                final int x = fila;
                final int y = columna;
                botones[fila][columna].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        manejarClic(x, y);
                    }
                });
            }
        }

        JPanel panelSuperior = new JPanel();
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        contadorLabel = new JLabel("Victorias: 0 | Perdidas: 0");
        panelSuperior.add(contadorLabel);

        JButton reiniciarPartidaButton = new JButton("Reiniciar Partida");
        reiniciarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                colocarMinas();
            }
        });



        panelSuperior.add(reiniciarPartidaButton);
        colocarMinas();



        setVisible(true);
    }

    public static void main(String[] args)
    {
        new HalloweenMinesweeper();
    } 

    private void ReiniciarPanelJuego() 
    {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                minas[i][j] = false;
                botones[i][j].setEnabled(true);
                if(calabazaIcon != null) botones[i][j].setIcon(calabazaIcon);
                botones[i][j].setText("");
            }

        }
    }

    private void colocarMinas() 
    {
        Random random = new Random();
        int minasColocadas = 0;
        ReiniciarPanelJuego();

    while (minasColocadas < NUM_MINES) {
        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE);

        if(!minas[x][y])
        {
            minas[x][y] = true;
            minasColocadas++;
        }
    }

    }


    private void manejarClic(int x, int y)
    {
        if(minas[x][y])
        {
            if(calaveraIcon != null) botones[x][y].setIcon(calaveraIcon);
            botones[x][y].setEnabled(false);
            mostrarTodasLasminas();
            partidasPerdidas++;
            actualizarContador();
            mostrarToast("Oh no! Has pulsado una mina.");
        }
        else 
        {
            botones[x][y].setText("1");
            botones[x][y].setIcon(null);
            botones[x][y].setEnabled(false);

            if(haGanado())
            {
                partidasGanadas++;
                actualizarContador();
                mostrarToast("¡Felicidades! No le has dado ha ninguna mina.");
            }
        }
    }

    private void actualizarContador() 
    {
        if(contadorLabel != null)
        {
            contadorLabel.setText("Victorias: " + partidasGanadas + " | "+ "Perdidas: "+ partidasPerdidas );
        }
    }
    private void mostrarToast(String mensaje) {
        // Crear una ventana temporal para el "toast"
        JWindow toast = new JWindow(this);
        toast.setLayout(new BorderLayout());
    
        // Configurar el mensaje y el estilo
        JLabel label = new JLabel(mensaje, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 170)); // Fondo semi-transparente
        label.setForeground(Color.WHITE);             // Texto en blanco
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        toast.add(label, BorderLayout.CENTER);
        toast.setSize(500, 50);
        
        // Centramos el "toast" en el medio de la ventana principal
        int x = this.getX() + (this.getWidth() - toast.getWidth()) / 2;
        int y = this.getY() + (this.getHeight() - toast.getHeight()) / 2;
        toast.setLocation(x, y);
    
        // Mostrar el "toast" por un tiempo limitado
        toast.setVisible(true);
        new Timer(2000, e -> toast.setVisible(false)).start(); // Oculta el toast tras 2 segundos
    }

  

    private void mostrarTodasLasminas() {
        for(int fila = 0; fila < GRID_SIZE; fila++){
            for (int columna = 0; columna < GRID_SIZE; columna++) {
                if(minas[fila][columna])
                {
                    botones[fila][columna].setIcon(calaveraIcon);
                }
                botones[fila][columna].setEnabled(false);
            }
        }
    }


    private boolean haGanado() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!minas[i][j] && botones[i][j].isEnabled()) {
                    return false; // Si quedan botones sin descubrir, no ha ganado
                }
            }
        }
        return true;
    }

}