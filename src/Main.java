//
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