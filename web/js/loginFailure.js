/**
 * 登录失败页面脚本
 */
document.addEventListener('DOMContentLoaded', function () {
  // 添加返回按钮点击效果
  const backButton = document.querySelector('.btn');
  if (backButton) {
    backButton.addEventListener('mousedown', function () {
      this.style.transform = 'scale(0.98)';
    });

    backButton.addEventListener('mouseup', function () {
      this.style.transform = 'scale(1)';
    });

    backButton.addEventListener('mouseleave', function () {
      this.style.transform = 'scale(1)';
    });
  }

  // 添加错误图标动画
  const errorIcon = document.querySelector('.error-icon');
  if (errorIcon) {
    errorIcon.style.animation = 'shake 0.5s ease-in-out';
  }
});

// 添加CSS动画
const style = document.createElement('style');
style.textContent = `
  @keyframes shake {
    0%, 100% { transform: translateX(0); }
    20%, 60% { transform: translateX(-10px); }
    40%, 80% { transform: translateX(10px); }
  }
`;
document.head.appendChild(style); 