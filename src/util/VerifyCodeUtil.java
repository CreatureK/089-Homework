package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码生成工具类
 */
public class VerifyCodeUtil {
  // 验证码字符集
  private static final String CODES = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
  private static final int WIDTH = 100;
  private static final int HEIGHT = 40;
  private static final int CODE_LENGTH = 4;
  private static final Random random = new Random();

  /**
   * 生成验证码并输出到指定输出流
   * 
   * @param output 输出流
   * @return 生成的验证码
   * @throws IOException 如果生成或输出图片时发生错误
   */
  public static String generateCode(OutputStream output) throws IOException {
    // 生成随机验证码
    char[] codeChars = new char[CODE_LENGTH];
    for (int i = 0; i < CODE_LENGTH; i++) {
      codeChars[i] = CODES.charAt(random.nextInt(CODES.length()));
    }
    String code = new String(codeChars);

    // 创建图片
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    // 设置背景色
    g.setColor(new Color(240, 240, 255));
    g.fillRect(0, 0, WIDTH, HEIGHT);

    // 添加噪点
    for (int i = 0; i < 100; i++) {
      int x = random.nextInt(WIDTH);
      int y = random.nextInt(HEIGHT);
      g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
      g.drawOval(x, y, 1, 1);
    }

    // 添加干扰线
    for (int i = 0; i < 5; i++) {
      int x1 = random.nextInt(WIDTH);
      int y1 = random.nextInt(HEIGHT);
      int x2 = random.nextInt(WIDTH);
      int y2 = random.nextInt(HEIGHT);
      g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
      g.drawLine(x1, y1, x2, y2);
    }

    // 绘制验证码
    g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
    for (int i = 0; i < CODE_LENGTH; i++) {
      g.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
      g.drawString(String.valueOf(codeChars[i]), 20 * i + 10, 25 + random.nextInt(10));
    }

    g.dispose();

    // 输出图片
    ImageIO.write(image, "JPEG", output);

    return code;
  }
}