function setTokenToHeader (xhr) {
    if(localStorage.getItem('token') !== null) {
        xhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem('token'));
    }
}