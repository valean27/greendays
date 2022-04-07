Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});
$( document ).ready(function() {
    console.log( "ready!" );
    document.getElementById('txtDate').value = new Date().toDateInputValue();
});
