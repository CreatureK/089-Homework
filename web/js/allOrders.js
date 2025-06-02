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

  // 订单成功消息自动消失
  const successMessage = document.querySelector('.success-message');
  if (successMessage) {
    setTimeout(() => {
      successMessage.style.display = 'none';
    }, 5000);
  }
});

/**
 * 单个订单删除
 * @param {number} orderId - 订单ID
 */
function deleteOrder(orderId) {
  if (confirm('确定要删除这个订单吗？')) {
    var form = document.getElementById('orderForm');
    // 清空之前选中的复选框
    var checkboxes = form.querySelectorAll('input[name="selectedOrders"]');
    for (var i = 0; i < checkboxes.length; i++) {
      checkboxes[i].checked = false;
    }

    // 设置当前订单为选中
    var orderCheckbox = form.querySelector('input[value="' + orderId + '"]');
    if (orderCheckbox) orderCheckbox.checked = true;

    form.action = 'OrderController';
    form.method = 'post';

    // 创建并添加action参数
    var actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'deleteOrders';
    form.appendChild(actionInput);

    // 提交表单
    form.submit();
  }
}

/**
 * 单个订单取消
 * @param {number} orderId - 订单ID
 */
function cancelOrder(orderId) {
  if (confirm('确定要取消这个订单吗？')) {
    var form = document.getElementById('orderForm');
    // 清空之前选中的复选框
    var checkboxes = form.querySelectorAll('input[name="selectedOrders"]');
    for (var i = 0; i < checkboxes.length; i++) {
      checkboxes[i].checked = false;
    }

    // 设置当前订单为选中
    var orderCheckbox = form.querySelector('input[value="' + orderId + '"]');
    if (orderCheckbox) orderCheckbox.checked = true;

    form.action = 'OrderController';
    form.method = 'post';

    // 创建并添加action参数
    var actionInput = document.createElement('input');
    actionInput.type = 'hidden';
    actionInput.name = 'action';
    actionInput.value = 'cancelOrders';
    form.appendChild(actionInput);

    // 提交表单
    form.submit();
  }
}

/**
 * 批量操作相关事件处理
 */
document.addEventListener('DOMContentLoaded', function () {
  // 批量删除订单
  var batchDeleteBtn = document.getElementById('batchDeleteBtn');
  if (batchDeleteBtn) {
    batchDeleteBtn.addEventListener('click', function () {
      var form = document.getElementById('orderForm');
      var selectedOrders = form.querySelectorAll('input[name="selectedOrders"]:checked');

      if (selectedOrders.length === 0) {
        alert('请至少选择一个订单');
        return;
      }

      if (confirm('确定要删除选中的 ' + selectedOrders.length + ' 个订单吗？')) {
        form.action = 'OrderController';
        form.method = 'post';

        // 创建并添加action参数
        var actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'deleteOrders';
        form.appendChild(actionInput);

        // 提交表单
        form.submit();
      }
    });
  }

  // 批量取消订单
  var batchCancelBtn = document.getElementById('batchCancelBtn');
  if (batchCancelBtn) {
    batchCancelBtn.addEventListener('click', function () {
      var form = document.getElementById('orderForm');
      var selectedOrders = form.querySelectorAll('input[name="selectedOrders"]:checked');

      if (selectedOrders.length === 0) {
        alert('请至少选择一个订单');
        return;
      }

      if (confirm('确定要取消选中的 ' + selectedOrders.length + ' 个订单吗？')) {
        form.action = 'OrderController';
        form.method = 'post';

        // 创建并添加action参数
        var actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'cancelOrders';
        form.appendChild(actionInput);

        // 提交表单
        form.submit();
      }
    });
  }

  // 全局删除订单按钮
  var deleteOrderBtn = document.getElementById('deleteOrderBtn');
  if (deleteOrderBtn) {
    deleteOrderBtn.addEventListener('click', function () {
      var form = document.getElementById('orderForm');
      var selectedOrders = form.querySelectorAll('input[name="selectedOrders"]:checked');

      if (selectedOrders.length === 0) {
        alert('请选择要删除的订单');
        return;
      }

      if (confirm('确定要删除选中的 ' + selectedOrders.length + ' 个订单吗？')) {
        form.action = 'OrderController';
        form.method = 'post';

        // 创建并添加action参数
        var actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'deleteOrders';
        form.appendChild(actionInput);

        // 提交表单
        form.submit();
      }
    });
  }

  // 全局取消订单按钮
  var cancelOrderBtn = document.getElementById('cancelOrderBtn');
  if (cancelOrderBtn) {
    cancelOrderBtn.addEventListener('click', function () {
      var form = document.getElementById('orderForm');
      var selectedOrders = form.querySelectorAll('input[name="selectedOrders"]:checked');

      if (selectedOrders.length === 0) {
        alert('请选择要取消的订单');
        return;
      }

      if (confirm('确定要取消选中的 ' + selectedOrders.length + ' 个订单吗？')) {
        form.action = 'OrderController';
        form.method = 'post';

        // 创建并添加action参数
        var actionInput = document.createElement('input');
        actionInput.type = 'hidden';
        actionInput.name = 'action';
        actionInput.value = 'cancelOrders';
        form.appendChild(actionInput);

        // 提交表单
        form.submit();
      }
    });
  }
}); 