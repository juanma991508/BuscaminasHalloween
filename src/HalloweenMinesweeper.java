package BuscaminasHalloween.src;

import javax.swing.*;
import java.awt.*;

public class HalloweenMinesweeper extends JFrame {


    public HalloweenMinesweeper() {
        super();
        setTitle("Halloween Minesweeper");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
    new HalloweenMinesweeper();
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