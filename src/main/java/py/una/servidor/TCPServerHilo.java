package py.una.servidor;

import java.net.*;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import py.una.bd.DeudaDAO;
import py.una.bd.PersonaDAO;
import py.una.entidad.Deuda;
import py.una.entidad.Persona;

public class TCPServerHilo extends Thread {

    private Socket socket = null;

    TCPMultiServer servidor;
    
    public TCPServerHilo(Socket socket, TCPMultiServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            out.println("Bienvenido!");
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                
                //out.println(inputLine);
                
                //to-do: utilizar json
                if (inputLine.startsWith("ConsultarDeudas ")) {
                    String documento = inputLine.substring("ConsultarDeudas ".length()).trim();
                    outputLine = documento.isEmpty()
                            ? "Petición mal formulada"
                            : consultarDeudas(documento);
                } else if (inputLine.equals("Bye")) {
                    outputLine = "Usted apago el hilo";
                    break;
                    
                }else if (inputLine.equals("Terminar todo")) {
                    servidor.listening = false;
                    outputLine = "Usted apago todo";
                    break;
                    
                } else {
                	outputLine = "No introdujo orden a ejecutar." ;
                }
                
                
                out.println(outputLine);
            }
            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String consultarDeudas(String documento) {
        PersonaDAO personaDAO = new PersonaDAO();
        List<Persona> personas = personaDAO.seleccionarPorNumeroDocumento(documento);
        if (personas.isEmpty()) {
            return "No existe una persona con ese documento";
        }

        Persona persona = personas.get(0);
        List<Deuda> deudas = new DeudaDAO().seleccionarPorPersonaId(persona.getId());
        JSONArray arregloDeudas = new JSONArray();
        BigDecimal montoTotal = BigDecimal.ZERO;
        boolean enMora = false;

        for (Deuda deuda : deudas) {
            JSONObject deudaJson = new JSONObject();
            deudaJson.put("entidad", deuda.getEntidad());
            deudaJson.put("monto", deuda.getMonto());
            deudaJson.put("dias", deuda.getDias());
            arregloDeudas.add(deudaJson);

            montoTotal = montoTotal.add(deuda.getMonto());
            if (deuda.getDias() > 0) {
                enMora = true;
            }
        }

        JSONObject resultado = new JSONObject();
        resultado.put("nombre", persona.getNombreCompleto());
        resultado.put("en_mora", enMora);
        resultado.put("monto_total_adeudado", montoTotal);
        resultado.put("deudas", arregloDeudas);
        return resultado.toJSONString();
    }
}
