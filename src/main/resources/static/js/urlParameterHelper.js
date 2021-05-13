function getParameter(parameterName) {
    parameterName = parameterName.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    let regex = new RegExp('[\\?&]' + parameterName + '=([^&#]*)');
    let results = regex.exec(location.search);
    return results === null ? null : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

function redirectToHome(){
    window.location.href="advertisementsList.html";
}
