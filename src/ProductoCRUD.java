import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoCRUD {
    private final String url = "jdbc:sqlite:productos.db";

    public ProductoCRUD() {
        crearTabla();
    }

    private Connection conectar() throws Exception {
        return DriverManager.getConnection(url);
    }

    private void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS productos ("
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " nombre TEXT NOT NULL,"
                   + " precio REAL NOT NULL"
                   + ");";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Error al crear tabla: " + e.getMessage());
        }
    }

    // C de crear en el crud
    public void registrar(Producto producto) {
        String sql = "INSERT INTO productos(nombre, precio) VALUES(?, ?)";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.executeUpdate();
            System.out.println("¡Producto registrado con éxito!");
        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    // R de read o leer pues, en español
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, precio FROM productos";
        try (Connection conn = conectar(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    // U de actualizar
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getId());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // D de borrar
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}