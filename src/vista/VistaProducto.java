package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaProducto extends JFrame {

    public JTextField txtNombre = new JTextField(10);
    public JTextField txtPrecio = new JTextField(10);
    public JTextField txtStock = new JTextField(10);

    public JButton btnAgregar = new JButton("Agregar");
    public JButton btnActualizar = new JButton("Actualizar");
    public JButton btnEliminar = new JButton("Eliminar");

    public JTable tabla = new JTable();
    public DefaultTableModel modelo = new DefaultTableModel();

    public VistaProducto() {

        setTitle("CRUD Productos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        add(new JLabel("Nombre")).setBounds(20, 20, 80, 25);
        add(txtNombre).setBounds(100, 20, 120, 25);

        add(new JLabel("Precio")).setBounds(20, 60, 80, 25);
        add(txtPrecio).setBounds(100, 60, 120, 25);

        add(new JLabel("Stock")).setBounds(20, 100, 80, 25);
        add(txtStock).setBounds(100, 100, 120, 25);

        btnAgregar.setBounds(250, 20, 120, 30);
        btnActualizar.setBounds(250, 60, 120, 30);
        btnEliminar.setBounds(250, 100, 120, 30);

        add(btnAgregar);
        add(btnActualizar);
        add(btnEliminar);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        tabla.setModel(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 540, 180);
        add(scroll);
    }   
}