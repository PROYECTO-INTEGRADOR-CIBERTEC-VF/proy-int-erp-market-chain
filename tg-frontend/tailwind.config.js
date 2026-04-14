/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,ts,scss}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        'on-background': '#ffffff',
        background: '#0e0e0e',
        'outline-variant': '#484847',
        'on-tertiary-fixed': '#41004d',
        secondary: '#7e98ff',
        'on-surface': '#ffffff',
        outline: '#767575',
        'on-primary-fixed-variant': '#002a60',
        'on-error': '#490006',
        'surface-container-lowest': '#000000',
        'tertiary-dim': '#e48fed',
        'surface-tint': '#84adff',
        'surface-variant': '#262626',
        'surface-container': '#1a1a1a',
        tertiary: '#fab0ff',
        'primary-fixed-dim': '#5091ff',
        'secondary-container': '#1e3da1',
        'tertiary-container': '#f39cfb',
        'surface-dim': '#0e0e0e',
        'error-container': '#9f0519',
        'inverse-on-surface': '#565555',
        'on-primary-fixed': '#000000',
        'on-secondary': '#00185d',
        'on-error-container': '#ffa8a3',
        'secondary-fixed': '#c6cfff',
        'on-secondary-fixed': '#00298b',
        'primary-fixed': '#6c9fff',
        surface: '#0e0e0e',
        'error-dim': '#d7383b',
        'secondary-fixed-dim': '#b3c1ff',
        'on-primary-container': '#00214e',
        error: '#ff716c',
        'tertiary-fixed-dim': '#e48fed',
        'on-secondary-container': '#c4ceff',
        'on-tertiary-container': '#60136d',
        'surface-bright': '#2c2c2c',
        'inverse-primary': '#005bc1',
        'on-primary': '#002d64',
        primary: '#84adff',
        'surface-container-high': '#20201f',
        'on-tertiary': '#6a1f77',
        'tertiary-fixed': '#f39cfb',
        'primary-container': '#6c9fff',
        'inverse-surface': '#fcf9f8',
        'surface-container-highest': '#262626',
        'on-surface-variant': '#adaaaa',
        'on-tertiary-fixed-variant': '#6a1f77',
        'secondary-dim': '#7e98ff',
        'primary-dim': '#0070ea',
        'on-secondary-fixed-variant': '#2b48ac',
        'surface-container-low': '#131313'
      },
      borderRadius: {
        DEFAULT: '1rem',
        lg: '2rem',
        xl: '3rem',
        full: '9999px'
      },
      fontFamily: {
        headline: ['Manrope', 'sans-serif'],
        body: ['Inter', 'sans-serif'],
        label: ['Inter', 'sans-serif']
      }
    }
  }
};
