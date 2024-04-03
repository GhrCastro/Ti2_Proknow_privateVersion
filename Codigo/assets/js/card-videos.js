export function cardVideos(data){
    var cards = `<div class="p-2">`
    data.forEach(data => {
        cards += `
            <div name="${data.name}" title="${data.name}" id="${data.id}" url="${data.url}" role="button" class="rounded div-content-video d-flex align-items-center shadow py-3 px-5 mb-4">
                <i class="fs-4 fa-brands ${data.icon}"></i>
                <span class="fw-bold mx-2 text-muted opacity-50 name-video">${data.name}</span>
            </div>
        `
    });

    cards += `</div>`

    return cards
}