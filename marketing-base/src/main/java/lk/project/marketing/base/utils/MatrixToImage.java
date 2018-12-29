package lk.project.marketing.base.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 二维码生成工具
 * @author  egan
 * <pre>
 * email egzosn@gmail.com
 * date  2017/2/7 10:35
 * </pre>
 */
@Slf4j
public class MatrixToImage {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private MatrixToImage() {}

    /**
	 * 根据二维矩阵的碎片 生成对应的二维码图像缓冲
	 * @param matrix  二维矩阵的碎片 包含 宽高 行，字节
	 * @see com.google.zxing.common.BitMatrix
	 * @return 二维码图像缓冲
     */
	   public static BufferedImage toBufferedImage(BitMatrix matrix) {
		 int width = matrix.getWidth();
		 int height = matrix.getHeight();
		 BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		 for (int x = 0; x < width; x++) {
		   for (int y = 0; y < height; y++) {
			 image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
		   }
		 }
		 return image;
	   }


	/**
	 * 二维码生成文件
	 * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
	 * @param format 格式
	 * @param file 保持的文件地址
	 * @throws IOException 文件保存异常
	 */
	   public static void writeToFile(BitMatrix matrix, String format, File file)
	       throws IOException {
	     BufferedImage image = toBufferedImage(matrix);
	     if (!ImageIO.write(image, format, file)) {
	       throw new IOException("Could not write an image of format " + format + " to " + file);
	     }
	   }


	/**
	 * 二维码生成流
	 * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
	 * @param format 格式
	 * @param stream 保持的文件输出流
	 * @throws IOException 文件保存异常
	 */
	   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
	       throws IOException {
	     BufferedImage image = toBufferedImage(matrix);
	     if (!ImageIO.write(image, format, stream)) {
	       throw new IOException("Could not write an image of format " + format);
	     }
	   }
	   
	   
	/**
	* 二维码信息写成JPG文件
	* @param content  二维码信息
	* @param fileUrl 文件地址
	*/
	public static void writeInfoToJpgFile(String content, String fileUrl) throws WriterException, IOException {
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix bitMatrix = multiFormatWriter.encode(content,
				BarcodeFormat.QR_CODE, 250, 250, hints);
		File file1 = new File(fileUrl);
		MatrixToImage.writeToFile(bitMatrix, "jpg", file1);

	}
	  
	  
	/**
	* 二维码信息写成JPG BufferedImage
	* @param content 二维码信息
	* @return JPG BufferedImage
	*/
	public static BufferedImage writeInfoToJpgBuff(String content) throws WriterException {

		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(content,
				BarcodeFormat.QR_CODE, 250, 250, hints);
		return MatrixToImage.toBufferedImage(bitMatrix);

	}


	/**
	 * 在二维码图片中添加logo图片
	 * @param image 二维码图片
	 * @param logoPath logo图片路径
	 */
     public static BufferedImage addLogo(BufferedImage image, String logoPath) throws IOException {

     	final int LogoPart = 4;
		Graphics2D g = image.createGraphics();
		BufferedImage logoImage = ImageIO.read(new File(logoPath));
		// 计算logo图片大小,可适应长方形图片,根据较短边生成正方形
		int width = image.getWidth() < image.getHeight() ? image.getWidth() / LogoPart : image.getHeight() / LogoPart;
		int height = width;
		// 计算logo图片放置位置
		int x = (image.getWidth() - width) / 2;
		int y = (image.getHeight() - height) / 2;
		// 在二维码图片上绘制logo图片
		g.drawImage(logoImage, x, y, width, height, null);

		// 绘制logo边框,可选
		//g.drawRoundRect(x, y, logoImage.getWidth(), logoImage.getHeight(), 10, 10);
		g.setStroke(new BasicStroke(2)); // 画笔粗细
		g.setColor(Color.WHITE); // 边框颜色
		g.drawRect(x, y, width, height); // 矩形边框
		logoImage.flush();
		g.dispose();
		return image;

	 }

//	/**
//	 * 解析二维码图片
//	 * @param filePath 图片路径
//	 */
//	 public static String decodeQR(String filePath) throws IOException, NotFoundException {
//
//		if (StringUtils.isEmpty(filePath)) {
//			throw new IOException("未指定二维码图片文件!");
//		}
//
//		FileInputStream imageIn;
//		try {
//			imageIn = new FileInputStream(filePath);
//		} catch (FileNotFoundException e) {
//			log.error("二维码图片文件不存在!{}",e);
//			throw e;
//		}
//
//		EnumMap<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);
//		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8"); // 指定编码方式,防止中文乱码
//		BufferedImage image = ImageIO.read(imageIn);
//		LuminanceSource source = new BufferedImageLuminanceSource(image);
//		Binarizer binarizer = new HybridBinarizer(source);
//		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
//		MultiFormatReader reader = new MultiFormatReader();
//		Result result = reader.decode(binaryBitmap, hints);
//		return result.getText();
//
//	}

}
