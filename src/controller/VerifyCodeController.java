package controller;

import util.VerifyCodeUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 验证码控制器，负责生成验证码图片
 */
public class VerifyCodeController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public VerifyCodeController() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("===生成验证码开始===");

    // 设置响应类型为图片
    response.setContentType("image/jpeg");

    // 禁止缓存
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    try {
      // 生成验证码并写入response
      String verifyCode = VerifyCodeUtil.generateCode(response.getOutputStream());

      // 将验证码存入session
      HttpSession session = request.getSession();
      session.setAttribute("verifyCode", verifyCode);

      System.out.println("验证码生成成功：" + verifyCode);
      System.out.println("验证码已存入Session，SessionID：" + session.getId());
    } catch (Exception e) {
      System.out.println("生成验证码失败：" + e.getMessage());
      e.printStackTrace();
    }

    System.out.println("===生成验证码结束===");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}