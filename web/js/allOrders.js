/**
 * 订单列表页面脚本
 */
document.addEventListener('DOMContentLoaded', function () {
  // 订单卡片悬停效果增强
  const orderCards = document.querySelectorAll('.order-card');
  orderCards.forEach(card => {
    card.addEventListener('mouseenter', function () {
      this.style.transform = 'translateY(-8px)';
      this.style.boxShadow = '0 12px 30px rgba(0, 0, 0, 0.15)';
    });

    card.addEventListener('mouseleave', function () {
      this.style.transform = 'translateY(-4px)';
      this.style.boxShadow = '0 8px 30px rgba(0, 0, 0, 0.1)';
    });
  });

  // 查看详情按钮点击效果
  const detailButtons = document.querySelectorAll('.order-details-btn');
  detailButtons.forEach(button => {
    button.addEventListener('click', function () {
      // 获取订单ID
      const orderId = this.closest('.order-card').querySelector('.order-id').textContent.replace('订单号：', '').trim();

      // 这里可以添加查看订单详情的逻辑，例如弹出模态框或跳转到详情页
      alert(`订单 ${orderId} 的详情功能正在开发中...`);
    });
  });

  // 退出登录按钮确认
  const logoutBtn = document.querySelector('.user-info .btn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', function (e) {
      if (!confirm('确定要退出登录吗？')) {
        e.preventDefault();
      }
    });
  }

  // 添加页面加载动画
  const orderContainer = document.querySelector('.orders-container');
  if (orderContainer) {
    orderContainer.style.opacity = '0';
    orderContainer.style.transition = 'opacity 0.5s ease';

    setTimeout(() => {
      orderContainer.style.opacity = '1';
    }, 100);
  }
}); 