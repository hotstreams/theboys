/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{html,js,svelte,ts}', 
    './node_modules/flowbite/**/*.js'
  ],
  theme: {
    extend: {},
  },
  plugins: [ 
    require('preline/plugin'),  
    require('flowbite/plugin'),
    require('@tailwindcss/forms'),
  ]
}
