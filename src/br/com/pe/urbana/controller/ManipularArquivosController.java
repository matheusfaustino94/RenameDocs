package br.com.pe.urbana.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import br.com.pe.urbana.modelo.EntidadePDF;
import br.com.pe.urbana.util.ManipularLaudos;
import br.com.pe.urbana.util.Util;

public class ManipularArquivosController {
	private static ManipularArquivosController instance;

	public ManipularArquivosController() {

	}

	public static ManipularArquivosController getInstance() {

		if (instance == null) {
			instance = new ManipularArquivosController();
		}

		return instance;
	}

	public void transferenciaArquivo(String nameArquivo) throws IOException {

		String pathExemploOrigem = nameArquivo;
		String pathExemploDestinoCopiar = nameArquivo;

		File arquivoExemploOrigem = new File(pathExemploOrigem);

		if (arquivoExemploOrigem.exists()) {
			File arquivoExemploDestinoCopiar = new File(
					pathExemploDestinoCopiar);
			ManipularLaudos.copy(arquivoExemploOrigem,
					arquivoExemploDestinoCopiar);

			arquivoExemploOrigem.delete();
		}

	}
	/*
	 * copia os pdfs já renomeados para a pasta espeficicada
	 */
	public static void copia(File fonte, String destino) throws IOException {
		InputStream in = new FileInputStream(fonte);
		OutputStream out = new FileOutputStream(destino);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public void limparOrigem() throws IOException, NotFoundException {
		/*
		 * limpa a pasta ORIGEM Depois de todos os arquivos contidos nela 
		 * forem convertido.
		 */
		File origem = new File(Util.PATH_ORIGEM);
		File[] arqOrigin = origem.listFiles();

		for (File fileOrig : arqOrigin) {
			System.out.println("Deletando o file " + fileOrig.getName());
			fileOrig.delete();
		}

	}
	
	public void apagarTemp() throws IOException, NotFoundException {
		/*
		 * limpa a pasta TEMP Depois de todos os arquivos contidos nela 
		 * forem convertidos. (para garantir que nenhum arquivo seja movido
		 * para uma pasta nao relacionada).
		 */
		File temp = new File(Util.PATH_TEMP);
		File[] arqOrigin = temp.listFiles();

		for (File fileOrig : arqOrigin) {
			System.out.println("Deletando o file " + fileOrig.getName());
			fileOrig.delete();
			temp.delete();
		}

	}

	/*
	 * metodo responsavel por carregar os arquivos Pdfs, converter as paginas 
	 * para PNG, ler o qrcode e renomear os arquivos.
	 */
	public void manipularPDF(File fl) throws FileNotFoundException,
			IOException, NotFoundException {

		List<EntidadePDF> listPdf = new ArrayList<EntidadePDF>();
		//carrega o pdf
		PDDocument doc = PDDocument.load(new FileInputStream(fl));
		System.out.println("iniciando conversão PDF para png");
		@SuppressWarnings("unchecked")
		// Ler todas as paginas do pdf.
		List<PDPage> pages = doc.getDocumentCatalog().getAllPages();

		Iterator<PDPage> i = pages.iterator();

		// se a pasta TEMP nao existir, ela é criada
		File diretorio = new File(Util.PATH_TEMP);
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}

		// se a pasta DESTINO nao existir, ela é criada
		File destin = new File(Util.PATH_DESTINO);
		if (!destin.exists()) {
			destin.mkdir();
		}
		// se a pasta CONVERTIDOS nao existir, ela é criada
		File convertido = new File(Util.PATH_CONVERTIDO);
		if (!convertido.exists()) {
			convertido.mkdir();
		}

		int count = 1;
		EntidadePDF entd = null;
		String aux = "";
		while (i.hasNext()) {
			entd = new EntidadePDF();

			System.out.println("convertendo pagina " + count);
			PDPage page = i.next();
			BufferedImage bi = page.convertToImage();

			aux = "QRCODE" + count;
			entd.setPath(diretorio + "\\" + aux + ".png");
			entd.setNome(aux);
			entd.setPathDestino(Util.PATH_DESTINO);
			// cria as imagens de acordo com as paginas do pdf
			ImageIO.write(bi, "png", new File(entd.getPath()));

			listPdf.add(entd);
			count++;

		}

		/*
		 * recebe a primeira imagem (QRCODE1) e recorta o qrcode que é salvo na
		 * imagem QRCODE0.
		 */

		InputStream imagemQualquer = new FileInputStream(diretorio + "\\"
				+ "QRCODE1.png");
		BufferedImage image = ImageIO.read(imagemQualquer);

		// cordenadas do ponto inicial de leitura da imagem

		// Não pode ser maior que (larguraDaImagem/2 + x)
		int x = 550;

		// Não pode ser maior que (alturaDaImagem/2 + y)
		int y = 80;

		System.out.println("L: " + image.getWidth() / 2);
		System.out.println("A: " + image.getHeight() / 2);

		// largura do corte
		int w = 600;

		// altura do corte
		int h = 400;

		System.out.println(w);
		System.out.println(h);

		BufferedImage img = image.getSubimage(x, y, w, h);

		try {
			ImageIO.write(img, "JPG",
					new File(diretorio + "\\" + "QRCODE0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**************************/

		// //Leitura do Qrcode com Zxing
		System.out.println("Decodificando...");

		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(diretorio + "//"
								+ "QRCODE0.png")))));
		Result decodificado = new MultiFormatReader().decode(bitmap);
		System.out.println("resultado: " + decodificado.getText());

		String result = null;
		String[] auxMsg = null;
		File renomeado = null;

		result = decodificado.getText();
		boolean flagSplit = false;
		auxMsg = result.split("\n");

		if (auxMsg.length == 1) {
			auxMsg = result.split(";");
			flagSplit = true;
		}
		String nome = "";
		if (flagSplit) {
			String auxCPF = Util.unMaskCnpj(auxMsg[2]);
			nome = auxCPF + "_" + auxMsg[3].trim();
		} else {
			nome = auxMsg[1].trim() + "_" + auxMsg[3].trim();
		}
		renomeado = new File(Util.PATH_DESTINO + nome);
		renomeado.mkdir();

		File filePath = new File(Util.PATH_TEMP);
		File[] listFile = filePath.listFiles();
		String name = "";
		int acc = 0;
		for (File file : listFile) {

			InputStream in = new FileInputStream(Util.PATH_TEMP + "/"
					+ file.getName());
			name = "/" + nome;
			OutputStream out = new FileOutputStream(Util.PATH_DESTINO + name
					+ name + "_" + acc + ".png");

			// Transferindo bytes de entrada para saída
			byte[] buffer = new byte[1024];
			int lenght;
			while ((lenght = in.read(buffer)) > 0) {
				out.write(buffer, 0, lenght);

			}

			/*
			 * apaga o "recorte" do Qrcode das pastas geradas (recorte utilizado
			 * apenas para aumentar as chances de sucesso da leitura do Qrcode)
			 */
			String arq = nome + "_" + "0.png";
			File pasta = new File(Util.PATH_DESTINO + name);
			File[] arquivos = pasta.listFiles();
			for (int J = 0; J < arquivos.length; J++) {
				if (arquivos[J].getName().matches(arq)) {
					arquivos[J].delete();
				}
			}

			in.close();
			out.close();

			acc++;
		}

		// mover pdf

		File fonte = new File(Util.PATH_ORIGEM + "/" + fl.getName());
		String destino = (Util.PATH_CONVERTIDO + "/" + nome + ".pdf");
		try {
			copia(fonte, destino);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// limpa a pasta TEMP
		File[] arqTemp = diretorio.listFiles();

		for (File fileTemp : arqTemp) {
			System.out.println("Deletando o file " + fileTemp.getName());
			fileTemp.delete();
			diretorio.delete();
		}

		System.out.println("Deletando o diretorio " + diretorio.getName());
		diretorio.delete();
		System.out.println("Conversão Completa!");

	}

}
