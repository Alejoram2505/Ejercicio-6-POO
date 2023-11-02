import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz que define los métodos comunes para todos los dispositivos electrónicos.
 */
interface DispositivoElectronico {
    /**
     * Enciende el dispositivo.
     */
    void encender();

    /**
     * Apaga el dispositivo.
     */
    void apagar();

    /**
     * Valida si el dispositivo está encendido.
     *
     * @return true si el dispositivo está encendido, false en caso contrario.
     */
    boolean validarEstado();
}

/**
 * Clase que representa un teléfono inteligente.
 */
class Telefono implements DispositivoElectronico {
    String modelo;
    private boolean encendido;

    /**
     * Constructor de la clase Telefono.
     *
     * @param modelo El modelo del teléfono.
     */
    public Telefono(String modelo) {
        this.modelo = modelo;
        this.encendido = false;
    }

    @Override
    public void encender() {
        encendido = true;
    }

    @Override
    public void apagar() {
        encendido = false;
    }

    @Override
    public boolean validarEstado() {
        return encendido;
    }
}

/**
 * Clase que representa una computadora portátil.
 */
class ComputadoraPortatil implements DispositivoElectronico {
    String marca;
    private boolean encendido;

    /**
     * Constructor de la clase ComputadoraPortatil.
     *
     * @param marca La marca de la computadora portátil.
     */
    public ComputadoraPortatil(String marca) {
        this.marca = marca;
        this.encendido = false;
    }

    @Override
    public void encender() {
        encendido = true;
    }

    @Override
    public void apagar() {
        encendido = false;
    }

    @Override
    public boolean validarEstado() {
        return encendido;
    }
}

/**
 * Clase principal que simula una tienda de dispositivos electrónicos.
 */
public class ElectroTechStore {
    public static void main(String[] args) {
        // Crear dispositivos
        Telefono telefono1 = new Telefono("iPhone X");
        Telefono telefono2 = new Telefono("Samsung Galaxy S21");
        ComputadoraPortatil laptop1 = new ComputadoraPortatil("Dell XPS 13");
        ComputadoraPortatil laptop2 = new ComputadoraPortatil("HP Spectre x360");

        // Encender algunos dispositivos
        telefono1.encender();
        laptop2.encender();

        // Crear un stand de dispositivos
        List<DispositivoElectronico> stand = new ArrayList<>();
        stand.add(telefono1);
        stand.add(telefono2);
        stand.add(laptop1);
        stand.add(laptop2);

        // Mostrar información de los dispositivos
        for (DispositivoElectronico dispositivo : stand) {
            mostrarInformacion(dispositivo);
        }

        // Validar qué elementos están encendidos y apagados
        List<DispositivoElectronico> encendidos = new ArrayList<>();
        List<DispositivoElectronico> apagados = new ArrayList<>();
        validarStand(stand, encendidos, apagados);

        System.out.println("\nDispositivos encendidos:");
        for (DispositivoElectronico dispositivo : encendidos) {
            mostrarInformacion(dispositivo);
        }

        System.out.println("\nDispositivos apagados:");
        for (DispositivoElectronico dispositivo : apagados) {
            mostrarInformacion(dispositivo);
        }

        // Guardar los dispositivos en un archivo CSV
        guardarDispositivosCSV(stand, "dispositivos.csv");

        // Cargar los dispositivos desde un archivo CSV
        List<DispositivoElectronico> standCargado = cargarDispositivosCSV("dispositivos.csv");
        System.out.println("\nDispositivos cargados desde CSV:");
        for (DispositivoElectronico dispositivo : standCargado) {
            mostrarInformacion(dispositivo);
        }
    }

    // Función para mostrar información de un dispositivo
    private static void mostrarInformacion(DispositivoElectronico dispositivo) {
        if (dispositivo instanceof Telefono) {
            Telefono telefono = (Telefono) dispositivo;
            System.out.println("Teléfono - Modelo: " + telefono.modelo + ", Encendido: " + telefono.validarEstado());
        } else if (dispositivo instanceof ComputadoraPortatil) {
            ComputadoraPortatil laptop = (ComputadoraPortatil) dispositivo;
            System.out.println("Computadora Portátil - Marca: " + laptop.marca + ", Encendida: " + laptop.validarEstado());
        }
    }

    // Función para validar qué elementos están encendidos y apagados
    private static void validarStand(List<DispositivoElectronico> stand, List<DispositivoElectronico> encendidos, List<DispositivoElectronico> apagados) {
        for (DispositivoElectronico dispositivo : stand) {
            if (dispositivo.validarEstado()) {
                encendidos.add(dispositivo);
            } else {
                apagados.add(dispositivo);
            }
        }
    }

    // Función para guardar los dispositivos en un archivo CSV
    private static void guardarDispositivosCSV(List<DispositivoElectronico> stand, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Tipo,Modelo/Marca,Estado");
            for (DispositivoElectronico dispositivo : stand) {
                if (dispositivo instanceof Telefono) {
                    Telefono telefono = (Telefono) dispositivo;
                    writer.println("Telefono," + telefono.modelo + "," + telefono.validarEstado());
                } else if (dispositivo instanceof ComputadoraPortatil) {
                    ComputadoraPortatil laptop = (ComputadoraPortatil) dispositivo;
                    writer.println("ComputadoraPortatil," + laptop.marca + "," + laptop.validarEstado());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Función para cargar los dispositivos desde un archivo CSV
    private static List<DispositivoElectronico> cargarDispositivosCSV(String filename) {
        List<DispositivoElectronico> stand = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String tipo = parts[0];
                    String modeloMarca = parts[1];
                    boolean estado = Boolean.parseBoolean(parts[2]);
                    if (tipo.equals("Telefono")) {
                        Telefono telefono = new Telefono(modeloMarca);
                        if (estado) {
                            telefono.encender();
                        }
                        stand.add(telefono);
                    } else if (tipo.equals("ComputadoraPortatil")) {
                        ComputadoraPortatil laptop = new ComputadoraPortatil(modeloMarca);
                        if (estado) {
                            laptop.encender();
                        }
                        stand.add(laptop);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stand;
    }
}
