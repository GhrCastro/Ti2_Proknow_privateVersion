import { cardVideos } from './card-videos.js'
import { cardComments } from './card-comments.js'
import { setStoreInicialize, getStore, createStore } from './store.js'
import { postComment, listComments } from './list-videos.js'

await setStoreInicialize()

const divCardVideos = document.getElementById('card-videos')
const divCardComments = document.getElementById('comments')
const iframeVideo = document.getElementById('iframe-videos')
const inputSearch = document.querySelector('.input-search')
const btnSendComment = document.getElementById('send-comment')
const textArea = document.getElementById('text-area-comment')

iframeVideo.src = getStore('videos')[0].url

const releaseDOM = string => {
    const parser = new DOMParser()
    return parser.parseFromString(string, 'text/html').body.firstChild
}

const alternateVideo = async target => {
    
    const actives = Array.from(document.querySelectorAll('.active-show'))
    actives.forEach(a => a.classList.remove('active-show'))
    
    target.classList.add('active-show')
    
    divCardComments.innerHTML = ''
    iframeVideo.src = target.getAttribute('url')
    const id = target.getAttribute('id')

    const commentsFiltered = await listComments(`?id-video=${id}`)

    divCardComments.appendChild(releaseDOM(cardComments(commentsFiltered)))

    btnSendComment.setAttribute('id-video', id)
}

const like = target => {
    target.classList.replace('far', 'fas')

    const likes = target.nextSibling.nextSibling
    const quantity = parseInt(likes.textContent)

    const config = {
        true: {
            action: 'remove',
            content: quantity - 1,
            replace: {
                before: 'fas',
                after: 'far'
            }
        },
        false: {
            action: 'add',
            content: quantity + 1,
            replace: {
                before: 'far',
                after: 'fas'
            }
        }
    }

    const validation = target.classList.contains('liked')

    likes.textContent = config[validation].content
    target.classList[config[validation].action]('liked')
    target.classList.replace(config[validation].replace.before, config[validation].replace.after)

    
}

const cards = cardVideos(getStore('videos'))

const comments = cardComments(getStore('comments'))

divCardVideos.appendChild(releaseDOM(cards))
divCardComments.appendChild(releaseDOM(comments))

document.body.addEventListener('click', e => {
    const target = e.target

    if(target.classList.contains('div-content-video')) alternateVideo(target)

    if(target.classList.contains('like-comment')) like(target)
})


inputSearch.addEventListener('keyup', e => {
    const value = e.target.value.toUpperCase()
    
    const allCardsVideos = Array.from(document.querySelectorAll('.div-content-video'))

    allCardsVideos.forEach(card => {
        card.classList.remove('d-none')
        if(!card.getAttribute('name').toUpperCase().includes(value)){
            card.classList.add('d-none')
        }
    })
   
})

btnSendComment.addEventListener('click', async e => {
    const target = e.target

    const data = [
        {
            "id-video": parseInt(target.getAttribute('id-video')) ,
            "name": "You",
            "date": "Now",
            "comment": textArea.value,
            "profile": "avatar.png",
            "likes": 0
        }
    ]

    const comments = getStore('comments')

    comments.push(data[0])

    createStore('comments', comments)

    const response = await postComment(data[0])

    console.log(response);

    divCardComments.appendChild(releaseDOM(cardComments(data)))

    textArea.value = ''

    divCardComments.scrollTo(0, divCardComments.scrollHeight);
   
})