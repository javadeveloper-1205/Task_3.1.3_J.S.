
function createNewUser(){

    let addButton = document.getElementById("button-new");
    addButton.addEventListener("click", function (e) {createUser()});
}

async function createTable() {
    let body = document.getElementById("user_list");

    let response = await fetch("/api/users/", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }
    });

    let result = await response.json();

console.log(result)
    let tr;
    body.innerHTML = "";
    result.forEach((user, index)=> {
        tr = document.createElement("tr");
        tr.appendChild(create(user.id));
        tr.appendChild(create(user.username));
        tr.appendChild(create(user.lastname));
        tr.appendChild(create(user.age));
        tr.appendChild(create(user.email));
        let rolesList = create("");
        user.authorities.map((role, index)=>{
            let div = document.createElement("div");
            div.className = "role d-inline-block m-1";
            div.innerHTML = role.authority;
            return div;
        }).forEach((value, index) => {rolesList.appendChild(value)});
        tr.appendChild(rolesList);
        tr.appendChild(create(`<button user-id="${user.id}" type="button" class="btn btn-info edit-btn" data-toggle="modal" data-target="#editModal">Edit</button></td>`));
        tr.appendChild(create(`<button user-id="${user.id}" type="button" class="btn btn-danger delete-btn" data-target="#delete-btn">Delete</button></td>`));
        body.appendChild(tr);
    });


    let delButton = document.getElementsByClassName("delete-btn");
    let editButton = document.getElementsByClassName("edit-btn");

    Array.from(delButton).forEach(el=> el.addEventListener("click", function (e){
         deleteUser(e.currentTarget.getAttribute('user-id'));
        //changeToDelete();
    }, false));

    Array.from(editButton).forEach(el=> el.addEventListener("click", function (e){
        openModal(e.currentTarget.getAttribute('user-id'));
        //changeToEdit();
    }, false));

}

function create(inp){
    let el = document.createElement("td");
    el.innerHTML = inp;
    return el;
}


jQuery(document).ready(function() {
        //$('#new-user-form-btn-save').on('click', function(event) { createUser(); });
        $('#modal-edit-btn-save').on('click', function(event) { updateUser(); });
    createTable();
});

function openModal(id) {

    $('#edit-user-form').trigger('reset');
    $('#modal-edit').modal();
    getUser(id, function(user) {
    console.log(user)
        $('#edit-user-form [name=id]').val(user.id);
        $('#edit-user-form [name=username]').val(user.username);
        $('#edit-user-form [name=lastname]').val(user.lastname);
        $('#edit-user-form [name=age]').val(user.age);
        $('#edit-user-form [name=email]').val(user.email);
        $('#edit-user-form [name=password]').val(user.password);
        $('#edit-user-form [name=roleList]').val(user.roleList?.map(function(a) { return a.role; }));
    });
}

function getCurrentUser(id) {
    $.ajax({
        url: '/api/users',
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { },
        error: function() { showError('Ошибка'); }

    });
}

function getUser(id, func) {
    $.ajax({
        url: `/api/users/` + id ,
        type: 'get',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { func(data);
        console.log(data) },

        error: function() { showError('Ошибка'); }
    });
}

function showError(text) {
    $('#modal-error-text').text(text);
    $('#modal-error').modal();
}

function updateUser() {

    var userData = $('#edit-user-form-2').jsonify();

/*    if (typeof userData.roleList === 'string') {
        userData.roleList = [userData.roleList];
    }
*/
userData['roles'] = []

$("#roleList option").each(function(){
if (this.selected) {
userData['roles'].push({role: this.value, id: this.value == "ROLE_ADMIN" ? 1 : 2, authority: this.value});
}
});

console.log(userData)

    $.ajax({
        url: `/api/users/edit`,
        type: 'put',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            createTable(data);
            $('#modal-edit').modal('toggle');
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}


function deleteUser(id) {
console.log(id)
    $.ajax({
        url: `/api/users/` + id ,
        type: 'delete',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) { createTable(data); },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}


function createUser() {

    var userData = $('#new-user-form').jsonify();

userData['roles'] = []

$("#roleList option").each(function(){
if (this.selected) {
userData['roles'].push({role: this.value, id: this.value == "ROLE_ADMIN" ? 1 : 2, authority: this.value});
}
});

    $.ajax({
        url: `/api/users/`,
        type: 'post',
        data: JSON.stringify(userData),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            //createTable(data);
            //$('#list-tab').click();
            $('#new-user-form').trigger('reset');

            //$('#button-new').
        },
        error: function() { showError('На сервере произошла ошибка'); }
    });
}


/*
async function openModal (e){

let target = e.target;
let userid = $(target).attr("user-id")
console.log(userid)
$('editModal').modal('show');

    let response = await fetch(`/api/users/${userid}/`, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        }
    });

    let result = await response.json();
    let editForm = document.getElementById('editForm')//.find("input")

console.log(editForm)
        editForm[1].value = result.id;
        editForm[2].value = result.username;
        editForm[3].value = result.lastname;
        editForm[4].value = result.age;
        editForm[5].value = result.email;
        editForm[6].value = result.password;

            let options = Array.from(editForm.getElementsByTagName("option"));
            options.forEach((value, index) => {
                if (info.roles.includes(value.innerHTML)) value.setAttribute("selected", "");
            });

console.log($('#editUserButton'))

$(document).on('click', '#editUserButton', function(){
                editUserBtn(e);
})

}

/*
async function editUserBtn (e){

console.log("нажалось")

   let editForm = document.getElementById('editForm')//.find("input")

        let user = {

         id: editForm[1].value,
         username: editForm[2].value,
         lastname: editForm[3].value,
         age: editForm[4].value,
         email: editForm[5].value,
         password: editForm[6].value,
         }
         $("#editRoles").each(function(){
         if (this.selected) {
         user['roles'].push(this.value);
         }
         });

console.log(user)

 let response = await fetch(`/api/users/`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
        body: JSON.stringify(user)
    });

createTable()

$('#editModal').modal("hide");

}

async function deleteUser(e){
    //e.preventDefault();
    console.log("удаление")

let target = e.target;
let userid = $(target).attr("user-id")
console.log(userid)

    console.log(user)

     let response = await fetch("api/users/", {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
        body: JSON.stringify(user)
    });

   // let result = await response.json();

    await createTable();
    //return false;
}
*/