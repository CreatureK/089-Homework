/**
 * 学院与系联动功能实现
 */
document.addEventListener('DOMContentLoaded', function () {
  // 获取相关DOM元素
  const schoolSelect = document.getElementById('uSchool');
  const departmentSelect = document.getElementById('uDepartment');
  const verifyCodeImg = document.getElementById('verifyCodeImg');

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
  fetch('LoginController?action=getDepartments&school=' + encodeURIComponent(school))
    .then(response => response.json())
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
    })
    .catch(error => {
      console.error('获取系列表失败:', error);
    });
}

/**
 * 刷新验证码
 */
function refreshVerifyCode() {
  const verifyCodeImg = document.getElementById('verifyCodeImg');
  if (verifyCodeImg) {
    // 添加时间戳防止缓存
    verifyCodeImg.src = 'VerifyCodeController?' + new Date().getTime();
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

  element.parentNode.appendChild(errorDiv);
}

/**
 * 移除表单元素的错误信息
 * @param {HTMLElement} element - 表单元素
 */
function removeError(element) {
  element.classList.remove('is-invalid');
  const errorDiv = element.parentNode.querySelector('.error-message');
  if (errorDiv) {
    errorDiv.remove();
  }
} 