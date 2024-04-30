import { routes } from './routes.js'
import { fetchHeaders } from './fetch.js'
import { loading } from './loading.js'

export async function listVideos(params = ''){
    loading(true)
    const request = await fetch(routes.videos  + params, { headers: fetchHeaders })
    const response = await request.json()
    loading(false)
    return response
}

export async function listComments(params = ''){
    loading(true)
    const request = await fetch(routes.comments + params, { headers: fetchHeaders })
    const response = await request.json()
    loading(false)
    return response
}

export async function postComment(data){
    loading(true)
    const request = await fetch(routes.comments, { 
        method: 'POST',
        headers: fetchHeaders,
        body: JSON.stringify(data)
    })

    const response = await request.json()
    loading(false)
    return response
}

export async function postVideo(data){
    loading(true)
    const request = await fetch(routes.videos, { 
        method: 'POST',
        headers: fetchHeaders,
        body: JSON.stringify(data)
    })

    const response = await request.json()
    loading(false)
    return response
}