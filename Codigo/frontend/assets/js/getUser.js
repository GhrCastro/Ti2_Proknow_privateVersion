
function getSessionUser(){

    const userJson = sessionStorage.getItem('usuario');

    if(userJson){
        const user = JSON.parse(userJson);
        return user;
    }else{
        return null;
    }

}