package exercicios.semana1.ex1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;

public class ImageViewer {
    private JFrame frame;
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JButton prevButton, nextButton, updateButton;
    private File[] imageFiles;
    private int currentImageIndex = 0;
    private String imagesPath;

    public ImageViewer(String path) {
        this.imagesPath = path;
        initializeGUI();
        loadImages();
        showImage(0);
    }

    private void initializeGUI() {
        // Configurar a janela principal
        frame = new JFrame("Visualizador de Imagens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // NORTH - Título da imagem
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        // CENTER - Área de exibição da imagem
        imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Adicionar scroll pane para imagens grandes
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);

        // SOUTH - Painel de botões
        JPanel buttonPanel = new JPanel();
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        updateButton = new JButton("Update");
        //buttonPanel.add(prevButton);
        buttonPanel.add(updateButton);
        //buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(prevButton, BorderLayout.WEST);
        frame.add(nextButton, BorderLayout.EAST);

        // Configurar ActionListeners
        setupEventListeners();

        // Centralizar a janela
        frame.setLocationRelativeTo(null);
    }

    private void setupEventListeners() {
        // Botão Anterior
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex > 0) {
                    showEndMessage("");
                    showImage(currentImageIndex - 1);
                } else {
                    currentImageIndex = currentImageIndex - 1;
                    showEndMessage("Fim das imagens triste");
                }
            }
        });

        // Botão Próximo
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex < imageFiles.length - 1) {
                    showEndMessage("");
                    showImage(currentImageIndex + 1);
                } else {
                    currentImageIndex = currentImageIndex + 1;
                    showEndMessage("Fim das imagens :(");
                }
            }
        });

        // Botão Update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImages();
                if (imageFiles.length > 0) {
                    showImage(0);
                } else {
                    showEndMessage("Nenhuma imagem encontrada");
                }
            }
        });
    }

    private void loadImages() {
        File folder = new File(imagesPath);

        // Verificar se a pasta existe
        if (!folder.exists() || !folder.isDirectory()) {
            JOptionPane.showMessageDialog(frame, "Pasta não encontrada: " + imagesPath);
            imageFiles = new File[0];
            return;
        }

        // Carregar imagens com filtro
        imageFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) return false;

                String name = file.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                        name.endsWith(".png") || name.endsWith(".gif") ||
                        name.endsWith(".bmp");
            }
        });

        if (imageFiles == null) {
            imageFiles = new File[0];
        }

        System.out.println("Encontradas " + imageFiles.length + " imagens");
    }

    private void showImage(int index) {
        if (imageFiles.length == 0) {
            showEndMessage("Nenhuma imagem encontrada");
            return;
        }

        if (index >= 0 && index < imageFiles.length) {
            currentImageIndex = index;
            File imageFile = imageFiles[index];

            try {
                // Carregar e exibir a imagem
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());

                // Redimensionar a imagem se for muito grande
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);

                imageLabel.setIcon(icon);
                titleLabel.setText(imageFile.getName());

                System.out.println("Exibindo: " + imageFile.getName());

            } catch (Exception e) {
                showEndMessage("Erro ao carregar imagem: " + imageFile.getName());
                e.printStackTrace();
            }
        }
    }

    private void showEndMessage(String message) {
        //if(message.equals("Fim das imagens triste")) {
        imageLabel.setText(message);
        imageLabel.setIcon(null);
        titleLabel.setText("");
        System.out.println(message);
        //}
        //return false;
    }

    public void open() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Verificar argumentos de linha de comando
        String imagePath;
        if (args.length > 0) {
            imagePath = args[0];
        } else {
            // Usar pasta padrão se não for especificado
            imagePath = "/home/rmpgm/Downloads/Semana1/ex1/images";
        }

        System.out.println("Pasta de imagens: " + imagePath);

        // Executar na EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageViewer(imagePath).open();
            }
        });
    }

}

