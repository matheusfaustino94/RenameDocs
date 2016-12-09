/**
 * 
 */
package br.com.pe.urbana.modelo;

/**
 * @author iagos
 *
 * 06/12/2016
 */
public class EntidadePDF {
	
	private String path;
	
	private String nome;
	
	private String cpf;
	
	private String prontuario;
	
	private String tpDocumento;

	private String pathDestino;
	
	private String pathTemp;

	public String getPathTemp() {
		return pathTemp;
	}

	public void setPathTemp(String pathTemp) {
		this.pathTemp = pathTemp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getProntuario() {
		return prontuario;
	}

	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}

	public String getTpDocumento() {
		return tpDocumento;
	}

	public void setTpDocumento(String tpDocumento) {
		this.tpDocumento = tpDocumento;
	}

	public String getPathDestino() {
		return pathDestino;
	}

	public void setPathDestino(String pathDestino) {
		this.pathDestino = pathDestino;
	}
	
	

	

}
