import { carouselVideos, Highlights } from './component/videos.js'
import { routes } from './routes.js'
import { loading } from './loading.js'

const divCarouselVideos = document.getElementById('carousel-videos')
const divHighlights = document.getElementById('highlights')

loading(true)
const requestVideos = await fetch(routes.videos)
const jsonVideos = await requestVideos.json()

divCarouselVideos.innerHTML = await carouselVideos(jsonVideos)

const requestHighlights = await fetch(routes.highlights)
const jsonHighlights = await requestHighlights.json()

divHighlights.innerHTML = await Highlights(jsonHighlights)



const owl = $('.owl-carousel').owlCarousel({
    center: true,
    loop: true,
    arrows: true,
    dots: true,
    margin: 10,
    items: 4,
    nav: true,
    responsiveClass: true,
    responsive: {
        0: {
            items: 1,
            nav: true
        },
        700: {
            items: 2,
            nav: true
        },
        1000: {
            items: 4,
            nav: true,
        }
    }
})

loading(false, 4000)