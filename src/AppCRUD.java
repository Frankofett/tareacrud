import java.util.List;
import java.util.Scanner;

public class AppCRUD {
    public static void main(String[] args) {
        ProductoCRUD gestor = new ProductoCRUD();
        
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("-----------------------------");
            System.out.println("   SISTEMA DE PRODUCTOS CRUD  ");
            System.out.println("-----------------------------");
            System.out.println("1. Registrar nuevo producto");
            System.out.println("2. Ver lista de productos");
            System.out.println("3. Modificar un producto");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Salir del programa");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        
                        // Creamos el objeto y lo pasamos al gestor
                        Producto nuevo = new Producto(nombre, precio);
                        gestor.registrar(nuevo);
                        break;

                    case 2:
                        System.out.println("\n--- LISTA DE PRODUCTOS ---");
                        List<Producto> productos = gestor.listar();
                        if (productos.isEmpty()) {
                            System.out.println("No hay productos registrados.");
                        } else {
                            for (Producto p : productos) {
                                System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: $" + p.getPrecio());
                            }
                        }
                        break;

                    case 3:
                        System.out.print("ID del producto a modificar: ");
                        int idMod = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = Double.parseDouble(scanner.nextLine());

                        // Creamos el objeto con los datos actualizados y el ID correspondiente
                        Producto actualizado = new Producto(idMod, nuevoNombre, nuevoPrecio);
                        if (gestor.actualizar(actualizado)) {
                            System.out.println("Producto actualizado correctamente.");
                        } else {
                            System.out.println("No se encontró ningún producto con ese ID.");
                        }
                        break;

                    case 4:
                        System.out.print("ID del producto a eliminar: ");
                        int idDel = Integer.parseInt(scanner.nextLine());
                        if (gestor.eliminar(idDel)) {
                            System.out.println("Producto eliminado correctamente.");
                        } else {
                            System.out.println("No se encontró ningún producto con ese ID.");
                        }
                        break;

                    case 5:
                        System.out.println("Saliendo del sistema... ¡Éxito en la entrega de tu tarea!");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error en los datos ingresados. Intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}