const container = document.getElementById('mods');
const registerBtn = document.getElementById('register1');
const loginBtn = document.getElementById('login12');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});
loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});