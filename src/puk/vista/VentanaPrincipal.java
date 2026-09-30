/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package puk.vista;

import puk.modelo.GestionBiblioteca;
import javax.swing.table.DefaultTableModel;
import puk.modelo.Libro;
import puk.persistencia.PersistenciaBiblioteca;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author utpuk
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form ventanaPrincipal
     */
    private GestionBiblioteca biblioteca;
    private PersistenciaBiblioteca persistencia;

    public VentanaPrincipal(
            GestionBiblioteca biblioteca,
            PersistenciaBiblioteca persistencia) {

        initComponents();

        btnEditarLibro.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    btnEditarLibro.doClick();
                }
            }
        });
       
        getContentPane().setBackground(
                new java.awt.Color(245, 241, 232)
        );

        Image icono = new ImageIcon(
                getClass().getResource("/puk/recursos/logo.png")
        ).getImage();

        setIconImage(icono);

        tablaLibros.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    eliminarLibroSeleccionado();
                }
            }
        });

        this.biblioteca = biblioteca;
        this.persistencia = persistencia;

        bloquearEdicionTabla();
        cargarTabla();
        ajustarColumnas();

        aplicarRenderizadoTabla();

        tablaLibros.setRowHeight(26);

        tablaLibros.getTableHeader().setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13)
        );

        tablaLibros.setFont(
                new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)
        );

        tablaLibros.getTableHeader().setBackground(
                new java.awt.Color(110, 79, 52)
        );

        tablaLibros.getTableHeader().setForeground(
                java.awt.Color.WHITE
        );
        setLocationRelativeTo(null);
    }

    private void eliminarLibroSeleccionado() {

        int filaSeleccionada = tablaLibros.getSelectedRow();

        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un libro antes de eliminarlo.",
                    "Ningún libro seleccionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres eliminar el libro seleccionado?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(
                tablaLibros.getValueAt(filaSeleccionada, 0).toString()
        );

        biblioteca.eliminarLibro(id);

        persistencia.guardar(biblioteca);

        cargarTabla();

    }

    public void cargarTabla() {

        cargarLibrosEnTabla(biblioteca.getLibros());
        actualizarTotalLibros();
    }

    private void actualizarTotalLibros() {

        lblTotalLibros.setText(
                "Total de libros: "
                + biblioteca.getLibros().size()
        );

    }

    private void cargarLibrosEnTabla(ArrayList<Libro> libros) {

        DefaultTableModel modelo
                = (DefaultTableModel) tablaLibros.getModel();

        modelo.setRowCount(0);

        for (Libro libro : libros) {

            Object[] fila = {
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getIdioma(),
                libro.isOriginal() ? "Original" : "Fotocopia",
                libro.isFisico() ? "Físico" : "Digital"
            };

            modelo.addRow(fila);
        }
    }

    private void ajustarColumnas() {
        tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaLibros.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaLibros.getColumnModel().getColumn(2).setPreferredWidth(160);
        tablaLibros.getColumnModel().getColumn(3).setPreferredWidth(130);
        tablaLibros.getColumnModel().getColumn(4).setPreferredWidth(90);
        tablaLibros.getColumnModel().getColumn(5).setPreferredWidth(90);
        tablaLibros.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    private void bloquearEdicionTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID",
                    "Título",
                    "Autor",
                    "ISBN",
                    "Idioma",
                    "Original",
                    "Formato"
                }
        ) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaLibros.setModel(modelo);
    }

    private void aplicarRenderizadoTabla() {

        tablaLibros.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component c = super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                );

                if (isSelected) {

                    c.setBackground(new java.awt.Color(180, 220, 180));
                    c.setForeground(java.awt.Color.BLACK);

                } else {

                    if (row % 2 == 0) {
                        c.setBackground(java.awt.Color.WHITE);
                    } else {
                        c.setBackground(
                                new java.awt.Color(248, 244, 236)
                        );
                    }

                    c.setForeground(java.awt.Color.BLACK);
                }

                return c;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        lblTitulo = new javax.swing.JLabel();
        scrollTablaLibros = new javax.swing.JScrollPane();
        tablaLibros = new javax.swing.JTable();
        btnNuevoLibro = new javax.swing.JButton();
        btnEliminarLibro = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        lblTotalLibros = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        btnEditarLibro = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(245, 241, 232));
        setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 600));

        lblTitulo.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        lblTitulo.setText("Biblioteca de Puk");

        tablaLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "Autor", "ISBN", "Año edición", "Original", "Formato"
            }
        ));
        scrollTablaLibros.setViewportView(tablaLibros);

        btnNuevoLibro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/nuevo.png"))); // NOI18N
        btnNuevoLibro.setText("Nuevo libro");
        btnNuevoLibro.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevoLibro.setIconTextGap(8);
        btnNuevoLibro.setMargin(new java.awt.Insets(4, 8, 4, 8));
        btnNuevoLibro.setPreferredSize(new java.awt.Dimension(170, 50));
        btnNuevoLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoLibroActionPerformed(evt);
            }
        });

        btnEliminarLibro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/eliminar.png"))); // NOI18N
        btnEliminarLibro.setText("Eliminar libro");
        btnEliminarLibro.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminarLibro.setIconTextGap(8);
        btnEliminarLibro.setMargin(new java.awt.Insets(4, 8, 4, 8));
        btnEliminarLibro.setPreferredSize(new java.awt.Dimension(170, 50));
        btnEliminarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarLibroActionPerformed(evt);
            }
        });

        txtBuscar.setText("Buscar...");
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarMouseClicked(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBuscar.setIconTextGap(8);
        btnBuscar.setMargin(new java.awt.Insets(4, 8, 4, 8));
        btnBuscar.setPreferredSize(new java.awt.Dimension(120, 50));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/limpiar.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLimpiar.setIconTextGap(8);
        btnLimpiar.setMargin(new java.awt.Insets(4, 8, 4, 8));
        btnLimpiar.setPreferredSize(new java.awt.Dimension(120, 50));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        lblTotalLibros.setText("Total del libros: 0");

        lblBuscar.setText("Buscar:");

        btnEditarLibro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/editar.png"))); // NOI18N
        btnEditarLibro.setText("Editar libro");
        btnEditarLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarLibroActionPerformed(evt);
            }
        });

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/puk/recursos/refrescar.png"))); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollTablaLibros)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnEliminarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnEditarLibro)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefrescar)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBuscar, btnEditarLibro, btnEliminarLibro, btnLimpiar, btnNuevoLibro, btnRefrescar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalLibros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTablaLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarLibro)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(btnRefrescar))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBuscar, btnEditarLibro, btnEliminarLibro, btnLimpiar, btnNuevoLibro, btnRefrescar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoLibroActionPerformed
        VentanaNuevoLibro ventana
                = new VentanaNuevoLibro(
                        biblioteca,
                        persistencia,
                        this
                );

        ventana.setVisible(true);
    }//GEN-LAST:event_btnNuevoLibroActionPerformed

    private void btnEliminarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarLibroActionPerformed
        int filaSeleccionada = tablaLibros.getSelectedRow();

        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un libro de la tabla.",
                    "Ningún libro seleccionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id = Integer.parseInt(
                tablaLibros.getValueAt(filaSeleccionada, 0).toString()
        );

        String titulo = tablaLibros
                .getValueAt(filaSeleccionada, 1)
                .toString();

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar \"" + titulo + "\"?",
                "Confirmar eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = biblioteca.eliminarLibro(id);

        persistencia.guardar(biblioteca);

        cargarTabla();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Libro eliminado",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }//GEN-LAST:event_btnEliminarLibroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            cargarTabla();
            return;
        }

        cargarLibrosEnTabla(
                biblioteca.buscarLibros(texto)
        );

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseClicked
        if (txtBuscar.getText().equals("Buscar...")) {
            txtBuscar.setText("");
        }
    }//GEN-LAST:event_txtBuscarMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtBuscar.setText("Buscar...");
        cargarTabla();

    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        btnBuscar.doClick();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnEditarLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarLibroActionPerformed
        int filaSeleccionada = tablaLibros.getSelectedRow();

        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un libro de la tabla.",
                    "Ningún libro seleccionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id = Integer.parseInt(
                tablaLibros.getValueAt(filaSeleccionada, 0).toString()
        );

        Libro libro = biblioteca.buscarLibroPorId(id);

        if (libro == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se ha encontrado el libro.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        javax.swing.JComboBox<String> cmbOriginal
                = new javax.swing.JComboBox<>(
                        new String[]{"Original", "Fotocopia"}
                );

        javax.swing.JComboBox<String> cmbFormato
                = new javax.swing.JComboBox<>(
                        new String[]{"Físico", "Digital"}
                );

        cmbOriginal.setSelectedItem(
                libro.isOriginal() ? "Original" : "Fotocopia"
        );

        cmbFormato.setSelectedItem(
                libro.isFisico() ? "Físico" : "Digital"
        );

        javax.swing.JPanel panel = new javax.swing.JPanel(
                new java.awt.GridLayout(2, 2, 10, 10)
        );

        panel.add(new javax.swing.JLabel("Estado:"));
        panel.add(cmbOriginal);

        panel.add(new javax.swing.JLabel("Formato:"));
        panel.add(cmbFormato);

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                this,
                panel,
                "Editar libro",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE
        );

        if (respuesta != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }

        libro.setOriginal(
                cmbOriginal.getSelectedItem().equals("Original")
        );

        libro.setFisico(
                cmbFormato.getSelectedItem().equals("Físico")
        );

        persistencia.guardar(biblioteca);

        cargarTabla();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Libro actualizado correctamente.",
                "Libro editado",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }//GEN-LAST:event_btnEditarLibroActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed

        biblioteca = persistencia.cargar();
        cargarTabla();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Lista de libros actualizada."
        );

    }//GEN-LAST:event_btnRefrescarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditarLibro;
    private javax.swing.JButton btnEliminarLibro;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevoLibro;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalLibros;
    private javax.swing.JScrollPane scrollTablaLibros;
    private javax.swing.JTable tablaLibros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
