# -_CRUD_de_Productos_en_Java_Arquitectura_por_Capas_- :.
🧾 CRUD de Productos en Java (Arquitectura por Capas):

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/4e1f388e-df1a-4ef8-a578-dfeea8367ded" />  

```

Solución completa, estructurada y funcional en Java (IntelliJ) con:

✔ CRUD completo (Create, Read, Update, Delete)
✔ Arquitectura por capas (Modelo – DAO – Servicio – Controlador – Vista)
✔ Interfaz gráfica (Swing)
✔ Persistencia con JDBC (compatible con Oracle 19c / H2 / MySQL)
✔ Tema: Gestión de Productos .

🧩 1. Modelo de Datos (BD)
CREATE TABLE PRODUCTOS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NOMBRE VARCHAR2(100),
    PRECIO NUMBER(10,2),
    STOCK NUMBER
);

🧠 2. Estructura del Proyecto
src/
 ├── modelo/
 │    └── Producto.java
 ├── dao/
 │    └── ProductoDAO.java
 ├── servicio/
 │    └── ProductoService.java
 ├── vista/
 │    └── VistaProducto.java
 ├── controlador/
 │    └── ControladorProducto.java
 ├── util/
 │    └── ConexionBD.java
 └── Main.java

🔌 3. Conexión a BD
package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "system";
    private static final String PASS = "1234";

    public static Connection getConexion() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

📦 4. Modelo
package modelo;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto() {}

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

🗄️ 5. DAO (CRUD)
package dao;

import modelo.Producto;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertar(Producto p) {
        String sql = "INSERT INTO PRODUCTOS (NOMBRE, PRECIO, STOCK) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE"),
                        rs.getDouble("PRECIO"),
                        rs.getInt("STOCK")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void actualizar(Producto p) {
        String sql = "UPDATE PRODUCTOS SET NOMBRE=?, PRECIO=?, STOCK=? WHERE ID=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM PRODUCTOS WHERE ID=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

⚙️ 6. Servicio
package servicio;

import dao.ProductoDAO;
import modelo.Producto;

import java.util.List;

public class ProductoService {

    private ProductoDAO dao = new ProductoDAO();

    public void guardar(Producto p) {
        dao.insertar(p);
    }

    public List<Producto> obtenerTodos() {
        return dao.listar();
    }

    public void actualizar(Producto p) {
        dao.actualizar(p);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}

🖥️ 7. Vista (Swing)
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

🎮 8. Controlador
package controlador;

import modelo.Producto;
import servicio.ProductoService;
import vista.VistaProducto;

import javax.swing.table.DefaultTableModel;

public class ControladorProducto {

    private VistaProducto vista;
    private ProductoService servicio;

    public ControladorProducto(VistaProducto vista, ProductoService servicio) {
        this.vista = vista;
        this.servicio = servicio;

        cargarDatos();

        vista.btnAgregar.addActionListener(e -> agregar());
        vista.btnActualizar.addActionListener(e -> actualizar());
        vista.btnEliminar.addActionListener(e -> eliminar());
    }

    private void agregar() {
        Producto p = new Producto(
                vista.txtNombre.getText(),
                Double.parseDouble(vista.txtPrecio.getText()),
                Integer.parseInt(vista.txtStock.getText())
        );

        servicio.guardar(p);
        cargarDatos();
    }

    private void actualizar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila >= 0) {
            int id = (int) vista.tabla.getValueAt(fila, 0);

            Producto p = new Producto(
                    id,
                    vista.txtNombre.getText(),
                    Double.parseDouble(vista.txtPrecio.getText()),
                    Integer.parseInt(vista.txtStock.getText())
            );

            servicio.actualizar(p);
            cargarDatos();
        }
    }

    private void eliminar() {
        int fila = vista.tabla.getSelectedRow();

        if (fila >= 0) {
            int id = (int) vista.tabla.getValueAt(fila, 0);
            servicio.eliminar(id);
            cargarDatos();
        }
    }

    private void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) vista.tabla.getModel();
        modelo.setRowCount(0);

        servicio.obtenerTodos().forEach(p -> {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            });
        });
    }
}

🚀 9. Main
import controlador.ControladorProducto;
import servicio.ProductoService;
import vista.VistaProducto;

public class Main {

    public static void main(String[] args) {

        VistaProducto vista = new VistaProducto();
        ProductoService servicio = new ProductoService();

        new ControladorProducto(vista, servicio);

        vista.setVisible(true);
    }
}

🧪 10. Datos de Prueba
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, STOCK) VALUES ('Laptop', 2500, 10);
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, STOCK) VALUES ('Mouse', 50, 100);
INSERT INTO PRODUCTOS (NOMBRE, PRECIO, STOCK) VALUES ('Teclado', 120, 50);

🧩 Posibles Mejoras (Nivel Profesional)
Validaciones robustas (try/catch + JOptionPane)
Patrón DAO + Factory
Uso consistente de PreparedStatement
Arquitectura MVC más estricta
Integración con JPA / Hibernate
Tema visual moderno (FlatLaf)
Búsqueda y filtros dinámicos
Paginación en JTable :. . / .  
