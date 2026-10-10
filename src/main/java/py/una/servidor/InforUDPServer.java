package py.una.servidor;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.simple.JSONObject;

import py.una.bd.PersonaDAO;
import py.una.entidad.Persona;

public class InforUDPServer {
	
	
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
                String[] partesSolicitud = datoRecibido.split("\\s+");
                if (partesSolicitud.length == 2 && "VerificarIdentidad".equals(partesSolicitud[0])) {
                    String documento = partesSolicitud[1];
                    List<Persona> personas = pdao.seleccionarPorNumeroDocumento(documento);
                    if (personas.isEmpty()) {
                        respuesta = "No existe una persona con ese documento";
                    } else {
                        Persona persona = personas.get(0);
                        JSONObject resultado = new JSONObject();
                        resultado.put("nombre", persona.getNombreCompleto());
                        resultado.put("tipo_persona", persona.getTipoPersona());
                        resultado.put("estado", persona.getEstado());
                        respuesta = resultado.toJSONString();
                    }
                } else if (partesSolicitud.length == 2 && "ConsultarScore".equals(partesSolicitud[0])) {
                    try {
                        String idPersona = partesSolicitud[1];
                        if (idPersona == null || idPersona.trim().isEmpty()) {
                            respuesta = "Petición mal formulada";
                        } else {
                            List<Persona> personas = pdao.seleccionarPorNumeroDocumento(idPersona);
                            if (personas.isEmpty()) {
                                respuesta = "No existe una persona con ese id";
                            } else {
                                Persona persona = personas.get(0);
                                JSONObject resultado = new JSONObject();
                                resultado.put("nombre", persona.getNombreCompleto());
                                resultado.put("score", persona.getScore());
                                respuesta = resultado.toJSONString();
                            }
                        }
                    } catch (NumberFormatException ex) {
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

