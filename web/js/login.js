/**
 * 学院与系联动功能实现
 */
document.addEventListener('DOMContentLoaded', function () {
  // 获取相关DOM元素
  const schoolSelect = document.getElementById('uSchool');
  const departmentSelect = document.getElementById('uDepartment');
  const verifyCodeImg = document.getElementById('verifyCodeImg');
  const passwordToggle = document.getElementById('passwordToggle');
  const passwordInput = document.getElementById('uPw');

  // 密码显示/隐藏切换功能
  if (passwordToggle && passwordInput) {
    passwordToggle.addEventListener('click', function () {
      // 切换密码显示/隐藏状态
      const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
      passwordInput.setAttribute('type', type);

      // 切换图标
      const icon = this.querySelector('i');
      if (type === 'text') {
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
        this.setAttribute('title', '隐藏密码');
      } else {
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
        this.setAttribute('title', '显示密码');
      }

      // 添加动画效果
      icon.classList.add('password-toggle-animate');
      setTimeout(() => {
        icon.classList.remove('password-toggle-animate');
      }, 300);
    });
  }

  // 学院选择改变时更新系选项
  if (schoolSelect && departmentSelect) {
    schoolSelect.addEventListener('change', function () {
      updateDepartments(this.value);
    });

    // 页面加载时初始化系选项
    if (schoolSelect.value) {
      updateDepartments(schoolSelect.value);
    }
  }

  // 点击验证码图片刷新验证码
  if (verifyCodeImg) {
    verifyCodeImg.addEventListener('click', refreshVerifyCode);
  }

  // 表单验证
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', function (event) {
      if (!validateForm()) {
        event.preventDefault();
      }
    });
  }
});

/**
 * 根据选择的学院更新系下拉列表
 * @param {string} school - 选择的学院名称
 */
function updateDepartments(school) {
  const departmentSelect = document.getElementById('uDepartment');

  // 如果没有选择学院，清空系下拉列表
  if (!school) {
    departmentSelect.innerHTML = '<option value="">请选择</option>';
    return;
  }

  // 发送AJAX请求获取所选学院的系列表
  fetch('LoginController?action=getDepartments&school=' + encodeURIComponent(school), {
    method: 'GET',
    headers: {
      'Cache-Control': 'no-cache'
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('网络响应错误');
      }
      return response.json();
    })
    .then(departments => {
      // 清空当前选项
      departmentSelect.innerHTML = '';

      // 添加默认选项
      const defaultOption = document.createElement('option');
      defaultOption.value = '';
      defaultOption.textContent = '请选择';
      departmentSelect.appendChild(defaultOption);

      // 添加系选项
      departments.forEach(dept => {
        const option = document.createElement('option');
        option.value = dept;
        option.textContent = dept;
        departmentSelect.appendChild(option);
      });

      // 添加选择后的交互效果
      departmentSelect.classList.add('active');
      setTimeout(() => {
        departmentSelect.classList.remove('active');
      }, 300);
    })
    .catch(error => {
      console.error('获取系列表失败:', error);
      // 显示错误信息
      departmentSelect.innerHTML = '<option value="">加载失败，请重试</option>';
    });
}

/**
 * 刷新验证码
 */
function refreshVerifyCode() {
  const verifyCodeImg = document.getElementById('verifyCodeImg');
  if (verifyCodeImg) {
    // 添加随机时间戳参数防止缓存
    const timestamp = new Date().getTime();
    const randomNum = Math.floor(Math.random() * 1000);
    verifyCodeImg.src = 'VerifyCodeController?t=' + timestamp + '&r=' + randomNum;

    console.log('刷新验证码：' + verifyCodeImg.src);

    // 添加刷新动画效果
    verifyCodeImg.classList.add('refreshing');
    setTimeout(() => {
      verifyCodeImg.classList.remove('refreshing');
    }, 500);

    // 清空验证码输入框
    const verifyCodeInput = document.getElementById('verifyCode');
    if (verifyCodeInput) {
      verifyCodeInput.value = '';
      verifyCodeInput.focus();
    }
  }
}

/**
 * 验证表单数据
 * @returns {boolean} - 表单是否验证通过
 */
function validateForm() {
  const uIdInput = document.getElementById('uId');
  const uPwInput = document.getElementById('uPw');
  const uSchoolSelect = document.getElementById('uSchool');
  const uDepartmentSelect = document.getElementById('uDepartment');
  const verifyCodeInput = document.getElementById('verifyCode');

  let isValid = true;

  // 验证用户ID
  if (!uIdInput.value.trim()) {
    showError(uIdInput, '请输入用户ID');
    isValid = false;
  } else if (!/^\d+$/.test(uIdInput.value.trim())) {
    showError(uIdInput, '用户ID必须是数字');
    isValid = false;
  } else {
    removeError(uIdInput);
  }

  // 验证密码
  if (!uPwInput.value) {
    showError(uPwInput, '请输入密码');
    isValid = false;
  } else {
    removeError(uPwInput);
  }

  // 验证学院选择
  if (!uSchoolSelect.value) {
    showError(uSchoolSelect, '请选择所在学院');
    isValid = false;
  } else {
    removeError(uSchoolSelect);
  }

  // 验证系选择
  if (!uDepartmentSelect.value) {
    showError(uDepartmentSelect, '请选择所在系');
    isValid = false;
  } else {
    removeError(uDepartmentSelect);
  }

  // 验证验证码
  if (!verifyCodeInput.value.trim()) {
    showError(verifyCodeInput, '请输入验证码');
    isValid = false;
  } else {
    removeError(verifyCodeInput);
  }

  return isValid;
}

/**
 * 显示表单元素错误信息
 * @param {HTMLElement} element - 表单元素
 * @param {string} message - 错误信息
 */
function showError(element, message) {
  removeError(element);
  element.classList.add('is-invalid');

  const errorDiv = document.createElement('div');
  errorDiv.className = 'error-message';
  errorDiv.style.color = '#ff3b30';
  errorDiv.style.fontSize = '12px';
  errorDiv.style.marginTop = '5px';
  errorDiv.textContent = message;

  // 根据元素是否在密码容器内调整错误信息的添加位置
  const parent = element.closest('.password-container') || element.parentNode;
  parent.appendChild(errorDiv);
}

/**
 * 移除表单元素的错误信息
 * @param {HTMLElement} element - 表单元素
 */
function removeError(element) {
  element.classList.remove('is-invalid');
  const parent = element.closest('.password-container') || element.parentNode;
  const errorDiv = parent.querySelector('.error-message');
  if (errorDiv) {
    errorDiv.remove();
  }
} 