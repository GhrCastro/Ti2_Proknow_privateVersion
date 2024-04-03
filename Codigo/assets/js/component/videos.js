export async function carouselVideos(data){
    
    var videos = ``
    data.forEach(json => {
        videos += `
            <div class="item">
                <iframe class="w-100 rounded"
                    src="${json.url}"
                    height="300"
                    title="YouTube video player" 
                    frameborder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                    allowfullscreen
                >
                </iframe> 
            </div>
        `
    });

    return videos
}

export async function Highlights(data){
    
    var highlights = ``
    data.forEach(json => {

        var split = json.url.split('/embed/')
        var link = `${split[0]}/watch?v=${split[1]}`
        
        highlights += `
            <div class="col-lg-4 col-md-6 col-sm-12 d-flex pb-4">
                <div class="card w-100 shadow">
                    <div class="card-horizontal">
                        <div class="card-body">
                            <iframe class="w-100 rounded"
                                src="${json.url}"
                                height="180"
                                title="YouTube video player" 
                                frameborder="0"
                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                                allowfullscreen
                            >
                            </iframe> 
                            <h4 class="card-title">${json.title}</h4>
                            <p class="card-text">${json.describe}</p>
                            <p class="text-end fw-bold">
                                <a href="${link}" target="_blank" class="card-link text-end">Read More →</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        `
    });

    return highlights
}