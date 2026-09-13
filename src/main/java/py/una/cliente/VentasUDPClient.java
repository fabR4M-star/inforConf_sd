package py.una.cliente;


import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class VentasUDPClient {

    public static void main(String a[]) throws Exception {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
            clientSocket.setSoTimeout(10000);

            while (true) {
                System.out.print("Escribe una petición a Electro Hogar (o 'salir'): ");
                String datoPaquete = inFromUser.readLine();

                if (datoPaquete == null || "salir".equalsIgnoreCase(datoPaquete.trim())) {
                    break;
                }

                byte[] sendData = datoPaquete.getBytes(StandardCharsets.UTF_8);
                System.out.println("Enviar " + datoPaquete + " al servidor. (" + sendData.length + " bytes)");
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
                clientSocket.send(sendPacket);

                DatagramPacket receivePacket =
                        new DatagramPacket(new byte[1024], 1024);
                System.out.println("Esperamos si viene la respuesta.");

                try {
                    // ESPERAMOS LA RESPUESTA, BLOQUEANTE
                    clientSocket.receive(receivePacket);

                    String respuesta = new String(receivePacket.getData(), 0,
                            receivePacket.getLength(), StandardCharsets.UTF_8);

                    InetAddress returnIPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                    System.out.println(respuesta);

                } catch (SocketTimeoutException ste) {

                    System.out.println("TimeOut: El paquete udp se asume perdido.");
                }
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
} 

