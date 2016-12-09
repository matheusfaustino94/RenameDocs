package br.com.pe.urbana.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heiders
 */
public class Util {
	
	//Origem do PDF
	public static final String PATH_ORIGEM = "C:\\Users\\iagos\\Desktop\\ARQUIVOS\\PDF\\ORIGEM";
	//destino onde serao salvas as imagens
	public static final String PATH_DESTINO ="C:\\Users\\iagos\\Desktop\\ARQUIVOS\\PDF\\DESTINO\\";
	//destino raiz (teste)
	public static final String PATH_RAIZ ="C:\\Users\\iagos\\Desktop\\ARQUIVOS\\PDF\\";
	
	//destino raiz (teste)
	public static final String PATH_CONVERTIDO ="C:\\Users\\iagos\\Desktop\\ARQUIVOS\\PDF\\CONVERTIDOS\\";
		
	//Origem do PDF(teste)
	//public static final String PATH_PDF = "src\\Paginas\\SCAN.pdf";
	
	
	//PATH TEMPORARIO
	public static final String PATH_TEMP = "C:/Users/iagos/Desktop/ARQUIVOS/PDF/TEMP//";
	public static final String PATH_TEMP_TESTE = "C:/Users/iagos/Desktop/ARQUIVOS/PDF/TEMP2//";
	
    public static final String PATH = "/C:/Users/heiders/Desktop/ARQUIVOS/APROCESSAR/";
    
//    public static final String PATH_ORIGEM = "C:/Users/iagos/Desktop/ARQUIVOS/";
    
//    public static final String PATH_SCP = "/backup/arquivos/";
   

    public static Date parseDT(String data) {
        Date dt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            if (data != null && !data.equals("")) {
                dt = sdf.parse(data);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dt;
    }

    public static String tratamento(String campo) {
        String str = "0";

        if (campo != null && !campo.equals("")) {
            str = campo.replace(",", ".");
        }
        return str;

    }
    
	public static String unMaskCnpj(String cnpj) {
			
		if(cnpj != null && !cnpj.equals("")){
				cnpj = cnpj.replace(".", "");
				cnpj = cnpj.replace(".", "");
				cnpj = cnpj.replace("/", "");
				cnpj = cnpj.replace("-", "");
				cnpj = cnpj.trim();
		}
		
		return cnpj;
	}

}
