# Laboratorio de sockets - Sistemas distribuidos
## InforConf
### Requisitos
- Maven
- JDK8
- Postgresql

### Preparar la base de datos
1. Crear la base de datos `bd_sockets` y el usuario `postgres` con contraseña `postgres`.
2. Asegurarse de que PostgreSQL escuche en `localhost:5432`.
3. Ejecutar `comandos_bd.sql` dentro de `bd_sockets` para crear las tablas y cargar datos de prueba.

Los documentos incluidos para probar son `80012345-6`, `4567890`, `90098765-4`, `3214567` y `5678901`. En una base recién creada, sus IDs serán del 1 al 5, en ese orden.

### Compilar
Desde esta carpeta ejecutar:

```sh
mvn clean package
```

Se generan `target/servidorUDP.jar` y `target/servidorTCP.jar`.

### Probar UDP
Iniciar el servidor en una terminal:

```sh
java -jar target/servidorUDP.jar
```

En otra terminal, desde el módulo `electro_hogar_sd`, ejecutar `java -jar target/clienteUDP.jar`. El cliente envía cada línea escrita al servidor UDP en el puerto `9876`.

Peticiones admitidas:

```text
VerificarIdentidad 80012345-6
ConsultarScore 80012345-6
```

La primera devuelve un JSON con `nombre`, `tipo_persona` y `estado`, por ejemplo:

```json
{"nombre":"Comercial del Norte S.A.","tipo_persona":"JURIDICA","estado":"ACTIVO"}
```

La implementación actual de `ConsultarScore` también busca por número de documento y devuelve `nombre` y `score`:

```json
{"nombre":"Comercial del Norte S.A.","score":850}
```

Para terminar el cliente UDP, escribir `salir`.

### Probar TCP
Iniciar el servidor TCP en otra terminal:

```sh
java -jar target/servidorTCP.jar
```

El servidor escucha en `localhost:4444`. Desde `electro_hogar_sd`, ejecutar `java -jar target/clienteTCP.jar` y escribir:

```text
ConsultarDeudas 80012345-6
```

La respuesta JSON contiene `nombre`, `en_mora`, `monto_total_adeudado` y el arreglo `deudas` (cada elemento incluye `entidad`, `monto` y `dias`). El cliente TCP presenta el JSON en tablas. También se puede probar con `4567890`, `90098765-4` o cualquier otro documento de prueba.

Escribir `Bye` para cerrar la conexión del cliente. `Terminar todo` activa la señal de cierre global del servidor; como el servidor puede quedar esperando otra conexión en `accept()`, para detener el proceso de forma inmediata usar `Ctrl+C` en la terminal del servidor. Una consulta con un documento inexistente responde `No existe una persona con ese documento`; una petición no admitida responde `Petición mal formulada` en UDP o `No introdujo orden a ejecutar.` en TCP.
