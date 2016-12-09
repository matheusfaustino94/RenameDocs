package br.com.pe.urbana.job;

import java.io.File;
import java.io.IOException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.google.zxing.NotFoundException;
import br.com.pe.urbana.controller.ManipularArquivosController;
import br.com.pe.urbana.util.Util;

public class QuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		ManipularArquivosController man = ManipularArquivosController
				.getInstance();

		File diretorio = new File(Util.PATH_ORIGEM);

		File[] listArquivos = diretorio.listFiles();

		for (File fl : listArquivos) {

			try {
				man.manipularPDF(fl);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NotFoundException e) {
				e.printStackTrace();

			}
		}

		try {
			man.limparOrigem();			
		} catch (NotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
