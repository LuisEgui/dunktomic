# frontend

Esta carpeta contiene el codigo del frontend de Dunktomic desarrollado con Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## Dependencies

### Tailwind install

```sh
npm install -D tailwindcss postcss autoprefixer

npx tailwindcss init -p
```

### Daisy UI install

Install daisyUI as a Node package:

```sh
npm i -D daisyui@latest
```

Add daisyUI to tailwind.config.js:

```sh
module.exports = {
  //...
  plugins: [
    require('daisyui'),
  ],
}
```

### Hero Icons install

```sh
npm install @heroicons/vue
```
