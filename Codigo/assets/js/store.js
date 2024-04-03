import {listComments, listVideos} from './list-videos.js'

export async function setStoreInicialize(){
    const stores = {
        comments: await listComments(),
        videos: await listVideos()
    }

    for (const key in stores) {
        if (stores.hasOwnProperty.call(stores, key)) {
            createStore(key, stores[key])
        }
    }
}

export function createStore(id, data){
    localStorage.setItem(id, JSON.stringify(data))
}

export function getStore(id){
    return JSON.parse(localStorage.getItem(id))
}