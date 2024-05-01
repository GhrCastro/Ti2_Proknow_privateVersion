(() => {
    'use strict'
    const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(tooltipTriggerEl => {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
})()


let mini = true;

function toggleSidebar() {
    if (mini) {
        document.getElementById("sidebar").style.width = "250px";
        mini = false;
    } else {
        document.getElementById("sidebar").style.width = "85px";
        mini = true;
    }
}
