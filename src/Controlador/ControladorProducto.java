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
