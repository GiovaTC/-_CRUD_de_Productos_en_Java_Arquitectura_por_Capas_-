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
