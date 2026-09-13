package py.una.servidor;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import py.una.bd.PersonaDAO;
import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;

public class VentasUDPServer {
	
	
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        PersonaDAO pdao = new PersonaDAO();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            try (DatagramSocket serverSocket = new DatagramSocket(puertoServidor)) {
			System.out.println("Servidor Electro Hogar - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData(), 0,
                    receivePacket.getLength(), StandardCharsets.UTF_8).trim();
                System.out.println("DatoRecibido: " + datoRecibido );

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                String respuesta;
                if ("GET /api/score".equals(datoRecibido)) {
                    respuesta = PersonaJSON.listaObjetoString(pdao.seleccionar());
                } else if (datoRecibido.startsWith("GET /api/score {")) {
                    try {
                        datoRecibido = datoRecibido.substring("GET /api/score ".length()).trim();
                        JSONObject solicitud = (JSONObject) new JSONParser().parse(datoRecibido);
                        Object valorNumeroDocumento = solicitud.get("numeroDocumento");

                        if (!(valorNumeroDocumento instanceof String)
                                || ((String) valorNumeroDocumento).trim().isEmpty()) {
                            respuesta = "Petición mal formulada";
                        } else {
                            List<Persona> personas = pdao.seleccionarPorNumeroDocumento(
                                    ((String) valorNumeroDocumento).trim());
                            if (personas.isEmpty()) {
                                respuesta = "El numero de documento no corresponde a algun cliente de InforConf";
                            } else {
                                respuesta = PersonaJSON.objetoString(personas.get(0));
                            }
                        }
                    } catch (Exception ex) {
                        respuesta = "Petición mal formulada";
                    }
                } else {
                    respuesta = "Petición mal formulada";
                }

                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                byte[] sendData = respuesta.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

