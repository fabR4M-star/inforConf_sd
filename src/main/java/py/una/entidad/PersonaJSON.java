package py.una.entidad;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PersonaJSON {


    public static void main(String[] args) throws Exception {

    	System.out.println("Ejemplo de uso 1: pasar de objeto a string");
     	Persona p = new Persona();
    	p.setNumeroDocumento("80012345-6");
    	p.setNombreCompleto("Comercial del Norte S.A.");
    	p.setTipoPersona("JURIDICA");
    	p.setEstado("ACTIVO");
    	p.setScore(850);
    	p.setCategoriaRiesgo("BAJO");
    	
    	String r1 = PersonaJSON.objetoString(p);
    	System.out.println(r1);
    	
    	
    	System.out.println("\n*************************************************************************");
    	System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
     	String un_string = "{\"numeroDocumento\":\"4567890\",\"nombreCompleto\":\"Maria Gonzalez\",\"tipoPersona\":\"FISICA\",\"estado\":\"ACTIVO\",\"score\":620,\"categoriaRiesgo\":\"MEDIO\"}";
    	
    	Persona r2 = PersonaJSON.stringObjeto(un_string);
    	System.out.println(r2.getNumeroDocumento() + " " + r2.getNombreCompleto() + " " + r2.getScore());
    }
    
    public static String objetoString(Persona p) {	
        return objetoJson(p).toJSONString();
    }

    public static String listaObjetoString(List<Persona> productos) {
        JSONArray array = new JSONArray();
        for (Persona producto : productos) {
            array.add(objetoJson(producto));
        }
        return array.toJSONString();
    }

    private static JSONObject objetoJson(Persona p) {
        JSONObject obj = new JSONObject();
        obj.put("id", p.getId());
        obj.put("numeroDocumento", p.getNumeroDocumento());
        obj.put("nombreCompleto", p.getNombreCompleto());
        obj.put("tipoPersona", p.getTipoPersona());
        obj.put("estado", p.getEstado());
        obj.put("score", p.getScore());
        obj.put("categoriaRiesgo", p.getCategoriaRiesgo());
        return obj;
    }
    
    
    public static Persona stringObjeto(String str) throws Exception {
    	Persona p = new Persona();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        if (jsonObject.get("id") != null) {
            p.setId(((Long) jsonObject.get("id")).intValue());
        }
        p.setNumeroDocumento((String) jsonObject.get("numeroDocumento"));
        p.setNombreCompleto((String) jsonObject.get("nombreCompleto"));
        p.setTipoPersona((String) jsonObject.get("tipoPersona"));
        p.setEstado((String) jsonObject.get("estado"));
        p.setScore(((Long) jsonObject.get("score")).intValue());
        p.setCategoriaRiesgo((String) jsonObject.get("categoriaRiesgo"));
        return p;
	}

}
