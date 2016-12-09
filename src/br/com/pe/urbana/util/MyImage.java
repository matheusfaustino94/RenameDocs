package br.com.pe.urbana.util;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class MyImage implements QRCodeImage{
	private BufferedImage code;
	public MyImage(String image) {
		ImageIcon img = new ImageIcon(image);
		code = imageToByte(img.getImage());
	}
	@Override
	public int getHeight() {
		return code.getHeight();
	}
	@Override
	public int getWidth() {
		return code.getWidth();
	}
	@Override
	public int getPixel(int x, int y) {
		return code.getRGB(x, y);
	}
	/**
	 * Converte uma imagem em um array de bytes.
	 * @param image : imagem a ser convertida.
	 * @return Retorna o array de bytes da imagem.
	 * 
	 * @author Zell Ruskea
	 * */
	private BufferedImage imageToByte(Image image) {
        BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);  
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);  
        bg.dispose();
        bi.flush();
        bg.finalize();
        return bi;     
    }
}
