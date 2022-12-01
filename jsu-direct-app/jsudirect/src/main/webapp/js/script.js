let searchBtn = document.getElementById("submit-btn");

searchBtn.addEventListener("click", () => {
    event.preventDefault();

    getDirections();
});

async function getDirections() {
    let origin = document.getElementById("origin").value;
    let destination =  document.getElementById("destination").value;
    let params = new URLSearchParams();
    params.append("origin", origin );
    params.append("destination", destination);
    const response = await fetch("/directions", {
        method: "POST",
        body: params,
    });
   
    const result = await response.text();
    
    localStorage.setItem("directions", result);
    console.log(localStorage.getItem("directions"));
    window.location.href = "destination.html";
}