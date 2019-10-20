  function validateForm(form) {
    var a = form.search_query.value;
    if (a === "") {
      return false;
    }
    return true;
  }

function searchArtist(){
    form=document.getElementById("searchForm");
    if(validateForm(form)){
        form.action="/searchArtist";
        form.submit();
    } else {
        alert("I cannot read your mind!")
    }
}
function searchTrack(){
    form=document.getElementById("searchForm");
    if(validateForm(form)){
        form.action="/searchTrack";
        form.submit();
    } else {
        alert("Gimme Something!")
    }
}
