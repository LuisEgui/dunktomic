const getItem = (field) => {
    const result = window.localStorage.getItem(field)
    return result ? JSON.parse(result) : null
}

const setItem = (field, data) => {
    const parseData = typeof data === 'string' ? data : JSON.stringify(data)
    window.localStorage.setItem(field, parseData)
}

const removeItem = (field) => {
    window.localStorage.removeItem(field)
}

export const localStorageService = {
    getItem,
    setItem,
    removeItem,
}