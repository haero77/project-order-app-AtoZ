/* Element */
const sampleButton = document.getElementById("sample-btn");
const storeAddFormButton = document.getElementById("storeAddFormButton");

/* REST API function */
function buttonClick() {
  const data = sampleButton.val();

  const jsonData = {"data" : data};

  $.ajax({
    url: "/admin/landing",
    contentType: "application/json",
    type: "POST",
    data: jsonData,
    success: function (incomingData) {
      $("#confirmation").show();
      $("#minimumOrderAmount").text(incomingData.storeName);
      $("#test-target").text(incomingData.storeName + incomingData.minimumOrderAmount);
    }
  })
}

function addStore() {
  const storeViewDto = {
    storeName: $("#storeName").val(),
    minimumOrderAmount: $("#minimumOrderAmount").val()
  }
  console.log(storeViewDto);

  $.ajax({
    url: "/admin/addStore",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(storeViewDto),
    success: function (response) {
      console.log(response);
      location.href = response;
    }
  });
}

function join() {
  const memberJoinDto = {
    memberId: $("#memberId").val(),
    password: $("#password").val(),
    memberName: $("#memberName").val()
  }
  console.log(memberJoinDto);

  $.ajax({
    url: "/member/join",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(memberJoinDto),
    success: function (response) {
      alert('회원가입 성공');
    },
  })
}

function login() {
  const memberLoginDto = {
    memberId: $("#memberId").val(),
    password: $("#password").val(),
  }

  $.ajax({
    url: "/member/login",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(memberLoginDto),
    success: function (response) {
      console.log(document.cookie);
      alert('로그인 성공');
    },
    complete: function () {
      location.href = "/order/menuList";
      console.log(document.cookie);
      // toMenuList();
    }
  });
}

function toMenuList() {
  $.ajax( {
    url: "/order/menuList",
    type: "GET"
  })
}
