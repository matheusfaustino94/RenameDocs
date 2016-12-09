package br.com.pe.urbana.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class ManipularLaudos{

    public static void main(String[] args){
       
        String pathExemploOrigem = "C:\\Users\\heiders\\Desktop\\TesteFotos\\LaudosNaoVinculadas\\00879853492.png";
        String pathExemploDestinoCopiar = "C:\\Users\\heiders\\Desktop\\TesteFotos\\LaudosVinculadas\\00879853492.png";
        String pathExemploDestinoCopiar2 = "C:\\Users\\heiders\\Desktop\\TesteFotos\\LaudosNaoVinculadas\\Teste\\00879853492.png";
        String pathExemploDestinoRenomear = "C:\\Users\\heiders\\Desktop\\TesteFotos\\LaudosNaoVinculadas\\Teste\\00879853492.png";
       
        File arquivoExemploOrigem = new File(pathExemploOrigem);
        File arquivoExemploDestinoCopiar = new File(pathExemploDestinoCopiar);
        File arquivoExemploDestinoCopiar2 = new File(pathExemploDestinoCopiar2);
        File arquivoExemploDestinoRenomear = new File(pathExemploDestinoRenomear);
        try {
           
            //Primeiro modo de copiar o arquivo de um local para outro
            copy(arquivoExemploOrigem, arquivoExemploDestinoCopiar);
           
            //Segundo modo de copiar o arquivo de um local para outro
            copy(arquivoExemploOrigem, arquivoExemploDestinoCopiar2, true);
           
            //Renomear ou mover um arquivo
            //Se não alterar a pasta, o sistema apenas renomeia
            //Se alterar a pasta, o sistema move o arquivo e renomeia
            arquivoExemploDestinoCopiar.renameTo(arquivoExemploDestinoRenomear);
           
            //Apagar um arquivo
            arquivoExemploDestinoCopiar2.delete();
           
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
   
    /**
     * Copia arquivos de um local para o outro 
     * @param origem - Arquivo de origem 
     * @param destino - Arquivo de destino 
     * @param overwrite - Confirmação para sobrescrever os arquivos 
     * @throws IOException 
     */
    public static void copy(File origem, File destino) throws IOException {

        InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);           
        // Transferindo bytes de entrada para saída
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght= in.read(buffer)) > 0) {
            out.write(buffer, 0, lenght);
        }
        in.close();
        out.close();

    }
   
    /** 
     * Copia arquivos de um local para o outro 
     * @param origem - Arquivo de origem 
     * @param destino - Arquivo de destino 
     * @param overwrite - Confirmação para sobrescrever os arquivos 
     * @throws IOException 
     */ 
    public static void copy(File origem, File destino, boolean overwrite) throws IOException{ 

       if (destino.exists() && !overwrite){ 
          System.err.println(destino.getName()+" já existe, ignorando..."); 
          return; 
       } 
       FileInputStream fisOrigem = new FileInputStream(origem); 
       FileOutputStream fisDestino = new FileOutputStream(destino); 
       FileChannel fcOrigem = fisOrigem.getChannel();   
       FileChannel fcDestino = fisDestino.getChannel();   
       fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);   
       fisOrigem.close();   
       fisDestino.close(); 
 
    }
    
   
}

